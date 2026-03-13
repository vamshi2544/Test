# ApplyAdmin — Solution Architect Features Prompt

## API Version Change Tracker · Consumer Registry · API Readiness Scorecard · Onboarding Checklist

> **Author context:** Solution Architect (title: Team Lead) at Birlasoft / Synchrony Financial
> **Save to:** `docs/prompts/apply-admin-architect-features-prompt.md`
> **Use with:** GitHub Copilot Chat, Claude, or Cursor — one feature at a time
> **Builds on:** `apply-admin-features-prompt.md` — all existing conventions apply

-----

## 📦 App Context (Carry Forward)

```
App:        ApplyAdmin — Spring Boot + Thymeleaf + Redis + PCF
Auth:       Spring Security RBAC — logged-in user is default Admin
Existing:   /parser, /pr-review, /agentic, /dashboard,
            /pcf-health, /env-health, /redis-explorer,
            /runbook, /pulse, /weekly-summary, /shoutout, /solutioning
CSS:        IBM Plex Mono + Syne, dark theme, CSS variables (see previous prompt)
Context:    Both internal + external APIs, Apigee proxies, ISO 8583 payments domain
            5 API sub-teams, Solution Architect guiding all of them
```

-----

## 🏗 Architecture Story — Why These 4 Features Belong Together

```
                    ┌─────────────────────────┐
                    │  New Developer Joins     │
                    │  Onboarding Checklist    │ ← teaches standards on day 1
                    └────────────┬────────────┘
                                 ↓
                    ┌─────────────────────────┐
                    │  API Readiness Scorecard │ ← enforces standards before ship
                    │  "Is this API good       │
                    │   enough to release?"    │
                    └────────────┬────────────┘
                                 ↓
                    ┌─────────────────────────┐
                    │  Version Change Tracker  │ ← answers "what changed?"
                    │  "What broke between     │
                    │   v1 and v2?"            │
                    └────────────┬────────────┘
                                 ↓
                    ┌─────────────────────────┐
                    │  Consumer Registry       │ ← answers "who does this affect?"
                    │  "Which teams will       │
                    │   break if we change X?" │
                    └─────────────────────────┘
```

These four features give you **full lifecycle governance** — from design standards
through version changes through consumer impact. No other tool in your stack does this.
New Relic monitors after the fact. SwaggerHub stores specs. Jira tracks tickets.
This connects the human decisions in between.

-----

## 🎨 Design Tokens (Same as Previous Prompts)

```css
/* Already in applyadmin.css — do not duplicate, just reference */
var(--bg-primary), var(--bg-card), var(--border-default), var(--border-accent)
var(--accent-purple), var(--accent-purple-light), var(--accent-cyan)
var(--accent-green), var(--accent-amber), var(--accent-rose)
var(--text-primary), var(--text-secondary), var(--text-muted)
var(--gradient-brand), var(--gradient-progress)
```

-----

## 📁 New File Structure (Add to Existing App)

```
src/main/
├── java/com/applyadmin/
│   ├── controller/
│   │   ├── VersionTrackerController.java
│   │   ├── ConsumerRegistryController.java
│   │   ├── ReadinessScorecardController.java
│   │   └── OnboardingController.java
│   ├── service/
│   │   ├── VersionTrackerService.java
│   │   ├── ApiDiffService.java             ← core diff logic
│   │   ├── ConsumerRegistryService.java
│   │   ├── ReadinessScorecardService.java
│   │   └── OnboardingService.java
│   └── model/
│       ├── ApiVersionComparison.java
│       ├── ApiChange.java
│       ├── ApiConsumer.java
│       ├── ConsumerDependency.java
│       ├── ReadinessScorecard.java
│       ├── ScorecardCriterion.java
│       └── OnboardingChecklist.java
└── resources/
    └── templates/
        ├── version-tracker/
        │   ├── dashboard.html       ← history of all comparisons
        │   ├── compare.html         ← paste two specs, run diff
        │   └── report.html          ← detailed change report
        ├── consumer-registry/
        │   ├── dashboard.html       ← all APIs and their consumers
        │   ├── api-detail.html      ← one API, all consumers, impact view
        │   └── register.html        ← register a new consumer
        ├── readiness-scorecard/
        │   ├── dashboard.html       ← all APIs and their scores
        │   ├── scorecard.html       ← fill/view a scorecard
        │   └── criteria-admin.html  ← admin: manage scorecard criteria
        └── onboarding/
            ├── checklist.html       ← new dev's checklist view
            └── admin.html           ← admin: manage checklist template
```

-----

## 🔵 FEATURE 1: API Version Change Tracker

### What It Does

Developers paste or upload two OpenAPI/Swagger JSON specs.
The tool diffs them, categorizes every change as Breaking or Non-Breaking,
and produces a readable change report — not a raw text diff.
All comparisons saved to Redis. Over time, builds a version history per API.

**Directly solves:** “Figuring out what changed between API versions”

### Routes

```
GET  /version-tracker                    → History dashboard
GET  /version-tracker/compare            → New comparison form
POST /version-tracker/compare/run        → Run the diff, show report
GET  /version-tracker/report/{id}        → View saved report
POST /version-tracker/report/{id}/delete → Delete (admin)
```

### Data Models

