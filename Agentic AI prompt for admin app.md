# ApplyAdmin — Full Build Prompt

## Spring Boot + Thymeleaf + Redis | Dashboard + Agentic AI Learning Hub

> **Save this file to your GitHub repo and pull it whenever you need to regenerate or extend the app with AI assistance.**
> Intended for: GitHub Copilot, Claude, ChatGPT, Cursor, or any AI coding assistant.

-----

## 🧭 Context: Who You Are & What This App Is

You are building a feature inside an existing internal admin tool called **ApplyAdmin**, built with:

- **Backend:** Java Spring Boot
- **Templating:** Thymeleaf (server-side rendered HTML pages)
- **State/Storage:** Redis (already in use for PR Review Log feature)
- **Auth/RBAC:** Role-based access control already in place. The default logged-in user is an Admin.
- **Company context:** Synchrony Financial (client), Birlasoft (employer), APIs team
- **Who uses this:** A lead engineer/mentor and 5 API sub-teams (~20-30 people total) who build and maintain REST APIs at an enterprise scale

The app currently has **two features**:

1. **105 Request Parser** — Takes a raw ISO 8583 string, parses it according to a field mapping configuration, and displays the parsed fields in a structured table view.
1. **PR Review Log** — Accepts PR review details (reviewer, PR link, comments, rating, timestamp), stores them in Redis, and allows retrieval/listing of past reviews.

You are now adding a **third feature** and also building a **unified Dashboard landing page**.

-----

## 🎨 Design System: Colors, Fonts & Thymeleaf UI Guidelines

### Color Palette

Keep consistency with the existing Spring Boot + Thymeleaf app. Use these CSS variables across all new pages:

```css
:root {
  --bg-primary: #0a0a0f;
  --bg-secondary: #111118;
  --bg-card: #14141e;
  --bg-input: #0d0d14;
  --border-default: #1e1e2e;
  --border-accent: #3b3b5c;

  --text-primary: #e2e8f0;
  --text-secondary: #94a3b8;
  --text-muted: #475569;
  --text-disabled: #334155;

  --accent-purple: #7c3aed;
  --accent-purple-light: #a78bfa;
  --accent-cyan: #06b6d4;
  --accent-green: #4ade80;
  --accent-amber: #fbbf24;
  --accent-rose: #f87171;

  --gradient-brand: linear-gradient(135deg, #7c3aed, #06b6d4);
  --gradient-progress: linear-gradient(90deg, #7c3aed, #a78bfa);
}
```

### Typography

```css
@import url('https://fonts.googleapis.com/css2?family=IBM+Plex+Mono:wght@300;400;500;600&family=Syne:wght@700;800&display=swap');

body { font-family: 'IBM Plex Mono', monospace; }
h1, h2, .app-title { font-family: 'Syne', sans-serif; font-weight: 800; }
```

### Component Patterns (Thymeleaf fragments)

- Cards: `background: var(--bg-card); border: 1px solid var(--border-default); border-radius: 8px; padding: 16px;`
- Badges: `font-size: 10px; padding: 2px 8px; border-radius: 20px; letter-spacing: 0.08em;`
- Status pills: Cycle through `Not Started (zinc)` → `In Progress (amber)` → `Done (green)` via POST endpoint
- Progress bars: `height: 3px; background: var(--gradient-progress); border-radius: 2px;`
- Buttons: Primary uses `--accent-purple`, hover darkens to `#6d28d9`
- All pages use a **sticky top nav** with the app name `Apply`**`Admin`** (Admin in purple)

-----

## 📐 Feature 1: Unified Dashboard Landing Page

### Route

`GET /dashboard` or `GET /` → redirect to `/dashboard`

### Purpose

Replace any existing default landing with a card-based feature hub. This is the first screen every user sees after login.

### Layout

