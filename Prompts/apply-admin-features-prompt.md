# ApplyAdmin — Complete Feature Build Prompt

## PCF Health Dashboard · Environment Health Checker · Redis Key Explorer

## Runbook Library · Weekly Team Pulse · Weekly Summary · Shoutout Board · Solutioning Log

> **How to use this file:**
> Save to: `docs/prompts/apply-admin-features-prompt.md` in your GitHub repo.
> Pull this file → paste into GitHub Copilot Chat, Claude, or Cursor → say “Build Feature X following this prompt.”
> Each feature is self-contained. Build one at a time.

-----

## 📦 App Context (Read This First)

```
App name:       ApplyAdmin
Stack:          Java Spring Boot + Thymeleaf + Redis
Deployed on:    PCF (Pivotal Cloud Foundry)
Auth/RBAC:      Spring Security, logged-in user is default Admin
Existing features:
  - /parser         → 105 ISO 8583 Request Parser
  - /pr-review      → PR Review Log (Redis-backed)
  - /agentic        → Agentic AI Learning Hub (Redis-backed)
  - /dashboard      → Feature hub landing page
CSS theme:      IBM Plex Mono + Syne fonts, dark theme with CSS variables
Redis:          Already connected via spring-boot-starter-data-redis
Context:        Synchrony Financial (client) | Birlasoft (employer)
                APIs team — both internal and external APIs
                Author is lead mentor across 5 API sub-teams
```

-----

## 🎨 Design Tokens (Apply to All New Pages)

All new pages must use these CSS variables. Add to `applyadmin.css` if not already there.

```css
:root {
  --bg-primary:         #0a0a0f;
  --bg-secondary:       #111118;
  --bg-card:            #14141e;
  --bg-input:           #0d0d14;
  --border-default:     #1e1e2e;
  --border-accent:      #3b3b5c;
  --text-primary:       #e2e8f0;
  --text-secondary:     #94a3b8;
  --text-muted:         #475569;
  --accent-purple:      #7c3aed;
  --accent-purple-light:#a78bfa;
  --accent-cyan:        #06b6d4;
  --accent-green:       #4ade80;
  --accent-amber:       #fbbf24;
  --accent-rose:        #f87171;
  --status-green-bg:    #052e16;
  --status-amber-bg:    #2e1f00;
  --status-red-bg:      #2e0000;
  --gradient-brand:     linear-gradient(135deg, #7c3aed, #06b6d4);
  --gradient-progress:  linear-gradient(90deg, #7c3aed, #a78bfa);
}
```

-----

## 🗂 Shared File Structure (All Features Follow This)

```
src/main/
├── java/com/applyadmin/
│   ├── controller/
│   │   ├── PcfHealthController.java
│   │   ├── EnvHealthController.java
│   │   ├── RedisExplorerController.java
│   │   ├── RunbookController.java
│   │   ├── TeamPulseController.java
│   │   ├── WeeklySummaryController.java
│   │   ├── ShoutoutController.java
│   │   └── SolutioningController.java
│   ├── service/
│   │   ├── PcfHealthService.java
│   │   ├── EnvHealthService.java
│   │   ├── RedisExplorerService.java
│   │   ├── RunbookService.java
│   │   ├── TeamPulseService.java
│   │   ├── WeeklySummaryService.java
│   │   ├── ShoutoutService.java
│   │   └── SolutioningService.java
│   └── model/
│       ├── AppHealthStatus.java
│       ├── EnvEndpoint.java
│       ├── Runbook.java
│       ├── TeamPulseEntry.java
│       ├── WeeklySummary.java
│       ├── Shoutout.java
│       └── SolutioningNote.java
└── resources/
    └── templates/
        ├── pcf-health/
        │   └── dashboard.html
        ├── env-health/
        │   └── checker.html
        ├── redis-explorer/
        │   └── explorer.html
        ├── runbook/
        │   ├── list.html
        │   └── detail.html
        ├── team-pulse/
        │   ├── form.html
        │   └── summary.html
        ├── weekly-summary/
        │   └── generator.html
        ├── shoutout/
        │   └── board.html
        └── solutioning/
            ├── list.html
            └── form.html
```

-----

## 🔴 FEATURE 1: PCF App Health Dashboard

### What It Does

Calls `/actuator/health` and `/actuator/info` on your PCF-deployed Spring Boot apps.
Shows a live status grid — green/yellow/red per app. No New Relic needed for a morning
health check. You maintain the app list yourself via the UI.

### Route