```java
public class ApiVersionComparison {
    private String id;               // UUID
    private String apiName;          // "Payment Gateway API"
    private String fromVersion;      // "v1.2.0"
    private String toVersion;        // "v2.0.0"
    private String fromSpecJson;     // Raw OpenAPI JSON (spec A)
    private String toSpecJson;       // Raw OpenAPI JSON (spec B)
    private List<ApiChange> changes; // All detected changes
    private int breakingCount;       // Computed from changes
    private int nonBreakingCount;
    private String submittedBy;      // Spring Security principal
    private LocalDateTime comparedAt;
    private String summary;          // One-line plain English summary
}

public class ApiChange {
    private String changeType;       // "ENDPOINT_REMOVED" | "FIELD_REMOVED" |
                                     // "FIELD_TYPE_CHANGED" | "FIELD_REQUIRED_ADDED" |
                                     // "ENUM_VALUE_REMOVED" | "ENDPOINT_ADDED" |
                                     // "FIELD_ADDED_OPTIONAL" | "DESCRIPTION_CHANGED" |
                                     // "RESPONSE_CODE_REMOVED" | "PARAM_RENAMED"
    private String severity;         // "BREAKING" | "NON_BREAKING" | "INFO"
    private String location;         // e.g. "POST /payments/v1/authorize → requestBody → cardNumber"
    private String description;      // Human-readable: "Field 'cardNumber' removed from request body"
    private String fromValue;        // What it was
    private String toValue;          // What it is now
    private String impact;           // "Consumers sending this field will receive 400"
}
```

### Redis Key Schema

```
version:comparison:{id}        → JSON ApiVersionComparison
version:index                  → JSON List of {id, apiName, fromVersion, toVersion,
                                   breakingCount, comparedAt} — for listing
version:apis                   → Set of all API names (for autocomplete/filter)
```

### Core Diff Service — ApiDiffService.java

This is the heart of the feature. Build it as a pure Java service with no external
dependencies. Parse both specs as Jackson JsonNode trees and walk them structurally.

```java
@Service
public class ApiDiffService {

    /**
     * Entry point — diff two OpenAPI JSON spec strings
     * Returns ordered list of changes: BREAKING first, then NON_BREAKING, then INFO
     */
    public List<ApiChange> diff(String specAJson, String specBJson);

    // ---- Private diff methods — one per OpenAPI section ----

    // Walk paths object — detect added/removed/changed endpoints
    private List<ApiChange> diffPaths(JsonNode pathsA, JsonNode pathsB);

    // For a single endpoint — diff request body schema
    private List<ApiChange> diffRequestBody(String path, String method,
                                             JsonNode bodyA, JsonNode bodyB);

    // For a single endpoint — diff response schemas per status code
    private List<ApiChange> diffResponses(String path, String method,
                                           JsonNode responsesA, JsonNode responsesB);

    // For a single endpoint — diff query/path/header parameters
    private List<ApiChange> diffParameters(String path, String method,
                                            JsonNode paramsA, JsonNode paramsB);

    // Recursively diff a JSON schema object — detect field add/remove/type change
    private List<ApiChange> diffSchema(String location, JsonNode schemaA, JsonNode schemaB);

    // Detect enum value changes
    private List<ApiChange> diffEnum(String location, JsonNode enumA, JsonNode enumB);
}
```

### Breaking vs Non-Breaking Classification

```java
// BREAKING changes — these break existing consumers:
ENDPOINT_REMOVED          // DELETE /payments/v1/refund disappeared
FIELD_REMOVED             // Required or optional field removed from request/response
FIELD_TYPE_CHANGED        // string → integer, object → array
FIELD_REQUIRED_ADDED      // Optional field became required in request
ENUM_VALUE_REMOVED        // Enum lost a value consumers might be sending
RESPONSE_CODE_REMOVED     // Was returning 201, now never returns 201
PARAM_RENAMED             // Query param ?accountId= renamed to ?account_id=
SECURITY_ADDED            // Endpoint now requires auth that didn't before

// NON-BREAKING changes — safe for existing consumers:
ENDPOINT_ADDED            // New endpoint added
FIELD_ADDED_OPTIONAL      // New optional field in request or response
FIELD_REQUIRED_REMOVED    // Required field became optional
ENUM_VALUE_ADDED          // New enum value added
RESPONSE_CODE_ADDED       // New success/error code added

// INFO — worth knowing but no action needed:
DESCRIPTION_CHANGED       // Documentation text changed
EXAMPLE_CHANGED           // Example values updated
TITLE_CHANGED             // API title changed
```

### Controller

```java
@Controller
@RequestMapping("/version-tracker")
public class VersionTrackerController {

    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("comparisons", versionTrackerService.getAllComparisons());
        model.addAttribute("apiNames", versionTrackerService.getAllApiNames());
        return "version-tracker/dashboard";
    }

    @GetMapping("/compare")
    public String compareForm() {
        return "version-tracker/compare";
    }

    @PostMapping("/compare/run")
    public String runComparison(
            @RequestParam String apiName,
            @RequestParam String fromVersion,
            @RequestParam String toVersion,
            @RequestParam String specAJson,
            @RequestParam String specBJson,
            Principal principal,
            RedirectAttributes redirectAttributes) {

        List<ApiChange> changes = apiDiffService.diff(specAJson, specBJson);

        ApiVersionComparison comparison = new ApiVersionComparison();
        comparison.setApiName(apiName);
        comparison.setFromVersion(fromVersion);
        comparison.setToVersion(toVersion);
        comparison.setFromSpecJson(specAJson);
        comparison.setToSpecJson(specBJson);
        comparison.setChanges(changes);
        comparison.setBreakingCount((int) changes.stream()
            .filter(c -> "BREAKING".equals(c.getSeverity())).count());
        comparison.setNonBreakingCount((int) changes.stream()
            .filter(c -> "NON_BREAKING".equals(c.getSeverity())).count());
        comparison.setSubmittedBy(principal.getName());
        comparison.setComparedAt(LocalDateTime.now());

        String id = versionTrackerService.save(comparison);
        return "redirect:/version-tracker/report/" + id;
    }

    @GetMapping("/report/{id}")
    public String viewReport(@PathVariable String id, Model model) {
        ApiVersionComparison comparison = versionTrackerService.getById(id);
        model.addAttribute("comparison", comparison);
        model.addAttribute("breakingChanges", comparison.getChanges().stream()
            .filter(c -> "BREAKING".equals(c.getSeverity())).collect(Collectors.toList()));
        model.addAttribute("nonBreakingChanges", comparison.getChanges().stream()
            .filter(c -> "NON_BREAKING".equals(c.getSeverity())).collect(Collectors.toList()));
        return "version-tracker/report";
    }
}
```