```
┌─────────────────────────────────────────────────────┐
│  [⚡ ApplyAdmin]   [Dashboard] [105 Parser] [PR Log] [Agentic AI Hub]    ● ADMIN  🧠 │
├─────────────────────────────────────────────────────┤
│                                                     │
│  Welcome back, [username]          [Role: ADMIN]    │
│                                                     │
│  ┌─────────────────┐ ┌─────────────────┐ ┌───────────────────┐ │
│  │  ⟨/⟩            │ │  ⊡              │ │  ◈                │ │
│  │  105 Parser     │ │  PR Review Log  │ │  Agentic AI Hub   │ │
│  │                 │ │                 │ │                   │ │
│  │  ISO 8583 raw   │ │  Redis-backed   │ │  Team learning &  │ │
│  │  field parser   │ │  review logging │ │  progress tracker │ │
│  │                 │ │                 │ │                   │ │
│  │  ● Active       │ │  ● Active       │ │  ◈ Beta           │ │
│  └─────────────────┘ └─────────────────┘ └───────────────────┘ │
│                                                     │
│  [Recent Activity / Admin Alerts panel]             │
└─────────────────────────────────────────────────────┘
```

### Thymeleaf Template: `dashboard.html`

- Extend base layout fragment (`layout/base.html`) that includes nav, CSS, and footer
- Each feature card is a Thymeleaf fragment (`th:fragment="featureCard"`) with: icon, title, description, status badge
- Clicking a card navigates to its route (`/parser`, `/pr-review`, `/agentic`)
- Admin users see an additional “Manage Features” toggle row below cards (enable/disable per RBAC)
- Show a small “Last used” timestamp per feature (pull from Redis or session)

### Controller

```java
@GetMapping("/dashboard")
public String dashboard(Model model, Principal principal) {
    model.addAttribute("username", principal.getName());
    model.addAttribute("features", featureService.getAllFeaturesWithStatus());
    model.addAttribute("recentActivity", activityService.getRecent(principal.getName(), 5));
    return "dashboard";
}
```

-----

## 📐 Feature 2: 105 Parser (Existing — No Change Needed)

Keep as-is. Just ensure it is:

- Accessible from nav → `/parser`
- The nav item highlights when active (`th:classappend`)
- Card on dashboard routes to `/parser`

-----

## 📐 Feature 3: PR Review Log (Existing — No Change Needed)

Keep as-is. Just ensure it is:

- Accessible from nav → `/pr-review`
- Card on dashboard routes to `/pr-review`

-----

## 📐 Feature 4: Agentic AI Learning Hub (NEW — Build This Fully)

### Route

`GET /agentic` → Main hub page
`POST /agentic/module/status` → Update module status for logged-in user
`POST /agentic/module/reflection` → Save/update reflection text
`POST /agentic/challenge/submit` → Submit weekly challenge response
`GET /agentic/leaderboard` → Returns team progress data (can be partial page / HTMX or full page)

-----

### 4.1 Data Models

```java
// Module progress per user
public class ModuleProgress {
    private String userId;
    private int moduleId;
    private String status; // "NOT_STARTED" | "IN_PROGRESS" | "DONE"
    private String reflection; // free-text, nullable
    private LocalDateTime updatedAt;
}

// Weekly challenge submission
public class ChallengeSubmission {
    private String userId;
    private int weekNumber;
    private String submissionText;
    private LocalDateTime submittedAt;
}

// Learning module definition (can be hardcoded or stored in Redis/config)
public class LearningModule {
    private int id;
    private String title;
    private String category; // "Foundations" | "Architecture" | "Applied" | "Advanced"
    private String type;     // "Reading" | "Hands-on" | "Video" | "Workshop" | "Project"
    private String duration;
    private String description;
    private String resourceUrl; // optional external link
    private String relevanceNote; // how it applies to API teams specifically
}
```

### 4.2 Redis Key Schema

```
# Per-user module progress
module:progress:{userId}:{moduleId}    → JSON of ModuleProgress

# Per-user reflections
module:reflection:{userId}:{moduleId} → String (reflection text)

# Challenge submissions
challenge:submission:{weekNum}:{userId} → JSON of ChallengeSubmission

# Leaderboard (aggregated, refresh every 5 min)
leaderboard:snapshot → JSON list of {userId, name, role, doneCount}
```

-----

### 4.3 Learning Modules Content (Hardcode These 16 Modules)

Organize into 4 tracks. Each track builds on the previous.

#### Track 1: Foundations (4 modules)