```
GET  /pcf-health              → Dashboard with all app statuses
GET  /pcf-health/refresh      → Trigger a fresh health check on all apps
POST /pcf-health/apps/add     → Add a new app to monitor
POST /pcf-health/apps/remove  → Remove an app from the list
```

### Data Model

```java
public class AppHealthStatus {
    private String appName;          // Display name e.g. "Payment Gateway API"
    private String internalUrl;      // PCF internal URL e.g. http://payment-api.apps.internal
    private String environment;      // "DEV" | "QA" | "PROD"
    private String status;           // "UP" | "DOWN" | "DEGRADED" | "UNKNOWN"
    private String httpStatus;       // Raw HTTP status code returned
    private Map<String,Object> details; // Full actuator response parsed
    private long responseTimeMs;     // How long the health call took
    private LocalDateTime lastChecked;
    private String lastError;        // If DOWN, what was the error message
}
```

### Redis Key Schema

```
pcf:apps:registry           → JSON List<AppHealthStatus> (your configured apps)
pcf:health:snapshot:{appName} → JSON AppHealthStatus (latest status per app)
pcf:health:last-refresh     → timestamp string of last full refresh
```

### Service Layer

```java
@Service
public class PcfHealthService {

    // Load all configured apps from Redis
    public List<AppHealthStatus> getAllApps();

    // Ping one app's /actuator/health endpoint
    // Uses RestTemplate with 5 second timeout
    // Catch connection refused, timeout, 5xx — mark as DOWN
    public AppHealthStatus checkHealth(AppHealthStatus app);

    // Ping all apps in parallel using CompletableFuture
    // Save each result back to Redis
    // Return combined list
    public List<AppHealthStatus> refreshAll();

    // Add a new app to the registry
    public void addApp(String appName, String internalUrl, String environment);

    // Remove an app by name
    public void removeApp(String appName);
}
```

### RestTemplate Config (Add to Spring Config)

```java
@Bean
public RestTemplate healthCheckRestTemplate() {
    HttpComponentsClientHttpRequestFactory factory =
        new HttpComponentsClientHttpRequestFactory();
    factory.setConnectTimeout(5000);   // 5 seconds max connect
    factory.setReadTimeout(5000);      // 5 seconds max read
    return new RestTemplate(factory);
}
```

### Controller

```java
@Controller
@RequestMapping("/pcf-health")
public class PcfHealthController {

    @GetMapping
    public String dashboard(Model model) {
        List<AppHealthStatus> apps = pcfHealthService.getAllApps();
        long upCount = apps.stream().filter(a -> "UP".equals(a.getStatus())).count();
        long downCount = apps.stream().filter(a -> "DOWN".equals(a.getStatus())).count();
        model.addAttribute("apps", apps);
        model.addAttribute("upCount", upCount);
        model.addAttribute("downCount", downCount);
        model.addAttribute("lastRefresh", pcfHealthService.getLastRefresh());
        return "pcf-health/dashboard";
    }

    @GetMapping("/refresh")
    public String refresh() {
        pcfHealthService.refreshAll();
        return "redirect:/pcf-health";
    }

    @PostMapping("/apps/add")
    public String addApp(@RequestParam String appName,
                         @RequestParam String internalUrl,
                         @RequestParam String environment) {
        pcfHealthService.addApp(appName, internalUrl, environment);
        return "redirect:/pcf-health";
    }

    @PostMapping("/apps/remove")
    public String removeApp(@RequestParam String appName) {
        pcfHealthService.removeApp(appName);
        return "redirect:/pcf-health";
    }
}
```

### Thymeleaf Template: `pcf-health/dashboard.html`

```html
<!-- Layout -->
<!-- Hero row: total apps, UP count (green), DOWN count (red), last refresh time, [Refresh Now] button -->
<!-- Status grid: one card per app -->
<!--   Card shows: app name, environment badge (DEV/QA/PROD colored), status indicator -->
<!--   Status dot: green = UP, red = DOWN, amber = DEGRADED, gray = UNKNOWN -->
<!--   Response time shown in ms -->
<!--   If DOWN: show lastError message in red -->
<!--   Click card to expand: show full actuator details (disk, db, memory) -->
<!-- Bottom: Add App form (collapsible): appName, internalUrl, environment dropdown -->
<!-- Admin only: Remove button on each card -->

<!-- Status color logic using Thymeleaf: -->
<!-- th:classappend="${app.status == 'UP'} ? 'status-up' : (${app.status == 'DOWN'} ? 'status-down' : 'status-unknown')" -->
```

### CSS for Status Cards

