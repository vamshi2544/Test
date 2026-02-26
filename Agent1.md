
observability-agent/
  pom.xml
  README.md
  src/main/java/com/company/obsagent/
    ObservabilityAgentApplication.java

    config/
      AppConfigProperties.java
      SchedulerConfig.java

    api/
      DeploymentAnalysisController.java
      MonitoringController.java
      dto/
        AnalyzeFileRequest.java
        AnalyzeFileResponse.java
        MonitoringRunResponse.java

    core/
      model/
        LogEvent.java
        ErrorCluster.java
        RunbookRule.java
        RunbookMatch.java
        AnalysisResult.java
        RootCause.java

      parsing/
        TimestampParser.java
        LogParser.java

      clustering/
        ErrorSignalDetector.java
        SignatureNormalizer.java
        ErrorClusterer.java

      runbook/
        RunbookLoader.java
        RunbookMatcher.java

      reporting/
        MarkdownReportWriter.java

      service/
        DeploymentAnalysisService.java
        MonitoringService.java

      util/
        Redactor.java
        FileUtils.java

    integrations/              <-- empty stubs now, filled later
      splunk/
        SplunkClient.java
        SplunkQueryTemplates.java
      pcf/
        PcfClient.java
      newrelic/
        NewRelicClient.java

  src/main/resources/
    application.yml
    runbooks/
      pcf_java_common.yml
    samples/
      logs.txt

  src/test/java/com/company/obsagent/
    parsing/
      LogParserTest.java
    clustering/
      ErrorClustererTest.java


End-to-end behavior (file-first)

Endpoint 1: Deployment Agent (File)

POST /api/deployments/analyze-file (multipart upload)
	•	input: file + optional appName
	•	output: JSON with clusters + likely root causes + also returns markdown string

Endpoint 2: Monitoring Agent (Stub)

POST /api/monitoring/run-now
	•	for now:
	•	reads a sample file OR accepts file upload
	•	runs the same analyzer
	•	later:
	•	loops across 40 apps and queries Splunk/NR

⸻

Copilot prompt plan (copy/paste prompts)

Use these in order, one prompt per step. This produces consistent results.

Step 0 — Generate the project scaffold

Prompt (Ask / Agent mode):

Create a Spring Boot 3 (Java 17) Maven project named observability-agent with packages under com.company.obsagent. Include dependencies: spring-boot-starter-web, spring-boot-starter-validation, lombok, jackson-databind, snakeyaml, spring-boot-starter-test. Create folder structure exactly: api/, config/, core/ (model, parsing, clustering, runbook, reporting, service, util), integrations/ (splunk, pcf, newrelic). Add application.yml, and runbooks/pcf_java_common.yml under resources. Add a README with run commands and curl examples.

What you should check after this:
	•	pom.xml builds
	•	app starts: mvn spring-boot:run

⸻

Step 1 — Define models (strong spine)

Prompt:

Implement Java model classes in core/model using Lombok @Data or @Value:
	•	LogEvent: rawLine, timestamp (Instant nullable), level (enum), message
	•	ErrorCluster: signature, exceptionClass nullable, count, firstSeen, lastSeen, sampleLines (List)
	•	RunbookRule: id, pattern, cause, confidence (LOW/MEDIUM/HIGH), owner, fixes (List)
	•	RunbookMatch: ruleId, clusterSignature, cause, confidence, owner, fixes
	•	RootCause: cause, confidence, owner, evidenceSignature, fixes
	•	AnalysisResult: appName, deployDetected, deployWindowStart/End, topClusters, runbookMatches, likelyRootCauses, stats map
Include enums LogLevel and Confidence. Use Instant for timestamps.

⸻

Step 2 — Parsing (timestamps + levels)

Prompt:

Implement core/parsing/TimestampParser and core/parsing/LogParser.
TimestampParser should parse these formats best-effort:
	1.	yyyy-MM-dd HH:mm:ss.SSS
	2.	ISO-8601 like 2026-02-25T14:23:11.123Z or with offset
Return Optional.
LogParser should accept List lines and output List extracting timestamp and log level (INFO/WARN/ERROR/FATAL/DEBUG/TRACE) from each line using regex. If not found, keep nulls. Preserve rawLine.

⸻

Step 3 — Clustering (error detection + signatures)

Prompt:

Implement clustering classes under core/clustering:
	•	ErrorSignalDetector: method isError(LogEvent) returns true if level ERROR/FATAL OR message contains keywords Exception, Caused by, OutOfMemoryError, timeout, Connection refused, 502/503/504.
	•	SignatureNormalizer: normalize by replacing UUIDs, hex values, and standalone numbers with placeholders, collapse whitespace, limit length to 220 chars.
	•	ErrorClusterer: create clusters keyed by (exceptionClass + normalized signature), count occurrences, compute firstSeen/lastSeen from timestamps, keep up to 3 sample raw lines per cluster. Return clusters sorted by count desc.