### Thymeleaf Templates

#### `version-tracker/dashboard.html`

```html
<!-- Header: "API Version Change Tracker" | [+ New Comparison] button -->
<!-- Filter by API name (dropdown of all saved API names) -->
<!-- Comparison history table: -->
<!--   API Name | From Version | To Version | Breaking Count (red if > 0) -->
<!--   Non-Breaking Count | Compared By | Date | [View Report] -->
<!-- Breaking count badge: red pill if > 0, green if 0 ("✓ Safe") -->
<!-- Sort: most recent first -->
```

#### `version-tracker/compare.html`

```html
<!-- Header: "Compare API Versions" -->
<!-- Form fields: -->
<!--   API Name (text input with datalist autocomplete from saved names) -->
<!--   From Version (text: "v1.2.0") -->
<!--   To Version (text: "v2.0.0") -->
<!--   Spec A textarea: "Paste OpenAPI JSON for FROM version" -->
<!--   Spec B textarea: "Paste OpenAPI JSON for TO version" -->
<!--   Helper text: "Get your spec JSON from SwaggerHub → Export → JSON" -->
<!--   [Run Comparison →] button -->
<!-- Note: both textareas should be large (rows=20) with monospace font -->
```

#### `version-tracker/report.html`

```html
<!-- Header: "{apiName}: {fromVersion} → {toVersion}" | date | compared by -->
<!-- Summary banner: -->
<!--   If breakingCount > 0: RED banner "⚠ {N} Breaking Changes — Review Before Releasing" -->
<!--   If breakingCount == 0: GREEN banner "✓ No Breaking Changes — Safe to Release" -->
<!-- Stats row: Breaking count (red) | Non-Breaking count (green) | Total changes -->

<!-- BREAKING CHANGES section (shown first, only if count > 0): -->
<!--   Each change card: -->
<!--     Change type badge (red) | Location path | Description -->
<!--     From value → To value (if applicable) -->
<!--     Impact statement (what breaks for consumers) -->

<!-- NON-BREAKING CHANGES section: -->
<!--   Collapsed by default, expand toggle -->
<!--   Each change: type badge (green) | location | description -->

<!-- INFO section: -->
<!--   Collapsed by default -->

<!-- [Export as Text] button → generates plain text report for email/Slack -->
<!-- [Check Consumer Impact] button → links to Consumer Registry filtered by this API -->
```

### Notes for Copilot

- Use Jackson `ObjectMapper.readTree()` to parse spec strings — do not use string matching
- Handle malformed JSON gracefully — show validation error before running diff
- The `diffSchema()` method must be recursive — schemas can be deeply nested
- `allOf`, `oneOf`, `anyOf` in OpenAPI — handle by flattening to a merged schema for comparison
- Store full spec JSON in Redis but only store change summary in the index (keep index lean)
- When generating the text export, format it as something you’d paste into a Teams/Slack message

-----

## 🟠 FEATURE 2: API Consumer Registry

### What It Does

A registry of who is consuming your APIs, which version they’re on, and
which environment they’re in. Phase 1 is manual — teams register themselves.
Built to grow: later wire Apigee proxy header logs to auto-populate.

**Directly solves:** “No visibility into who is consuming our APIs”
**Architect value:** Quantifies blast radius of any change before you make it.

### Routes

```
GET  /consumer-registry                      → Dashboard — all APIs + consumer counts
GET  /consumer-registry/api/{apiName}        → One API's consumers + impact view
GET  /consumer-registry/register             → Registration form
POST /consumer-registry/register             → Save consumer
GET  /consumer-registry/consumer/{id}/edit  → Edit consumer record
POST /consumer-registry/consumer/{id}/update → Update
POST /consumer-registry/consumer/{id}/delete → Delete (admin)
GET  /consumer-registry/impact/{apiName}     → Impact analysis view
```

### Data Models

```java
public class ApiConsumer {
    private String id;                  // UUID
    private String apiName;             // Which API being consumed: "Payment Gateway API"
    private String apiVersion;          // Which version: "v1", "v2.1"
    private String consumerSystemName;  // Name of consuming system: "Mobile Banking App"
    private String consumerTeam;        // Team that owns the consumer: "Digital Team"
    private String consumerContact;     // Name or email of contact person
    private String environment;         // "DEV" | "QA" | "PROD" | "ALL"
    private String integrationType;     // "INTERNAL" | "EXTERNAL_PARTNER" | "EXTERNAL_PUBLIC"
    private String usagePattern;        // "REAL_TIME" | "BATCH" | "EVENT_DRIVEN"
    private String criticalityLevel;    // "HIGH" | "MEDIUM" | "LOW"
                                        // HIGH = this breaks, consumer's system breaks
    private String endpointsUsed;       // Comma-separated list: "POST /authorize, GET /status"
    private String notes;               // Any special considerations
    private LocalDateTime registeredAt;
    private LocalDateTime lastUpdated;
    private String registeredBy;        // Spring Security principal
}

public class ConsumerDependency {
    // Used for the reverse view: given a consumer system, what APIs does it depend on?
    private String consumerSystemName;
    private List<ApiConsumer> dependencies;
}
```

### Redis Key Schema

```
consumer:{id}                          → JSON ApiConsumer
consumer:by-api:{apiName}             → Set of consumer IDs for this API
consumer:by-system:{consumerSystem}   → Set of consumer IDs for this system
consumer:index                         → JSON List of all consumer summaries (for listing)
consumer:apis                          → Set of all API names (for dropdowns)
consumer:systems                       → Set of all consumer system names
```

### Service Layer