```css
.status-dot { width: 10px; height: 10px; border-radius: 50%; display: inline-block; }
.status-dot.up       { background: var(--accent-green); box-shadow: 0 0 6px var(--accent-green); }
.status-dot.down     { background: var(--accent-rose);  box-shadow: 0 0 6px var(--accent-rose); }
.status-dot.degraded { background: var(--accent-amber); box-shadow: 0 0 6px var(--accent-amber); }
.status-dot.unknown  { background: var(--text-muted); }
.app-card { background: var(--bg-card); border: 1px solid var(--border-default); border-radius: 8px; padding: 16px; }
.app-card.down  { border-color: rgba(248,113,113,0.4); }
.app-card.up    { border-color: rgba(74,222,128,0.2); }
```

### application.properties

```properties
# PCF Health checks run with this timeout
pcf.health.timeout.ms=5000
pcf.health.parallel.threads=10
```

### Important Notes for Copilot

- Use `CompletableFuture.supplyAsync()` to ping all apps in parallel — do NOT ping sequentially
- Wrap each HTTP call in try-catch — a DOWN app must not crash the whole refresh
- Store the app registry separately from health snapshots in Redis (registry = config, snapshot = live data)
- Parse actuator `/health` response: look for `status` field at root level
- Parse actuator `/info` response: look for `app.version` or `build.version` if present
- All Redis values serialized as JSON using Jackson ObjectMapper

-----

## 🟡 FEATURE 2: Environment Health Checker

### What It Does

You define URLs for your APIs across environments (DEV/QA/PROD).
Hit “Check All” and see which endpoints respond, how fast, and with what status.
Replaces manually hitting URLs in a browser or Postman.
You can also call your Apigee proxy URLs here — just paste the proxy endpoint URL.

### Route

```
GET  /env-health              → Checker page with all configured endpoints
POST /env-health/check        → Run health check on all endpoints (or one)
POST /env-health/endpoints/add    → Add endpoint to list
POST /env-health/endpoints/remove → Remove endpoint
```

### Data Model

```java
public class EnvEndpoint {
    private String id;              // UUID
    private String name;            // "Payment API - QA"
    private String url;             // Full URL including path
    private String environment;     // "DEV" | "QA" | "PROD"
    private String method;          // "GET" | "POST" (default GET)
    private Map<String,String> headers; // Optional headers e.g. API key
    private int expectedStatusCode; // Expected HTTP status e.g. 200
    private int thresholdMs;        // If response > this, mark as SLOW (default 1000)
    private String apiType;         // "INTERNAL" | "APIGEE_PROXY" | "EXTERNAL"

    // Populated after check:
    private String lastStatus;      // "PASS" | "FAIL" | "SLOW" | "UNCHECKED"
    private int lastHttpCode;
    private long lastResponseMs;
    private LocalDateTime lastChecked;
    private String lastError;
}
```

### Redis Key Schema

```
env:endpoints:registry     → JSON List<EnvEndpoint>
env:health:last-run        → timestamp of last check run
```

### Service Layer

```java
@Service
public class EnvHealthService {

    public List<EnvEndpoint> getAllEndpoints();

    // Check a single endpoint
    // If GET: simple HTTP GET with headers
    // If POST: HTTP POST with empty body (just checking reachability)
    // Measure response time, compare to threshold, compare HTTP code to expected
    public EnvEndpoint checkEndpoint(EnvEndpoint endpoint);

    // Check all endpoints in parallel
    public List<EnvEndpoint> checkAll();

    public void addEndpoint(EnvEndpoint endpoint);
    public void removeEndpoint(String id);
}
```

### Thymeleaf Template: `env-health/checker.html`

```html
<!-- Header: total endpoints, PASS count, FAIL count, SLOW count, [Check All] button -->
<!-- Filter pills: All | DEV | QA | PROD | APIGEE_PROXY -->
<!-- Endpoint table: -->
<!--   Columns: Status badge | Name | Environment | URL (truncated) | Response Time | HTTP Code | Last Checked | Actions -->
<!--   Status badge: PASS=green, FAIL=red, SLOW=amber, UNCHECKED=gray -->
<!--   Response time bar: visual bar from 0 to threshold, turns red if over -->
<!--   Expand row to show full URL, headers (masked), last error -->
<!-- Add Endpoint form (collapsible at bottom): -->
<!--   name, url, environment, method, expectedStatusCode, thresholdMs, apiType -->
<!-- Each endpoint has individual [Check Now] button + [Remove] button (admin) -->
```

### Note on Apigee Proxy URLs