```
Module 1: What Is Agentic AI? (Reading, 20 min)
- Definition: an AI system that can perceive, plan, act, and iterate in a loop
- Key difference from a simple LLM call: agents have memory, tools, and goals
- ReAct pattern (Reason + Act): the agent thinks step-by-step, then picks an action
- Relevance to API teams: think of an agent as an intelligent API orchestrator
- Resources: Yao et al. ReAct paper (2022), LangChain intro docs

Module 2: LLM Tool Calling & Function Use (Hands-on, 40 min)
- How modern LLMs (GPT-4, Claude 3, Gemini) expose a "tools" or "functions" parameter
- The model decides WHICH tool to call and with WHAT arguments — you execute it
- Direct parallel: your REST APIs are the tools. The LLM becomes the orchestrator.
- Exercise: Define a mock ISO 8583 parser as a "tool" and let Claude call it
- Resources: OpenAI function calling docs, Anthropic tool use docs

Module 3: Memory in AI Agents (Reading, 30 min)
- 4 types: in-context (conversation history), external (Redis/vector DB), episodic (logs), semantic (embeddings)
- You already use Redis → it becomes your agent's external memory store
- Short-term: what the agent remembers in one session
- Long-term: persisted learnings, past decisions, user preferences
- Exercise: sketch how your PR Review Log could feed an agent's long-term memory

Module 4: The Agent Loop — Plan → Act → Observe → Repeat (Reading + Video, 35 min)
- The fundamental while-loop of every agent
- Stopping conditions: task complete, error threshold, max iterations, human-in-the-loop
- Tools agents commonly use: web search, code execution, API calls, file read/write, database query
- Failure modes: infinite loops, hallucinated tool calls, compounding errors
- Resources: Anthropic's "Building effective agents" guide (2024)
```

#### Track 2: Architecture Patterns (4 modules)

```
Module 5: Multi-Agent Systems — Why One Agent Isn't Enough (Reading, 40 min)
- When a single agent becomes a bottleneck: too many tools, too complex a task
- Decompose tasks: each agent has ONE job and does it well
- Parallel vs sequential agent execution
- Real example: a code review system with 3 agents: spec checker, security scanner, style linter
- Synchrony API parallel: one agent per compliance domain (PCI, SOX, APIGEE policies)

Module 6: Orchestrator / Worker / Supervisor Patterns (Video + Reading, 50 min)
- Orchestrator: the "manager" agent — receives the goal, decomposes it, routes to workers
- Worker agents: specialized, narrow-scope, tool-equipped
- Supervisor: monitors quality of worker output, can reject and re-route
- Handoff protocol: how agents pass state/context to each other (structured JSON, not free text)
- Pattern to memorize: Orchestrator → [Worker A, Worker B] → Supervisor → Output
- Framework options: LangGraph, CrewAI, AutoGen, Semantic Kernel

Module 7: LangGraph — State Machines for Agent Workflows (Hands-on, 60 min)
- Core concept: your agent flow is a directed graph with nodes (agents/functions) and edges (routing logic)
- StateGraph: define your state schema → add nodes → add conditional edges → compile → invoke
- Why it matters: you get explicit control over agent flow — no magic, no unpredictability
- Persistence: LangGraph checkpoints map directly to Redis — you already have this infrastructure
- Exercise: Build a 3-node graph: InputParser → Validator → Formatter (mirrors your 105 parser logic!)
- Python-first but Java SDK exists; can also call LangGraph Cloud APIs from Spring Boot

Module 8: Agentic RAG — Dynamic Retrieval vs Static Context (Reading, 45 min)
- Classic RAG: embed documents → retrieve top-K → stuff into prompt
- Agentic RAG: the agent DECIDES when to retrieve, what to retrieve, and whether the result is good enough
- Self-RAG: agent scores its own retrieval and re-queries if quality is low
- Corrective RAG (CRAG): agent detects retrieval failures and falls back to web search
- Relevance to APIs: imagine an agent that retrieves the right API spec version before answering a question
```

#### Track 3: Applied Engineering (4 modules)