```java
@Service
public class ConsumerRegistryService {

    public List<ApiConsumer> getAllConsumers();

    // Get all consumers for a specific API
    public List<ApiConsumer> getConsumersByApi(String apiName);

    // Get all APIs a specific system consumes (reverse lookup)
    public List<ApiConsumer> getDependenciesBySystem(String systemName);

    // Impact analysis: if this API changes, who is affected?
    // Returns grouped by criticality: HIGH first
    public Map<String, List<ApiConsumer>> getImpactAnalysis(String apiName);

    // Get consumer count per API (for dashboard summary)
    public Map<String, Long> getConsumerCountByApi();

    // How many consumers are on an old version?
    // e.g. API has v2 but 3 consumers still on v1
    public List<ApiConsumer> getConsumersOnOldVersion(String apiName, String latestVersion);

    public ApiConsumer getById(String id);
    public void register(ApiConsumer consumer, String registeredBy);
    public void update(String id, ApiConsumer updated);
    public void delete(String id);

    // Get all unique API names (for dropdowns and autocomplete)
    public Set<String> getAllApiNames();
}
```

### Controller

```java
@Controller
@RequestMapping("/consumer-registry")
public class ConsumerRegistryController {

    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("consumerCountByApi", registryService.getConsumerCountByApi());
        model.addAttribute("totalConsumers", registryService.getAllConsumers().size());
        model.addAttribute("highCriticalityCount",
            registryService.getAllConsumers().stream()
                .filter(c -> "HIGH".equals(c.getCriticalityLevel())).count());
        model.addAttribute("apiNames", registryService.getAllApiNames());
        return "consumer-registry/dashboard";
    }

    @GetMapping("/api/{apiName}")
    public String apiDetail(@PathVariable String apiName, Model model) {
        List<ApiConsumer> consumers = registryService.getConsumersByApi(apiName);
        Map<String, List<ApiConsumer>> impactGroups = registryService.getImpactAnalysis(apiName);
        model.addAttribute("apiName", apiName);
        model.addAttribute("consumers", consumers);
        model.addAttribute("impactGroups", impactGroups);
        model.addAttribute("environments", consumers.stream()
            .collect(Collectors.groupingBy(ApiConsumer::getEnvironment)));
        return "consumer-registry/api-detail";
    }

    @GetMapping("/impact/{apiName}")
    public String impactView(@PathVariable String apiName,
                              @RequestParam(required=false) String fromVersion,
                              @RequestParam(required=false) String toVersion,
                              Model model) {
        // Show: if we change apiName from fromVersion to toVersion, who breaks?
        List<ApiConsumer> affected = registryService.getConsumersOnOldVersion(apiName, toVersion);
        model.addAttribute("apiName", apiName);
        model.addAttribute("affectedConsumers", affected);
        model.addAttribute("fromVersion", fromVersion);
        model.addAttribute("toVersion", toVersion);
        return "consumer-registry/api-detail"; // reuse template, different mode
    }
}
```

### Thymeleaf Templates

#### `consumer-registry/dashboard.html`

```html
<!-- Header: "API Consumer Registry" | Total APIs | Total Consumers | [+ Register Consumer] -->
<!-- Alert row: "X consumers are HIGH criticality" (amber banner if > 0) -->

<!-- API cards grid: one card per unique API name -->
<!--   Card: API name | consumer count badge | environments covered -->
<!--   Consumer count by type: Internal N | External Partner N | External Public N -->
<!--   Click → /consumer-registry/api/{apiName} -->

<!-- All Consumers table (below grid, collapsible): -->
<!--   API Name | Consumer System | Team | Version | Environment | Criticality | Contact -->
<!--   Sort by: API name | Criticality | Registration date -->
<!--   Filter: by environment, by criticality, by integration type -->

<!-- Side panel or section: "Systems & Their Dependencies" -->
<!--   Each unique consumer system → how many APIs it depends on -->
<!--   Click → filtered view showing all that system's dependencies -->
```

#### `consumer-registry/api-detail.html`

```html
<!-- Header: "{apiName}" | {total consumer count} consumers -->

<!-- Impact Summary banner: -->
<!--   HIGH criticality consumers: N (red if > 0) -->
<!--   Environments covered: DEV / QA / PROD indicators -->
<!--   Versions in use: v1 (N consumers), v2 (N consumers) — flag old versions -->

<!-- Consumer table grouped by Criticality (HIGH first): -->
<!--   System Name | Team | Contact | Version | Environment | Usage Pattern | Endpoints Used -->
<!--   HIGH = red left border, MEDIUM = amber, LOW = gray -->

<!-- Version Distribution section: -->
<!--   Bar showing % of consumers on each version -->
<!--   If any consumer on outdated version: "3 consumers still on v1 — at risk from v2 changes" -->

<!-- [Check Version Impact] button: links to Version Tracker filtered by this API -->
<!-- [Export Consumer List] → plain text/CSV for sharing with stakeholders -->
```

#### `consumer-registry/register.html`

```html
<!-- Header: "Register API Consumer" -->
<!-- Form: -->
<!--   API Name (text + datalist from known APIs) -->
<!--   API Version being consumed (text) -->
<!--   Consumer System Name (text) -->
<!--   Consumer Team (text) -->
<!--   Contact Person (text) -->
<!--   Environment (multi-select: DEV / QA / PROD / ALL) -->
<!--   Integration Type (radio: Internal / External Partner / External Public) -->
<!--   Usage Pattern (radio: Real-Time / Batch / Event-Driven) -->
<!--   Criticality Level (radio: HIGH / MEDIUM / LOW) -->
<!--   Helper text for criticality: -->
<!--     HIGH = our system breaks if this API is down/changed without notice -->
<!--     MEDIUM = degraded functionality -->
<!--     LOW = non-critical path, fallback exists -->
<!--   Endpoints Used (textarea: "POST /authorize, GET /status/{id}") -->
<!--   Notes (textarea: optional) -->
<!--   [Register Consumer] button -->
```

### Notes for Copilot