Since you can reach Apigee proxies but not the Management API, add this note to the
add-endpoint form as helper text:

```
"For Apigee proxies: paste your full proxy URL (e.g. https://api.synchrony.com/payments/v1/health).
Select type = APIGEE_PROXY. Add your API key or Bearer token in headers if required."
```

-----

## 🔵 FEATURE 3: Redis Key Explorer

### What It Does

Admin-only UI to browse, inspect, and manage your Redis keys.
Useful for debugging all other features — check if data was saved correctly,
view TTLs, delete stale test data.

### Route

```
GET  /redis-explorer                    → Main explorer page
GET  /redis-explorer/search?prefix=X    → Search keys by prefix
GET  /redis-explorer/key?key=X          → View value of a specific key
POST /redis-explorer/delete             → Delete a key (admin only)
POST /redis-explorer/ttl               → Update TTL on a key
```

### Predefined Prefix Shortcuts

Show quick-filter buttons for your known key patterns:

```
[pcf:health:*]  [module:progress:*]  [module:reflection:*]
[runbook:*]     [pulse:*]            [shoutout:*]
[env:*]         [solutioning:*]      [ALL KEYS]
```

### Service Layer

```java
@Service
public class RedisExplorerService {

    // Search keys by prefix pattern using SCAN (not KEYS — safer for prod)
    public List<String> searchKeys(String prefix, int maxResults);

    // Get value of a key as String (deserialize JSON for display)
    public String getValue(String key);

    // Get TTL of a key in seconds (-1 = no expiry, -2 = not found)
    public long getTtl(String key);

    // Get type of key: string, list, hash, set, zset
    public String getType(String key);

    // Delete a key
    public void deleteKey(String key);

    // Set TTL on a key
    public void setTtl(String key, long seconds);
}
```

### Important Note for Copilot

- Use `RedisTemplate.scan()` with `ScanOptions.scanOptions().match(prefix + "*").count(100).build()`
- NEVER use `keys("*")` in a production Redis instance — it blocks the server
- Pretty-print JSON values using Jackson ObjectMapper with `SerializationFeature.INDENT_OUTPUT`
- Mask any value containing “password”, “token”, “secret”, “key” — replace with `[REDACTED]`
- This route must be RBAC-protected: admin role only

### Thymeleaf Template: `redis-explorer/explorer.html`

```html
<!-- Header: Redis connection status (ping check), total keys scanned -->
<!-- Quick prefix buttons (listed above) -->
<!-- Search bar: type prefix → results appear below -->
<!-- Results table: Key name | Type | TTL | Value preview (first 100 chars) | [View] [Delete] -->
<!-- View modal/expanded row: full JSON value pretty-printed, full TTL, [Set TTL] input -->
<!-- Delete requires confirmation: "Type key name to confirm delete" -->
<!-- Warning banner: "This is a production Redis instance. Deletions are permanent." -->
```

-----

## 🟢 FEATURE 4: Runbook Library

### What It Does

Searchable library of operational runbooks for your APIs.
When something goes wrong, developers search here before pinging you.
Replaces “ask the senior dev” with self-serve documentation.
Different from Confluence — these are SHORT, actionable, step-by-step.
Confluence has the long docs. This has the “what to do RIGHT NOW” guides.

### Route

```
GET  /runbook              → List all runbooks with search
GET  /runbook/{id}         → View a runbook
GET  /runbook/new          → Create form
POST /runbook/create       → Save new runbook
GET  /runbook/{id}/edit    → Edit form
POST /runbook/{id}/update  → Update runbook
POST /runbook/{id}/delete  → Delete (admin only)
```

### Data Model

```java
public class Runbook {
    private String id;              // UUID
    private String title;           // "How to restart Payment API on PCF"
    private String apiName;         // Which API this relates to
    private String category;        // "Restart" | "Rollback" | "Debug" | "Deploy" | "Config" | "Other"
    private String severity;        // "P1-Critical" | "P2-High" | "P3-Medium" | "P4-Low"
    private String problem;         // Short description of the problem this solves
    private String steps;           // Numbered step-by-step markdown text
    private String notes;           // Additional context, gotchas, links
    private String author;          // Who wrote it (from Spring Security principal)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int viewCount;          // How many times it was opened
    private List<String> tags;      // Searchable tags
}
```

### Redis Key Schema

```
runbook:{id}           → JSON Runbook
runbook:index          → JSON List of all runbook IDs (for listing)
runbook:search:tags    → Set of all tags (for autocomplete)
```

### Service Layer