```
Module 9: Agents That Call REST APIs — Design Patterns (Workshop, 60 min)
- This is your home turf: designing agents that orchestrate REST API calls
- Pattern 1: Sequential API chaining (output of API-1 becomes input of API-2)
- Pattern 2: Parallel fan-out (call 3 APIs simultaneously, merge results)
- Pattern 3: Conditional routing (call API-A or API-B based on LLM decision)
- Pattern 4: Retry + fallback (agent retries on 429/500, falls back to cached response)
- Exercise: Design an agent that validates an ISO 8583 message by calling 3 different field validation APIs
- Key concern: token efficiency — don't dump entire API response into context, have agent extract only needed fields

Module 10: Human-in-the-Loop (HITL) Agents (Reading + Workshop, 50 min)
- Not every decision should be autonomous — some need human approval
- Interrupt patterns: agent pauses, notifies human, waits for input, resumes
- Approval workflows: agent proposes an action, human approves/rejects/modifies
- Audit trail: every human intervention logged (your Redis PR log is a prototype of this!)
- When to force HITL: financial transactions, compliance actions, schema changes, production deployments
- Exercise: Modify the PR Review Log concept so an AI agent drafts a review and a human approves it before saving

Module 11: Evaluating Agent Quality (Reading, 40 min)
- The hardest part of agentic AI: how do you know it's working well?
- Metrics: task completion rate, tool call accuracy, hallucination rate, latency per step, human override rate
- Tracing tools: LangSmith, Phoenix (Arize), Weights & Biases, custom Redis logging
- Regression testing: golden datasets — known inputs with expected outputs
- Common pitfalls: agents that "succeed" but do the wrong thing, agents that loop forever
- Relevance: your admin tool is perfect for building an eval dashboard — you already have the infra

Module 12: Security & Safety in Agentic Systems (Reading, 35 min)
- Prompt injection: malicious input that hijacks agent behavior — critical for API teams
- Tool call validation: always validate LLM-generated tool arguments before executing
- Principle of least privilege: agents should only have access to APIs they need for THIS task
- Rate limiting: agents can trigger hundreds of API calls — your API gateway must handle this
- Data leakage: agents with memory can accidentally surface sensitive data from past sessions
- Synchrony-specific: PCI DSS implications of AI agents accessing payment data fields
```

#### Track 4: Advanced & Cutting Edge (4 modules)

```
Module 13: Model Context Protocol (MCP) — The New Standard (Reading + Hands-on, 55 min)
- Anthropic's open standard for connecting AI models to tools and data sources
- Think of it as "USB for AI agents" — standardized connector between LLM and external systems
- MCP Servers expose: tools (functions), resources (data), prompts (templates)
- MCP Clients: Claude Desktop, Cursor, your custom Spring Boot app can be an MCP client
- How this changes everything: instead of writing custom tool definitions per model, write once as an MCP server
- Exercise: Define your 105 Parser as an MCP server tool — any AI model can now call it
- Resources: modelcontextprotocol.io, Anthropic MCP docs

Module 14: Agent-to-Agent Communication (A2A Protocol) (Reading, 45 min)
- Google's Agent-to-Agent (A2A) protocol — complements MCP
- How agents discover each other, exchange tasks, and return results
- Agent Card: a JSON manifest describing what an agent can do (like an OpenAPI spec for agents)
- Use case: your 5 API teams each build a specialized agent → they communicate via A2A
- Combined MCP + A2A: MCP connects agents to tools/data; A2A connects agents to each other
- This is the emerging standard stack for enterprise multi-agent systems in 2025+

Module 15: Agentic Coding Assistants — Learning from Claude Code & Copilot (Reading, 40 min)
- How tools like Claude Code, Cursor, and GitHub Copilot implement agentic loops
- The edit → test → observe → fix loop that agentic coders use
- How they handle large codebases: chunking, summarization, selective context loading
- Lessons for your team: when building AI features in your Spring Boot app, think in agent loops
- Applying this: imagine an AI agent that monitors your API codebase for breaking changes and auto-creates Jira tickets

Module 16: Capstone Project — Build a Real Multi-Agent System (Project, 3-4 hours)
- Build a 3-agent system directly relevant to your work at Synchrony/Birlasoft
- Suggested project: "API Health Intelligence Agent"
  - Agent 1 (Monitor): Reads API logs, detects anomalies, generates a structured incident report
  - Agent 2 (Analyzer): Takes the incident report, queries relevant API specs, identifies root cause
  - Agent 3 (Recommender): Generates remediation suggestions and drafts a PR review entry
  - Orchestrator: Coordinates the 3 agents, handles failures, writes final output to Redis
- Stack suggestion: Python (LangGraph) + calls to your Spring Boot APIs as tools
- Deliverable: working code in GitHub + a 1-page design doc explaining your agent architecture
- Present to your 5 teams as a demo
```

-----

### 4.4 Thymeleaf Page Structure for `/agentic`

#### Main Template: `agentic/hub.html`