- The Redis `consumer:by-api:{apiName}` set enables fast lookup without scanning all consumers
- For `getConsumersOnOldVersion()`: get all consumers for the API, filter where `apiVersion != latestVersion`
- API names should be normalized to lowercase + trimmed before storing to prevent duplicates
- The impact analysis groups consumers by `criticalityLevel` — HIGH consumers should always appear first
- Export function: generate a plain text table formatted for pasting into email

-----

## 🟢 FEATURE 3: API Readiness Scorecard

### What It Does

Teams self-assess their API against YOUR defined standards before shipping.
Score is a percentage. Visible to everyone. Peer pressure does the enforcement.
You set the minimum score threshold for production. Below that — the card is red.
New developers use this to learn your standards on day 1.

**Directly solves:** “Standards enforcement — teams do things differently”
**Architect value:** You stop being the bottleneck for standards review.

### Routes

```
GET  /scorecard                             → Dashboard — all APIs and their scores
GET  /scorecard/api/{apiName}               → View latest scorecard for an API
GET  /scorecard/api/{apiName}/fill          → Fill/update scorecard form
POST /scorecard/api/{apiName}/save          → Save scorecard
GET  /scorecard/criteria                    → Admin: view all criteria
GET  /scorecard/criteria/edit               → Admin: add/edit/reorder criteria
POST /scorecard/criteria/save               → Admin: save criteria changes
GET  /scorecard/history/{apiName}           → All scorecards over time for one API
```

### Data Models

```java
public class ScorecardCriterion {
    private String id;              // UUID
    private String category;        // "Error Handling" | "Security" | "API Design" |
                                    // "Observability" | "Documentation" | "Operations"
    private int categoryWeight;     // Points this category contributes to total score
    private String criterion;       // "All errors return standard error schema with correlationId"
    private String description;     // More detail, examples, why it matters
    private String howToCheck;      // "Look at error response in Postman for a 400 call"
    private boolean required;       // If true, failing this = automatic disqualification
    private int displayOrder;
    private boolean active;         // Can be deactivated without deleting
}

public class ReadinessScorecard {
    private String id;              // UUID
    private String apiName;
    private String apiVersion;
    private String teamName;
    private String filledBy;        // Spring Security principal
    private LocalDateTime assessedAt;
    private List<ScorecardResponse> responses;
    private int totalScore;         // 0-100 calculated
    private String grade;           // "A" (90+) | "B" (80+) | "C" (70+) | "D" (60+) | "F"
    private boolean productionReady; // true if totalScore >= threshold AND no required failures
    private String overallNotes;    // Free text: anything else the team wants to note
    private String status;          // "DRAFT" | "SUBMITTED" | "REVIEWED"
    private String reviewedBy;      // Architect/lead who reviewed (optional)
    private String reviewNotes;     // Feedback from architect
}

public class ScorecardResponse {
    private String criterionId;
    private String result;          // "YES" | "NO" | "PARTIAL" | "NA"
    private String notes;           // Evidence or explanation
    private int pointsEarned;       // Calculated from criterion weight and result
}
```

### Redis Key Schema

```
scorecard:criteria                    → JSON List<ScorecardCriterion> (your standards)
scorecard:threshold                   → int (minimum score for production, default 80)
scorecard:{id}                        → JSON ReadinessScorecard
scorecard:latest:{apiName}            → String ID of latest scorecard for this API
scorecard:history:{apiName}           → JSON List of scorecard IDs for this API
scorecard:index                       → JSON List of all scorecard summaries
```

### Default Criteria (Seed These on First Run)

These are tailored for your context — Synchrony, ISO 8583, Apigee, PCF.
Store them in Redis on application startup if `scorecard:criteria` key is empty.

```java
List<ScorecardCriterion> defaultCriteria = Arrays.asList(

    // ERROR HANDLING — 20 points total
    buildCriterion("Error Handling", 5, "All error responses include correlationId, errorCode, and message",
        "Check 400, 404, 500 responses in Swagger and test in Postman", true),
    buildCriterion("Error Handling", 5, "HTTP status codes used correctly — no 200 on errors",
        "A failed payment must not return HTTP 200 with an error body", true),
    buildCriterion("Error Handling", 5, "Timeout errors return 504 not 500",
        "Simulate downstream timeout and verify response code", false),
    buildCriterion("Error Handling", 5, "Validation errors return 400 with field-level detail",
        "Send invalid payload — response should list which fields failed", false),

    // SECURITY — 25 points total
    buildCriterion("Security", 10, "No PCI fields in GET query parameters",
        "Check all GET endpoints — no cardNumber, cvv, expiry in ?params", true),
    buildCriterion("Security", 5, "No PCI fields appear in error messages or log output",
        "Trigger validation error on PCI field — check response body and logs", true),
    buildCriterion("Security", 5, "Authorization header required and validated on all endpoints",
        "Call endpoint without auth header — must return 401", true),
    buildCriterion("Security", 5, "Rate limiting configured on Apigee proxy",
        "Check Apigee proxy policies for SpikeArrest or QuotaPolicy", false),

    // API DESIGN — 20 points total
    buildCriterion("API Design", 5, "Versioning in URL path (/v1/, /v2/)",
        "All endpoint paths must start with /v{n}/", false),
    buildCriterion("API Design", 5, "Consistent field naming convention across all endpoints",
        "All fields must use camelCase — no mixing with snake_case", false),
    buildCriterion("API Design", 5, "Pagination on all list endpoints (page, size, totalCount)",
        "Any endpoint returning a list must support pagination params", false),
    buildCriterion("API Design", 5, "Idempotency key supported on POST endpoints that create resources",
        "POST /payments must support X-Idempotency-Key header", false),

    // OBSERVABILITY — 15 points total
    buildCriterion("Observability", 5, "CorrelationId passed through all downstream calls and logged",
        "Trace a request through logs — correlationId must appear in each log line", false),
    buildCriterion("Observability", 5, "PCF /actuator/health endpoint exposed and registered in ApplyAdmin",
        "Check /actuator/health returns UP and is in PCF Health Dashboard", false),
    buildCriterion("Observability", 5, "Log levels used correctly — not everything at INFO",
        "DEBUG for trace data, INFO for business events, ERROR for failures only", false),

    // DOCUMENTATION — 10 points total
    buildCriterion("Documentation", 5, "OpenAPI spec in SwaggerHub is up to date with implementation",
        "Compare spec to actual endpoints — no undocumented endpoints or fields", false),
    buildCriterion("Documentation", 5, "Each endpoint has description, request/response examples",
        "Open SwaggerHub — every endpoint must have a description and example", false),

    // OPERATIONS — 10 points total
    buildCriterion("Operations", 5, "Runbook exists in ApplyAdmin for common failure scenarios",
        "Search /runbook for this API name — at least one runbook must exist", false),
    buildCriterion("Operations", 5, "Environment URLs registered in ApplyAdmin Env Health Checker",
        "Check /env-health — DEV, QA, PROD URLs for this API must be listed", false)
);
```