```java
@Service
public class RunbookService {

    public List<Runbook> getAllRunbooks();
    public List<Runbook> searchRunbooks(String query);   // Search title, tags, apiName, problem
    public List<Runbook> filterByCategory(String category);
    public Runbook getById(String id);
    public void create(Runbook runbook, String authorUsername);
    public void update(String id, Runbook updated);
    public void delete(String id);
    public void incrementViewCount(String id);          // Call on every GET /{id}
    public List<Runbook> getMostViewed(int limit);      // Top 5 most accessed
}
```

### Thymeleaf Templates

#### `runbook/list.html`

```html
<!-- Header: "Runbook Library" | total count | [+ New Runbook] button -->
<!-- Search bar: searches title, tags, API name as you type (form submit on enter) -->
<!-- Filter row: category pills — All | Restart | Rollback | Debug | Deploy | Config -->
<!-- Severity filter: All | P1 | P2 | P3 | P4 -->
<!-- Top 5 Most Viewed section (quick access) -->
<!-- Runbook cards: title, apiName, category badge, severity badge, author, last updated, view count -->
<!-- Click card → goes to /runbook/{id} -->
```

#### `runbook/detail.html`

```html
<!-- Breadcrumb: Runbooks > {title} -->
<!-- Header: title, apiName, category + severity badges, author, last updated -->
<!-- Problem section: what problem this solves -->
<!-- Steps section: numbered steps rendered from text (use <pre> or markdown renderer) -->
<!-- Notes section: additional context -->
<!-- Tags: clickable, links to search for that tag -->
<!-- [Edit] button (admin), [Back to List] -->
<!-- View count shown subtly: "Viewed 12 times" -->
```

-----

## 🟠 FEATURE 5: Weekly Team Pulse

### What It Does

Every Friday, each team lead fills a 3-question form.
You (the mentor) see all 5 responses on Monday morning on one page.
Stored per week number. No Jira — this is the human narrative, not tickets.

### Route

```
GET  /pulse              → Current week's summary (your view)
GET  /pulse/submit       → Submission form (team lead view)
POST /pulse/submit       → Save pulse entry
GET  /pulse/history      → Past weeks dropdown
GET  /pulse/week/{weekNum} → A specific week's responses
```

### Data Model

```java
public class TeamPulseEntry {
    private String id;
    private String teamName;        // "API Team 1" etc
    private String submittedBy;     // username from Spring Security
    private int weekNumber;         // ISO week number e.g. 12
    private int year;
    private String shipped;         // What did we ship this week?
    private String blocked;         // What are we blocked on?
    private String needsFromMentor; // What do we need from you specifically?
    private String morale;          // "High" | "Medium" | "Low" — simple dropdown
    private LocalDateTime submittedAt;
}
```

### Redis Key Schema

```
pulse:week:{year}:{weekNum}:{teamName} → JSON TeamPulseEntry
pulse:weeks:index                       → Sorted set of "year:weekNum" strings
```

### Service Layer

```java
@Service
public class TeamPulseService {

    // Get current ISO week number
    public int getCurrentWeekNumber();

    // Get all entries for a specific week
    public List<TeamPulseEntry> getWeekEntries(int year, int weekNum);

    // Check if a team has submitted for current week
    public boolean hasSubmitted(String teamName, int weekNum);

    // Save a pulse entry
    public void submit(TeamPulseEntry entry);

    // Get list of all weeks that have submissions (for history dropdown)
    public List<String> getAllWeeks();
}
```

### Thymeleaf Templates

#### `team-pulse/summary.html` (your view — mentor)

```html
<!-- Header: "Week {N} Pulse" | date range | {X}/5 teams submitted -->
<!-- Progress bar: X of 5 submitted -->
<!-- Missing submissions: show which teams haven't submitted yet (red) -->
<!-- Response cards: one per team -->
<!--   Team name + morale indicator (color dot) -->
<!--   Shipped: what they said -->
<!--   Blocked: what they said (highlight in amber if not empty) -->
<!--   Needs from mentor: what they need from you (highlight in purple) -->
<!-- Week history: dropdown to view past weeks -->
<!-- [Export This Week] button → generates text summary you can paste to Slack/email -->
```

#### `team-pulse/form.html` (team lead view)

```html
<!-- Simple form: -->
<!--   Team Name (dropdown: Team 1-5) -->
<!--   What did we ship this week? (textarea) -->
<!--   What are we blocked on? (textarea, can say "Nothing") -->
<!--   What do we need from our mentor? (textarea) -->
<!--   Team morale this week (radio: High / Medium / Low) -->
<!--   [Submit] button -->
<!-- Show confirmation if already submitted this week: "You submitted on {date}. [Edit Submission]" -->
```