```html
<!-- Extends base layout -->
<!-- Tab navigation: Modules | Team Board | Weekly Challenge | What's Next -->
<!-- Tab 1: Learning Modules -->
  <!-- Category filter pills: All | Foundations | Architecture | Applied | Advanced -->
  <!-- Module cards — one per LearningModule -->
  <!-- Each card: icon, title, type badge, category, duration, description, status button, reflection textarea -->
  <!-- Status button POSTs to /agentic/module/status via form or HTMX -->
  <!-- Reflection textarea auto-saves to /agentic/module/reflection -->
  <!-- Progress bar at top: X of 16 complete -->
  
<!-- Tab 2: Team Board (Leaderboard) -->
  <!-- Table: rank, badge, name, team, modules done, progress bar -->
  <!-- Refreshes from /agentic/leaderboard endpoint -->
  
<!-- Tab 3: Weekly Challenge -->
  <!-- Challenge title, description, due date, submission count -->
  <!-- Textarea form → POST /agentic/challenge/submit -->
  <!-- Upcoming challenges list -->
  
<!-- Tab 4: What's Next (Roadmap) -->
  <!-- Feature roadmap cards with status badges -->
```

#### Thymeleaf Fragments to Create

- `fragments/nav.html` — sticky top nav with active link highlighting
- `fragments/module-card.html` — reusable module card
- `fragments/status-badge.html` — colored status pill
- `fragments/progress-bar.html` — progress bar with percentage

-----

### 4.5 Service Layer

```java
@Service
public class AgenticLearningService {

    // Get all modules with the current user's progress overlaid
    public List<ModuleWithProgress> getModulesForUser(String userId);

    // Update a single module's status for a user
    public void updateModuleStatus(String userId, int moduleId, String status);

    // Save or update a reflection
    public void saveReflection(String userId, int moduleId, String reflection);

    // Get leaderboard: sorted list of users by doneCount
    public List<LeaderboardEntry> getLeaderboard();

    // Submit weekly challenge
    public void submitChallenge(String userId, int weekNumber, String text);

    // Get current week's challenge definition (hardcoded or from config)
    public WeeklyChallenge getCurrentChallenge();
}
```

-----

### 4.6 Weekly Challenges (Preload These 6 — Rotate Weekly)

These are designed to be directly relevant to the API work at Synchrony:

```
Week 1: Design an agent that detects ISO 8583 field mapping drift
- Given your 105 parser: sketch an agent that monitors field mapping config changes 
  across versions and alerts on breaking changes
- Define: agent goal, tools it needs, decision loop, when it escalates to a human

Week 2: Multi-agent PR review system
- Design a 3-agent pipeline: Spec Compliance Agent + Security Scan Agent + Code Quality Agent
- How does the orchestrator merge their outputs into a single PR review summary?
- How does your existing PR Review Log store the result?

Week 3: API latency monitoring agent
- Agent watches API response times, detects SLA breaches, generates incident report
- Define the tools: what APIs does it call? What does it read/write to Redis?
- When does it notify a human vs auto-remediate?

Week 4: Agentic documentation generator
- Agent reads your OpenAPI spec, detects undocumented endpoints, and drafts descriptions
- How does it handle ambiguous endpoints? What's the human approval step?
- How would you integrate this into a GitHub Actions pipeline?

Week 5: MCP server for your 105 Parser
- Define your 105 Parser as an MCP server with tool definitions
- What inputs does it expose? What output schema does it return?
- Which AI models could now call it? How does auth work?

Week 6: Build your team's Agent Registry
- Each of your 5 teams defines one "Agent Card" (A2A spec) for a hypothetical agent
- What can each agent do? What tools does it need? What does it return?
- How would an orchestrator discover and invoke all 5 agents to answer a complex question?
```

-----

### 4.7 Admin Features (RBAC — Admin Only)

When logged in as admin (you), show additional controls:

```
/agentic/admin:
- Enable/disable the Agentic AI Hub feature globally (toggle in Redis)
- View all users' module progress (not just your own)
- Post/edit the weekly challenge
- Download a CSV of all team reflections
- View aggregate stats: average completion %, most-skipped modules, most-reflected-on modules
- Manually award completion badge to a user
```

-----

## 🔧 Spring Boot Setup Checklist

### Dependencies to Add (pom.xml)