### Score Calculation Logic

```java
public int calculateScore(List<ScorecardResponse> responses,
                           List<ScorecardCriterion> criteria) {
    int totalPossible = criteria.stream()
        .filter(ScorecardCriterion::isActive)
        .filter(c -> !"NA".equals(getResponseForCriterion(responses, c.getId()).getResult()))
        .mapToInt(ScorecardCriterion::getCategoryWeight)
        .sum();

    int totalEarned = responses.stream()
        .mapToInt(r -> {
            switch (r.getResult()) {
                case "YES":     return r.getPointsEarned();  // full points
                case "PARTIAL": return r.getPointsEarned() / 2; // half points
                case "NO":      return 0;
                case "NA":      return 0; // excluded from total possible too
                default:        return 0;
            }
        }).sum();

    return totalPossible > 0 ? (totalEarned * 100) / totalPossible : 0;
}

public boolean isProductionReady(ReadinessScorecard scorecard,
                                  List<ScorecardCriterion> criteria) {
    int threshold = getThreshold(); // from Redis, default 80

    // Must meet score threshold
    if (scorecard.getTotalScore() < threshold) return false;

    // Must not fail any REQUIRED criterion
    List<String> requiredCriterionIds = criteria.stream()
        .filter(ScorecardCriterion::isRequired)
        .map(ScorecardCriterion::getId)
        .collect(Collectors.toList());

    return scorecard.getResponses().stream()
        .filter(r -> requiredCriterionIds.contains(r.getCriterionId()))
        .noneMatch(r -> "NO".equals(r.getResult()));
}
```

### Thymeleaf Templates

#### `scorecard/dashboard.html`

```html
<!-- Header: "API Readiness Scorecard" | Production threshold: {N}% | [Admin: Edit Criteria] -->

<!-- Summary stats: Total APIs assessed | Production Ready | Below Threshold | Not Yet Assessed -->

<!-- API scorecard grid: one card per API -->
<!--   Card: API name | team | latest version assessed | Score % (big number) | Grade badge -->
<!--   Score color: >= threshold = green, 60-threshold = amber, < 60 = red -->
<!--   Production Ready: green checkmark or red X -->
<!--   "Last assessed: {date} by {user}" -->
<!--   [View Scorecard] [Fill New Assessment] buttons -->

<!-- APIs not yet assessed: list of API names from Consumer Registry with no scorecard -->
<!-- "These APIs have consumers but no readiness assessment" (amber alert) -->
```

#### `scorecard/scorecard.html` (fill or view)

```html
<!-- Header: "{apiName} Readiness Assessment" | version | team | date -->
<!-- Production Ready banner at top (green/red) — updates as form is filled -->
<!-- Score meter: large percentage, color-coded, updates dynamically as user answers -->

<!-- Grouped by category: -->
<!--   Category header: name | category score X/Y points -->
<!--   Each criterion: -->
<!--     Criterion text (bold) -->
<!--     Description (smaller, muted) -->
<!--     "How to check" hint (italic, collapsible) -->
<!--     Required badge (if required=true: "Required — failing this blocks prod") -->
<!--     Radio group: YES | PARTIAL | NO | N/A -->
<!--     Notes textarea (appears when PARTIAL or NO selected) -->

<!-- Overall Notes textarea at bottom -->
<!-- [Save as Draft] [Submit Assessment] buttons -->
<!-- Architect review section (admin only): reviewNotes textarea, [Mark as Reviewed] button -->
```

#### `scorecard/criteria-admin.html`

```html
<!-- Header: "Manage Scorecard Criteria" | [+ Add Criterion] | [Set Threshold] -->
<!-- Current threshold: input number (80 default), [Save Threshold] -->
<!-- Criteria list grouped by category: -->
<!--   Drag to reorder (or up/down arrows) -->
<!--   Each row: category | criterion text | weight | required? | active toggle -->
<!--   [Edit] [Deactivate] per row -->
<!-- Add/Edit form: all fields from ScorecardCriterion model -->
<!-- Note: deactivate instead of delete — preserve historical scorecard data integrity -->
```

### Notes for Copilot

- Seed default criteria on `@PostConstruct` if `scorecard:criteria` key is empty in Redis
- Score calculation runs client-side in JavaScript for real-time feedback as form is filled
- Also recalculate server-side on save — do not trust client-side score
- Required criteria failures must be visually distinct — red border, warning icon
- The `NA` option excludes a criterion from score calculation entirely — useful for APIs where
  some criteria genuinely don’t apply (e.g. no list endpoints → pagination criterion = NA)
- Admin can set the production threshold via UI — store in Redis key `scorecard:threshold`

-----

## 🟡 FEATURE 4: Onboarding Checklist

### What It Does

A living checklist for new developers joining any of your 5 teams.
Links directly to other ApplyAdmin features — Environment Checker, Runbook Library,
Agentic AI Hub, Scorecard. One place that answers “what do I do in my first 2 weeks?”
Tracks per-user completion. Admin maintains the template.

**Directly solves:** “Onboarding — new devs take too long to be productive”

### Routes

```
GET  /onboarding                     → Checklist for logged-in user
POST /onboarding/item/{id}/complete  → Mark item complete
POST /onboarding/item/{id}/undo      → Mark item incomplete
GET  /onboarding/admin               → Admin: manage checklist template
POST /onboarding/admin/save          → Admin: save template changes
GET  /onboarding/progress            → Admin: view all users' progress
```

### Data Models

```java
public class OnboardingItem {
    private String id;
    private String week;           // "Week 1 - Day 1" | "Week 1" | "Week 2" | "Month 1"
    private String category;       // "Access & Setup" | "Tools" | "Learning" | "Standards" | "First Task"
    private String title;          // Short action title
    private String description;    // What to do and why
    private String linkUrl;        // Internal ApplyAdmin link or external
    private String linkLabel;      // "Open Environment Checker" etc
    private boolean required;      // Must-complete vs nice-to-have
    private int displayOrder;
}

public class OnboardingProgress {
    private String userId;
    private String teamName;
    private Map<String, Boolean> completedItems; // itemId → true/false
    private LocalDateTime startedAt;
    private LocalDateTime lastActivityAt;
}
```

### Redis Key Schema

```
onboarding:template           → JSON List<OnboardingItem>
onboarding:progress:{userId}  → JSON OnboardingProgress
onboarding:users:index        → Set of all userIds with onboarding started
```

### Default Checklist Template (Seed on First Run)

Pre-populate with items that link directly to your ApplyAdmin features:

```java
List<OnboardingItem> defaultItems = Arrays.asList(

    // Week 1 Day 1 — Access & Setup
    buildItem("Week 1 - Day 1", "Access & Setup",
        "Get PCF access and verify you can deploy",
        "Request PCF org/space access from your team lead. Deploy a hello-world app to confirm.",
        null, null, true),

    buildItem("Week 1 - Day 1", "Access & Setup",
        "Bookmark ApplyAdmin — your daily tool",
        "This is your command center. Bookmark it now.",
        "/dashboard", "Open Dashboard", true),

    buildItem("Week 1 - Day 1", "Access & Setup",
        "Find all environment URLs for your team's APIs",
        "Don't ask teammates for URLs. They're all here.",
        "/env-health", "Open Env Health Checker", true),

    // Week 1 — Standards
    buildItem("Week 1", "Standards",
        "Read the API Readiness Scorecard criteria",
        "This is what 'done' looks like for an API at Synchrony. Read every criterion.",
        "/scorecard/criteria", "View Scorecard Criteria", true),

    buildItem("Week 1", "Standards",
        "Review a submitted scorecard for an existing API",
        "See how a real API was assessed. Understand the standards in context.",
        "/scorecard", "Open Scorecard Dashboard", false),

    // Week 1 — Learning
    buildItem("Week 1", "Learning",
        "Complete Agentic AI Hub — Track 1: Foundations (Modules 1-4)",
        "4 modules, ~2 hours total. Understand how AI agents work and why it matters for API teams.",
        "/agentic", "Open Learning Hub", false),

    // Week 1 — Operations
    buildItem("Week 1", "Tools",
        "Read the runbooks for your team's top 3 APIs",
        "When something breaks at 2am, you need to know this already.",
        "/runbook", "Open Runbook Library", true),

    buildItem("Week 1", "Tools",
        "Check PCF Health Dashboard — know what green looks like",
        "Familiarize yourself with what healthy looks like so you recognize unhealthy.",
        "/pcf-health", "Open PCF Health Dashboard", false),

    // Week 2 — Deeper
    buildItem("Week 2", "Standards",
        "Run a version comparison on any two API versions",
        "Pick any API with 2 versions. Run a diff. Read the change report.",
        "/version-tracker/compare", "Open Version Tracker", false),

    buildItem("Week 2", "Standards",
        "Register yourself as a consumer of at least one API",
        "If your work consumes any API, register it. This keeps our registry accurate.",
        "/consumer-registry/register", "Register a Consumer", true),

    buildItem("Week 2", "Learning",
        "Complete Agentic AI Hub — Track 2: Architecture (Modules 5-8)",
        "Multi-agent patterns, LangGraph, Agentic RAG. The architecture side of AI.",
        "/agentic", "Open Learning Hub", false),

    // Month 1 — First real task
    buildItem("Month 1", "First Task",
        "Fill a Readiness Scorecard for an API you've worked on",
        "Pick an API you understand. Self-assess it. This is how you demonstrate standards knowledge.",
        "/scorecard", "Open Scorecard", true),

    buildItem("Month 1", "First Task",
        "Post your first Weekly Team Pulse",
        "Join the team rhythm. Friday pulse takes 5 minutes.",
        "/pulse/submit", "Submit Pulse", false),

    buildItem("Month 1", "First Task",
        "Add a Shoutout for a teammate who helped you onboard",
        "Someone helped you. Thank them publicly.",
        "/shoutout", "Open Shoutout Board", false)
);
```

### Thymeleaf Templates

#### `onboarding/checklist.html`

```html
<!-- Header: "Welcome to the API Team, {username}" | Team: {teamName} -->
<!-- Progress bar: X of Y required items complete -->
<!-- "Production Ready" milestone: shows when all required items done (green banner) -->

<!-- Timeline grouped by week: "Week 1 - Day 1" | "Week 1" | "Week 2" | "Month 1" -->
<!--   Each item: -->
<!--     Checkbox (POST to complete/undo on click) -->
<!--     Title (strikethrough when done) -->
<!--     Description -->
<!--     Link button if linkUrl exists (opens in new tab) -->
<!--     Required badge if required=true -->
<!--     Category badge (Access/Standards/Learning/Tools/First Task) -->

<!-- Completed items greyed out but still visible -->
<!-- "All required items complete! You're ready to contribute." celebration state -->
```