-----

## 🟣 FEATURE 6: Weekly Summary Generator

### What It Does

You fill in what each team shipped. It formats a clean, professional status update
you can copy-paste directly into email to your manager or Slack to leadership.
Removes the weekly friction of writing status updates from scratch.

### Route

```
GET  /weekly-summary           → Generator page
POST /weekly-summary/generate  → Save and format summary
GET  /weekly-summary/history   → Past summaries
```

### Data Model

```java
public class WeeklySummary {
    private String id;
    private int weekNumber;
    private int year;
    private String highlights;       // Big wins across all teams
    private String team1Update;
    private String team2Update;
    private String team3Update;
    private String team4Update;
    private String team5Update;
    private String risks;            // Risks/blockers you want to escalate
    private String nextWeekFocus;    // What teams are focused on next week
    private String generatedText;    // Final formatted output
    private LocalDateTime createdAt;
}
```

### Smart Feature: Auto-Pull from Team Pulse

If Weekly Team Pulse entries exist for the current week, pre-fill the generator
with the “shipped” field from each team’s pulse entry. One click to get started.

```java
// In controller:
@GetMapping("/weekly-summary")
public String generator(Model model) {
    int week = pulseService.getCurrentWeekNumber();
    List<TeamPulseEntry> pulseEntries = pulseService.getWeekEntries(LocalDate.now().getYear(), week);
    // Pre-populate form fields from pulse entries if they exist
    model.addAttribute("pulseEntries", pulseEntries);
    return "weekly-summary/generator";
}
```

### Output Format (Generate This Text)

```
📋 API Teams — Week {N} Status Update
Period: {Mon date} – {Fri date}

✅ HIGHLIGHTS
{highlights text}

📦 TEAM UPDATES
• Team 1 — {team1Update}
• Team 2 — {team2Update}
• Team 3 — {team3Update}
• Team 4 — {team4Update}
• Team 5 — {team5Update}

⚠️ RISKS & BLOCKERS
{risks text}

🎯 NEXT WEEK FOCUS
{nextWeekFocus}

Generated by ApplyAdmin
```

### Thymeleaf Template: `weekly-summary/generator.html`

```html
<!-- Header: "Weekly Summary" | Week {N} | {date range} -->
<!-- [Pull from Team Pulse] button — pre-fills from this week's pulse entries -->
<!-- Form fields: one textarea per section above -->
<!-- [Generate Summary] → formats and shows output in a read-only box below -->
<!-- Output box: monospace font, full formatted text, [Copy to Clipboard] button -->
<!-- [Save to History] button → saves this week's summary to Redis -->
<!-- History tab: dropdown of past weeks → click to view/copy old summaries -->
```

-----

## 🌟 FEATURE 7: Shoutout Board

### What It Does

Team members give each other public kudos.
30 seconds to post. High morale impact.
Shows a live feed of recent shoutouts, visible to everyone.

### Route

```
GET  /shoutout              → Board with feed + post form
POST /shoutout/create       → Post a new shoutout
POST /shoutout/{id}/delete  → Delete (admin only)
```

### Data Model

```java
public class Shoutout {
    private String id;
    private String fromUser;        // Who is giving the shoutout (from Spring Security)
    private String toName;          // Who is receiving (free text — they might not have login)
    private String team;            // Which team they're on
    private String message;         // The shoutout message (max 280 chars)
    private String category;        // "🚀 Delivery" | "🧠 Problem Solving" | "🤝 Collaboration" | "🛡 Quality" | "💡 Innovation"
    private LocalDateTime createdAt;
}
```

### Redis Key Schema

```
shoutout:feed          → Redis List (LPUSH new, LRANGE 0 49 for latest 50)
shoutout:{id}          → JSON Shoutout (for individual lookup)
```

### Service Layer

```java
@Service
public class ShoutoutService {

    // Get latest N shoutouts (most recent first)
    public List<Shoutout> getLatest(int count);

    // Post a new shoutout
    public void post(Shoutout shoutout);

    // Get shoutouts for a specific person (by toName)
    public List<Shoutout> getForPerson(String name);

    // Delete (admin only)
    public void delete(String id);
}
```

### Thymeleaf Template: `shoutout/board.html`

```html
<!-- Header: "Shoutout Board 🌟" | total shoutouts this month -->
<!-- Post form (always visible at top): -->
<!--   To: (text input — who are you shouting out?) -->
<!--   Team: (dropdown: Team 1-5) -->
<!--   Category: (dropdown with emojis listed above) -->
<!--   Message: (textarea, max 280 chars, char counter) -->
<!--   [Send Shoutout 🚀] button -->
<!-- Feed: cards in reverse chronological order -->
<!--   Card: category emoji | "From {fromUser} to {toName} ({team})" | message | time ago -->
<!--   Subtle border color per category -->
<!-- Admin: small [x] delete button on each card -->
```

-----

## 📓 FEATURE 8: Solutioning Log

### What It Does

Every time you do a design review or solution a problem with a team,
log it here. 3 minutes to fill. Builds institutional memory.
Searchable by team, date, keyword. Private — only you see it (admin only).

### Route

```
GET  /solutioning              → List with search
GET  /solutioning/new          → New entry form
POST /solutioning/create       → Save entry
GET  /solutioning/{id}         → View entry
GET  /solutioning/{id}/edit    → Edit form
POST /solutioning/{id}/update  → Update
POST /solutioning/{id}/delete  → Delete
```

### Data Model

```java
public class SolutioningNote {
    private String id;
    private String teamName;          // Which team
    private String problemStatement;  // What was the problem in 1-2 sentences
    private String context;           // More background if needed
    private String optionsConsidered; // What approaches were discussed
    private String decisionMade;      // What was decided
    private String reasoning;         // Why this option was chosen
    private String followUp;          // What needs to happen next, who owns it
    private String outcome;           // Filled in later — what actually happened
    private LocalDate sessionDate;    // When did this conversation happen
    private List<String> tags;        // e.g. "auth", "error-handling", "pagination"
    private String status;            // "OPEN" | "RESOLVED" | "REVISIT"
}
```

### Redis Key Schema

```
solutioning:{id}           → JSON SolutioningNote
solutioning:index          → JSON List of all IDs with metadata (for listing/search)
solutioning:tags           → Set of all tags used
```

### Service Layer

```java
@Service
public class SolutioningService {

    public List<SolutioningNote> getAll();
    public List<SolutioningNote> searchByKeyword(String keyword);
    public List<SolutioningNote> filterByTeam(String teamName);
    public List<SolutioningNote> filterByStatus(String status);
    public SolutioningNote getById(String id);
    public void create(SolutioningNote note);
    public void update(String id, SolutioningNote updated);
    public void delete(String id);

    // Get open items that need follow-up
    public List<SolutioningNote> getOpenItems();
}
```

### Thymeleaf Templates

#### `solutioning/list.html`

```html
<!-- Header: "Solutioning Log" | [+ New Entry] | [ADMIN ONLY banner] -->
<!-- Open Items alert: if any notes have status=OPEN and followUp not empty, show count -->
<!-- Search bar + filter row: All Teams | Team 1-5 | filter by status: All/Open/Resolved/Revisit -->
<!-- Cards: date | team badge | problem statement | status badge | tags -->
<!-- Click → detail view -->
<!-- Sort: most recent first (default) | by team | by status -->
```

#### `solutioning/form.html`

```html
<!-- Clean form: -->
<!--   Team Name (dropdown) -->
<!--   Session Date (date picker, defaults to today) -->
<!--   Problem Statement (textarea — "What was the question or design challenge?") -->
<!--   Context (textarea — "Any background?") -->
<!--   Options Considered (textarea — "What did you discuss?") -->
<!--   Decision Made (textarea — "What was agreed?") -->
<!--   Reasoning (textarea — "Why this approach?") -->
<!--   Follow Up (textarea — "What's next? Who owns it?") -->
<!--   Tags (text input, comma separated) -->
<!--   Status (radio: OPEN / RESOLVED / REVISIT) -->
<!--   [Save Entry] button -->
```

-----

## 🔗 Dashboard Integration

Update `dashboard.html` to include all new feature cards.
Full card list for the dashboard:

```java
// In DashboardController, update feature list:
List<Feature> features = Arrays.asList(
    new Feature("105 Parser",         "ISO 8583 field mapping parser",          "/parser",         "⟨/⟩", "ACTIVE"),
    new Feature("PR Review Log",      "Redis-backed review logging",            "/pr-review",      "⊡",   "ACTIVE"),
    new Feature("Agentic AI Hub",     "Team learning & progress tracker",       "/agentic",        "◈",   "BETA"),
    new Feature("PCF Health",         "Live health status of your PCF apps",    "/pcf-health",     "⬡",   "ACTIVE"),
    new Feature("Env Health Checker", "Ping APIs across DEV/QA/PROD/Apigee",   "/env-health",     "⟳",   "ACTIVE"),
    new Feature("Redis Explorer",     "Browse and manage Redis keys",           "/redis-explorer", "⌗",   "ADMIN"),
    new Feature("Runbook Library",    "Searchable operational runbooks",         "/runbook",        "📖",  "ACTIVE"),
    new Feature("Team Pulse",         "Weekly team check-in — 3 questions",    "/pulse",          "💬",  "ACTIVE"),
    new Feature("Weekly Summary",     "Generate your leadership status update", "/weekly-summary", "📋",  "ACTIVE"),
    new Feature("Shoutout Board",     "Public team kudos and recognition",      "/shoutout",       "🌟",  "ACTIVE"),
    new Feature("Solutioning Log",    "Design decision and review log",         "/solutioning",    "📓",  "ADMIN")
);
```

-----

## 🔒 RBAC Rules Summary

|Feature             |All Users      |Admin Only          |
|--------------------|---------------|--------------------|
|PCF Health Dashboard|View           |Add/Remove apps     |
|Env Health Checker  |View + Check   |Add/Remove endpoints|
|Redis Explorer      |❌              |Full access         |
|Runbook Library     |View + Search  |Create/Edit/Delete  |
|Team Pulse          |Submit own team|View all teams      |
|Weekly Summary      |❌              |Full access         |
|Shoutout Board      |Post + View    |Delete              |
|Solutioning Log     |❌              |Full access         |

-----

## 🚀 Build Order (Follow This Sequence)

Tell Copilot to build in this exact order — each step is self-contained and testable:

```
Step 1:  Add CSS variables to applyadmin.css (design tokens at top of this file)
Step 2:  Update DashboardController with full feature list
Step 3:  Update dashboard.html with all feature cards (routing only for new ones)
Step 4:  Build Runbook Library — pure Redis CRUD, simplest to start
Step 5:  Build Shoutout Board — Redis List, simple form
Step 6:  Build Solutioning Log — Redis CRUD, admin only
Step 7:  Build Team Pulse — Redis, week-based key structure
Step 8:  Build Weekly Summary — wire auto-pull from Pulse
Step 9:  Build Redis Key Explorer — RedisTemplate scan operations
Step 10: Build Environment Health Checker — HTTP ping with RestTemplate
Step 11: Build PCF Health Dashboard — parallel Actuator calls
Step 12: Wire RBAC to all new routes
Step 13: End-to-end test: login → dashboard → each feature → verify Redis keys saved
```

-----

## ✅ Success Criteria

- [ ] Dashboard shows all 11 feature cards with correct routing
- [ ] Runbook Library: create, search, view, edit, delete all work
- [ ] Shoutout Board: post shows immediately in feed, persists across refresh
- [ ] Solutioning Log: admin-only, searchable by team and keyword
- [ ] Team Pulse: submission saves per team per week, mentor view shows all 5
- [ ] Weekly Summary: auto-pulls from pulse entries, copy-to-clipboard works
- [ ] Redis Explorer: scan by prefix works, values display as pretty JSON, delete requires confirmation
- [ ] Env Health Checker: ping returns status + response time, Apigee proxy URLs work
- [ ] PCF Health Dashboard: parallel calls complete within 10 seconds for 10 apps
- [ ] All new pages use consistent nav, CSS variables, and dark theme
- [ ] RBAC: admin-only features return 403 for non-admin users

-----

## 📁 GitHub Conventions

```
Branch naming:
  feature/runbook-library
  feature/pcf-health-dashboard
  feature/env-health-checker
  feature/redis-explorer
  feature/team-pulse
  feature/weekly-summary
  feature/shoutout-board
  feature/solutioning-log

Commit messages:
  feat(runbook): add search by tag and category filter
  feat(pcf-health): parallel actuator calls with CompletableFuture
  fix(env-health): handle connection timeout gracefully
  feat(pulse): auto-pull shipped items from team pulse entries

Tags:
  v2.0-runbook-shoutout
  v2.1-solutioning-pulse
  v2.2-weekly-summary
  v3.0-pcf-health-dashboard
  v3.1-env-health-checker
  v3.2-redis-explorer
```

-----

*ApplyAdmin — Internal Developer Platform for Synchrony API Teams*
*Stack: Java Spring Boot · Thymeleaf · Redis · PCF · RBAC*
*Author: Birlasoft APIs Team Lead / Mentor*
*File: `docs/prompts/apply-admin-features-prompt.md`*
*Last updated: March 2026*
