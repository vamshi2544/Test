# Observability Agent Service - Copilot Instructions

You are helping build a Java-only Spring Boot service called "observability-agent" that performs log-based diagnostics for PCF apps.

## Goals (Phased)
Phase 1 (now):
- Analyze a single local log file (uploaded via API) and generate:
  - Deployment window detection (best-effort)
  - Error clustering (group similar errors)
  - Runbook match (YAML rules -> fixes)
  - Outputs: JSON response + Markdown report

Phase 2+ (later):
- Replace file collector with integrations, one by one:
  1) Splunk REST API collector
  2) PCF (cf CLI or CF API) events collector
  3) New Relic collector
- Keep core analysis logic unchanged; only swap collectors.

## Non-negotiables
- Java 17, Spring Boot 3, Maven.
- Do NOT introduce Python, Node, or external workflow engines in core service.
- Do NOT include or request real credentials, tokens, or secrets in code or examples.
- Never log secrets; add redaction before analysis.
- Prefer deterministic, testable logic over “magic AI”.

## Architecture (must follow)
Use a single Spring Boot service with two modules (two workflows), sharing the same core engine:

Packages:
- com.company.obsagent.api
- com.company.obsagent.core
- com.company.obsagent.integrations (stubs only for Phase 1)

Core subpackages:
- core.model (DTOs: LogEvent, ErrorCluster, RunbookRule, Match, AnalysisResult, RootCause, Confidence, LogLevel)
- core.parsing (timestamp + level parsing)
- core.clustering (error detection + signature normalization + clustering)
- core.runbook (SnakeYAML loader + regex matcher)
- core.reporting (Markdown writer)
- core.service (DeploymentAnalysisService + MonitoringService)
- core.util (Redactor, File utils)

Integrations (Phase 1 is stubbed):
- integrations.splunk (SplunkClient interface + placeholder)
- integrations.pcf (PcfClient interface + placeholder)
- integrations.newrelic (NewRelicClient interface + placeholder)

## Endpoints (Phase 1)
1) POST /api/deployments/analyze-file
   - multipart/form-data: file (required), appName (optional)
   - returns JSON with AnalysisResult + markdownReport string
2) POST /api/monitoring/run-now
   - for now reads sample log from resources OR accepts optional file upload
   - returns summary + markdown

## Runbook
- Runbook file location: src/main/resources/runbooks/pcf_java_common.yml
- YAML schema: rules: [{id, pattern, cause, confidence, owner, fix:[...]}]
- Match rules against cluster signature using case-insensitive regex.
- Always include evidence signature with each recommended fix.

## Deployment window detection (best-effort)
Detect deploy markers such as:
- "cf push", "staging", "download droplet", "starting app", "uploaded droplet",
- Spring Boot "Started X in ... seconds", "listening on port", "staging failed"
Use marker timestamps to set window start/end if present.

## Error clustering rules (Phase 1)
Treat a line as error if:
- level is ERROR/FATAL, OR
- message contains: Exception, Caused by, OutOfMemoryError, timeout, Connection refused, 502/503/504.
Normalize signatures by replacing UUIDs, hex values, and standalone numbers with placeholders.
Keep only 3 sample lines per cluster.
Sort clusters by count desc.

## Output quality
- Produce clear, Confluence-ready Markdown.
- Avoid vague advice; only suggest fixes that map to runbook rules or clearly observed patterns.
- Add basic tests for parsing and clustering.

## Coding standards
- Use Lombok to reduce boilerplate.
- Use Instant for timestamps (nullable if not parsed).
- Validate upload size (default max 15 MB).
- Add redaction: mask Authorization headers, tokens, emails, long numeric IDs.