#### `onboarding/admin.html`

```html
<!-- Header: "Onboarding Template" | [+ Add Item] | [Preview as New Dev] -->
<!-- Items list grouped by week, drag to reorder -->
<!-- Each item: week | category | title | required toggle | link | [Edit] [Delete] -->
<!-- Add/Edit form: all OnboardingItem fields -->
```

#### `onboarding/progress.html` (admin only)

```html
<!-- Header: "Onboarding Progress — All Users" -->
<!-- Table: username | team | started date | required complete | total complete | % | last active -->
<!-- Click user → see their specific checklist state -->
<!-- Flag: users who started > 2 weeks ago and < 50% required complete (at risk) -->
```

-----

## 🔗 Dashboard Integration

Add these 4 cards to `DashboardController.java`:

```java
new Feature("Version Tracker",     "Diff API versions — find breaking changes",      "/version-tracker",     "⟷",  "ACTIVE"),
new Feature("Consumer Registry",   "Who is consuming your APIs and on which version", "/consumer-registry",   "⊛",  "ACTIVE"),
new Feature("Readiness Scorecard", "API production readiness against your standards", "/scorecard",           "✓",  "ACTIVE"),
new Feature("Onboarding",          "New developer checklist — linked to all tools",   "/onboarding",          "🎯", "ACTIVE"),
```

-----

## 🔗 Cross-Feature Links (Wire These)

These connections between features make the tool feel like a platform, not a collection of pages:

|From                        |To                |When                                                    |
|----------------------------|------------------|--------------------------------------------------------|
|Version Tracker report      |Consumer Registry |“Check Consumer Impact” button on breaking change report|
|Consumer Registry API detail|Version Tracker   |“Check Version History” button on API detail            |
|Scorecard dashboard         |Runbook Library   |“Missing runbook” criterion links to /runbook/new       |
|Scorecard dashboard         |Env Health Checker|“Missing env URLs” criterion links to /env-health       |
|Onboarding checklist        |Every feature     |Each item links directly to the relevant feature        |
|PCF Health Dashboard        |Scorecard         |“Not registered in scorecard?” prompt on unscored apps  |
|Consumer Registry           |Scorecard         |“No scorecard for this API” warning on API detail       |

-----

## 🔒 RBAC Rules

|Feature             |All Users                        |Admin Only                                   |
|--------------------|---------------------------------|---------------------------------------------|
|Version Tracker     |View history + run new comparison|Delete comparisons                           |
|Consumer Registry   |Register consumer + view         |Edit/Delete any consumer                     |
|Readiness Scorecard |Fill + view any scorecard        |Edit criteria + set threshold + mark reviewed|
|Onboarding Checklist|View + complete own checklist    |Edit template + view all progress            |

-----

## 🚀 Build Order

```
Step 1:  Add 4 new feature cards to DashboardController and dashboard.html
Step 2:  Build Onboarding Checklist — seed default items, per-user Redis progress
Step 3:  Build API Readiness Scorecard — seed default criteria, score calculation
Step 4:  Build Consumer Registry — CRUD + impact analysis grouping
Step 5:  Build ApiDiffService with basic path-level diff (endpoints added/removed)
Step 6:  Extend ApiDiffService with field-level schema diff (breaking changes)
Step 7:  Build Version Tracker controller + templates wired to ApiDiffService
Step 8:  Wire cross-feature links between all 4 features
Step 9:  Wire cross-feature links to existing features (Runbook, Env Health, PCF Health)
Step 10: Add Consumer Registry awareness to Version Tracker report
Step 11: Test full architect workflow:
           Fill scorecard → see gap → fix it → re-assess → score improves
           Run version diff → see breaking change → check consumer impact → notify contact
Step 12: RBAC gate all admin-only routes
```

-----

## ✅ Success Criteria

- [ ] Version Tracker: paste two specs, get breaking/non-breaking change report
- [ ] Version Tracker: history saved per API, browseable by API name
- [ ] Consumer Registry: register consumer, view by API, impact analysis groups by criticality
- [ ] Consumer Registry: reverse lookup — given a system, see all APIs it depends on
- [ ] Scorecard: default criteria seeded on first run
- [ ] Scorecard: score calculates correctly — required failures block production readiness
- [ ] Scorecard: admin can add/edit criteria and set production threshold
- [ ] Onboarding: new user sees checklist with all items unchecked
- [ ] Onboarding: check/uncheck persists across logout/login
- [ ] Onboarding: admin can edit template items and add new ones
- [ ] Cross-links: Version Tracker report links to Consumer Registry for impact view
- [ ] Cross-links: Scorecard criteria link to relevant ApplyAdmin features
- [ ] Dashboard: all 4 new cards route correctly

-----

## 📁 GitHub Conventions

```
Branch naming:
  feature/api-version-tracker
  feature/consumer-registry
  feature/readiness-scorecard
  feature/onboarding-checklist

Commit messages:
  feat(version-tracker): add breaking vs non-breaking classification
  feat(version-tracker): recursive schema diff for nested objects
  feat(consumer-registry): impact analysis grouped by criticality
  feat(scorecard): seed default criteria for Synchrony API standards
  feat(scorecard): real-time score calculation in scorecard form
  feat(onboarding): link checklist items to ApplyAdmin features

Tags:
  v4.0-version-tracker
  v4.1-consumer-registry
  v4.2-readiness-scorecard
  v4.3-onboarding-checklist
  v4.4-cross-feature-links
```

-----

*ApplyAdmin — Solution Architect Feature Set*
*API Version Change Tracker · Consumer Registry · Readiness Scorecard · Onboarding*
*Stack: Java Spring Boot · Thymeleaf · Redis · PCF*
*Author: Solution Architect — Birlasoft / Synchrony Financial API Teams*
*File: `docs/prompts/apply-admin-architect-features-prompt.md`*
*Last updated: March 2026*