```xml
<!-- Already have these, confirm they're present -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>

<!-- Add for JSON serialization in Redis -->
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
</dependency>
```

### application.properties additions

```properties
# Redis (already configured — confirm these)
spring.redis.host=localhost
spring.redis.port=6379

# Feature flags (admin-controlled)
feature.parser.enabled=true
feature.prreview.enabled=true
feature.agentic.enabled=true

# Agentic hub config
agentic.modules.total=16
agentic.leaderboard.cache.ttl.minutes=5
agentic.current.week=1
```

-----

## 📁 Suggested File Structure

```
src/main/
├── java/com/applyadmin/
│   ├── controller/
│   │   ├── DashboardController.java       ← NEW
│   │   ├── ParserController.java          ← existing
│   │   ├── PrReviewController.java        ← existing
│   │   └── AgenticController.java         ← NEW
│   ├── service/
│   │   ├── AgenticLearningService.java    ← NEW
│   │   ├── LeaderboardService.java        ← NEW
│   │   └── FeatureToggleService.java      ← NEW
│   ├── model/
│   │   ├── ModuleProgress.java            ← NEW
│   │   ├── LearningModule.java            ← NEW
│   │   ├── ChallengeSubmission.java       ← NEW
│   │   └── LeaderboardEntry.java          ← NEW
│   └── config/
│       ├── ModuleConfig.java              ← NEW (loads all 16 modules)
│       └── RedisConfig.java               ← existing/extend
│
└── resources/
    ├── templates/
    │   ├── dashboard.html                 ← NEW
    │   ├── agentic/
    │   │   ├── hub.html                   ← NEW
    │   │   └── admin.html                 ← NEW
    │   └── fragments/
    │       ├── nav.html                   ← NEW (shared nav)
    │       ├── module-card.html           ← NEW
    │       └── status-badge.html          ← NEW
    └── static/
        └── css/
            └── applyadmin.css             ← ADD design tokens here
```

-----

## 🚀 Build Order (Step-by-Step for AI Assistant)

Tell your AI coding assistant to follow this order:

```
Step 1: Create CSS design tokens in applyadmin.css with all variables listed above
Step 2: Create nav fragment (fragments/nav.html) with all 4 nav items and active state
Step 3: Create Dashboard page (dashboard.html + DashboardController.java)
Step 4: Create LearningModule model and ModuleConfig with all 16 modules hardcoded
Step 5: Create ModuleProgress model and Redis serialization
Step 6: Create AgenticLearningService with all methods listed above
Step 7: Create AgenticController with all routes listed above
Step 8: Create hub.html with all 4 tabs (Modules, Team Board, Weekly Challenge, What's Next)
Step 9: Create module-card fragment and wire status update form
Step 10: Add leaderboard endpoint and team board tab
Step 11: Add weekly challenge form and submission endpoint
Step 12: Add admin panel at /agentic/admin (RBAC-gated)
Step 13: Test full flow: login → dashboard → click Agentic Hub → mark modules → view leaderboard
```

-----

## 🎯 Success Criteria

The implementation is complete when:

- [ ] Dashboard loads at `/` or `/dashboard` showing all 3 feature cards
- [ ] Clicking each feature card navigates to correct route
- [ ] Agentic AI Hub shows all 16 modules with correct metadata
- [ ] User can click status button to cycle: Not Started → In Progress → Done
- [ ] Status persists across logout/login (stored in Redis per userId)
- [ ] User can write and save a reflection per module
- [ ] Leaderboard shows all team members sorted by completion count
- [ ] Weekly challenge form submits and saves to Redis
- [ ] Admin user sees additional controls not visible to regular users
- [ ] All pages use consistent color palette, typography, and nav

-----

## 📝 Notes for GitHub

- Branch name suggestion: `feature/agentic-learning-hub`
- Commit message convention: `feat(agentic): add module progress persistence to Redis`
- This prompt file lives at: `docs/prompts/apply-admin-agentic-hub-prompt.md`
- Pull this file and paste into Claude/Copilot/Cursor when resuming work
- Tag stable builds: `v1.0-dashboard`, `v1.1-agentic-hub`, `v1.2-leaderboard`

-----

*Generated for: Synchrony Financial / Birlasoft APIs Team*
*Stack: Java Spring Boot · Thymeleaf · Redis · RBAC*
*Last updated: March 2026*