⸻

Step 4 — Runbook (YAML loader + matcher)

Prompt:

Implement core/runbook/RunbookLoader and RunbookMatcher using SnakeYAML.
YAML structure: rules: [ {id, pattern, cause, confidence, owner, fix: [..]} ]
Map to RunbookRule where fix maps to fixes list.
RunbookMatcher should match rule pattern regex against cluster signature (case-insensitive) and produce RunbookMatch list.

Also ask Copilot to create the initial YAML file:

Prompt:

Create src/main/resources/runbooks/pcf_java_common.yml with at least 6 rules:
OutOfMemoryError/exit 137, HikariPool exhaustion, UnknownHost/Connection refused, 502/503/504 gorouter, Spring config missing (Could not resolve placeholder / BindException), RabbitMQ blocked/flow control keywords. Each rule must have owner and fixes list.

⸻

Step 5 — Analyzer service (the “agent brain”)

Prompt:

Implement core/service/DeploymentAnalysisService that:
	•	takes appName (optional), log text or lines, and runbook path
	•	parses events, detects deployment markers (“cf push”, “staging”, “starting app”, “download droplet”, “Started “, “staging failed”, “listening on port”) and sets deployDetected and window start/end using marker timestamps
	•	clusters errors and matches runbook rules on top 30 clusters
	•	computes likelyRootCauses (max 3) by scoring matches: score = confidenceWeight * clusterCount (LOW=1, MEDIUM=2, HIGH=3), dedupe by (cause, owner)
	•	returns AnalysisResult with top 10 clusters
Also implement a Redactor utility that masks patterns like Authorization headers, tokens, emails, and long numeric IDs before clustering/reporting. Apply redaction to raw lines before parsing.

⸻

Step 6 — Markdown report writer

Prompt:

Implement core/reporting/MarkdownReportWriter that converts AnalysisResult to a Markdown string with sections:
	•	Summary (deploy detected, window, total lines, #clusters)
	•	Top Error Clusters (count, signature, first/last seen, sample lines)
	•	Likely Root Causes & Fixes (cause, owner, confidence, evidence signature, fix steps)
Keep it readable and Confluence-friendly.

⸻

Step 7 — REST APIs (file upload)

Prompt:

Implement api/DeploymentAnalysisController with endpoint:
POST /api/deployments/analyze-file consuming multipart/form-data with fields:
	•	file (MultipartFile)
	•	appName (optional)
It should read file as UTF-8 safely, call DeploymentAnalysisService, return AnalyzeFileResponse containing AnalysisResult plus markdownReport string.
Add validation for max file size 15MB and return useful errors.

⸻

Step 8 — Monitoring module (stub now, scheduler later)

Prompt:

Implement core/service/MonitoringService and api/MonitoringController.
POST /api/monitoring/run-now should:
	•	for now load sample log file from resources (samples/logs.txt) OR accept optional file upload
	•	run analysis and return MonitoringRunResponse with summary + markdown.
Add a scheduled method placeholder (disabled by default) that later will iterate configured apps and call Splunk/NR collectors.

⸻

Step 9 — Configuration

Prompt:

Implement config/AppConfigProperties using @ConfigurationProperties(prefix="obsagent") with fields:
	•	runbookResourcePath (default “runbooks/pcf_java_common.yml”)
	•	maxUploadBytes
	•	monitoringEnabled (boolean)
	•	appInventory (List) for 40 apps later
Create application.yml with these defaults.

⸻

Step 10 — Tests (basic sanity)

Prompt:

Add JUnit tests:
	•	LogParserTest: parses timestamps and log levels from sample lines
	•	ErrorClustererTest: clusters repeated errors and counts correctly
Include one small sample log in test resources.

⸻

How you’ll extend collectors next (your plan)

Once file mode works, you add integrations step-by-step:
	1.	SplunkClient (REST)

	•	method: search(String query, Instant start, Instant end)
	•	return: list of raw log lines (or structured)
	•	plug it into DeploymentAnalysisService as an alternate “collector”

	2.	PcfClient (CF CLI or CF API)

	•	for v1, easiest is run cf events app via ProcessBuilder (local only)
	•	later move to Cloud Foundry API if allowed

	3.	NewRelicClient

	•	pull error rate and latency around deploy
	•	correlate with log clusters

This keeps the “brain” unchanged.

⸻

Two tips so Copilot doesn’t produce junk
	1.	Create empty files first in the exact folders, then ask Copilot to fill each file.
	2.	Tell Copilot: “Do not invent external systems; file-only for now; integrations are stubs.”

⸻

After you generate the code, run this local test
	1.	Start app:

	•	mvn spring-boot:run

	2.	Call endpoint:
