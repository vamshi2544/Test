
# ReleasePilot — End-to-End Plan & Open Questions

**Read this top-down.** It captures the design we built decision-by-decision, the FAQ implications, what to research with Rovo this week, and what to ask in office hours. Power Automate is intentionally absent (not an approved hackathon tool).

-----

## 1. What we’re building (one paragraph)

When a release is ready, a human comments `@release-pilot go` on a Jira story. The agent (Copilot Studio) wakes, reads the story + Fix Version + sibling stories + subtasks to understand the whole release, fills a release Excel from a template, builds a Confluence release page, advances the change tickets in ServiceNow, and notifies people in Teams. Approvals can happen later (hours or days) — when they do, the agent re-wakes and moves the release forward. **Per the hackathon FAQ, no real Synchrony data is used — the data layer is mocked, the architecture is real.**

-----

## 2. The decisions we locked, in order (top-down)

|Layer        |Question                                                             |Decision                                                                                         |Why                                                                                                                  |
|-------------|---------------------------------------------------------------------|-------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------|
|1            |How does the human signal?                                           |**Comment** on a Jira story (e.g. `@release-pilot go`)                                           |Most natural for the team; works in every path                                                                       |
|2            |What notices the comment?                                            |**Polling** (the agent asks Jira on a timer)                                                     |Outbound is the easy direction; sidesteps inbound-firewall and Premium walls                                         |
|3            |The human may comment on 1 story or several — what does the agent do?|Whichever story it sees, it reads the **Fix Version** and derives **all** release stories from it|Always processes the complete release. Adds a “is this release already in progress?” check to avoid double-processing|
|4            |What does the agent gather to understand the release?                |**Main story + subtasks** from Jira only (full understanding)                                    |Jira is the source of truth; ServiceNow is a destination, not a source                                               |
|5            |Order of work after understanding                                    |Build **Excel** → build **Confluence page** → update **ServiceNow** → notify in **Teams**        |Matches the real release workflow                                                                                    |
|Eng          |Who runs the logic?                                                  |**Copilot Studio** (the agent itself) — its built-in **HTTP node** calls Jira/SNOW/Confluence    |Approved tool; cloud-to-cloud; no Power Automate                                                                     |
|Doc store    |Where do filled docs live?                                           |**Confluence** (attach the filled Excel to the release page)                                     |Avoids SharePoint’s heavy Entra app-registration auth                                                                |
|Trigger style|Instant push or polling?                                             |**Polling**                                                                                      |Inbound (Jira Cloud → internal) needs Apigee + OAuth that Jira Automation can’t do gracefully                        |

-----

## 3. The architecture (current target)

```
HUMAN comments "@release-pilot go" on a Jira story
       │
       ▼
COPILOT STUDIO  (scheduled trigger — polls Jira)
       │
       │  (Copilot Studio's HTTP node makes every call)
       │
       ├──► JIRA          → read story, find Fix Version, find sibling stories, read subtasks
       ├──► ROVO          → reason over Jira/Confluence content (Atlassian-specific AI)
       ├──► EXCEL         → fill the release template from understanding
       ├──► CONFLUENCE    → publish release page + attach the filled Excel
       ├──► SERVICENOW    → advance CHG state (mocked per FAQ — see Section 5)
       └──► TEAMS         → notify channel: ready / approval needed / done
```

**Later invocations** (approval events, Implemented event) follow the same pattern: Copilot Studio wakes, re-derives release context from CHG work-note tags + sibling query, takes one small action, exits.

-----

## 4. Detailed agent behavior (what happens, step by step)

When the agent wakes (every ~1 minute via Copilot Studio’s scheduled trigger):

1. **Check for go-comments.** Call Jira: search for comments containing `@release-pilot go` added since the last run.
1. **For each matching story (deduplicated by Fix Version):**
- Read the story → extract Fix Version.
- **Idempotency check** — has this Fix Version already been processed (e.g., does any CHG in it carry the `ReleasePilot:managed` work note)? If yes, skip.
- Search Jira for all stories with that Fix Version.
- For each story, read subtasks.
1. **Understand the release** — pass the structured story+subtask data through the AI (Copilot Studio’s reasoning, optionally Rovo for Atlassian-specific patterns) to produce: a release summary, a consolidated deploy/rollback/verify plan, risk callouts.
1. **Build the Excel** — fetch the template, fill cells from the understanding.
1. **Build the Confluence page** — generate content from the understanding, publish to the release space, attach the filled Excel.
1. **Update ServiceNow CHGs** — for each CHG in the release: add the work-note tag (`ReleasePilot:managed | fixVersion=X | confluence=URL`), attach the Confluence link, advance state to “Assess and Evaluate.”
1. **Notify Teams** — post a card to the release channel: “Release X compiled. Approvers notified.”
1. **Exit cleanly.** Next wake-up handles approval events and downstream stages.

-----

## 5. The FAQ implication — mock the data layer

**The rule:** no real Synchrony data; mocks are explicitly permitted; “architecture should be right; full solution should work end to end.”

**What that means in practice:**

- Real **Copilot Studio** (the agent itself is real — that’s the whole point)
- Real **Teams** for the notification visual (just a demo channel)
- **Mock Jira** OR a personal Jira sandbox with fake stories
- **Mock ServiceNow** — a small REST API that mimics SNOW’s `/api/now/table/change_request` endpoints, statefully (when the agent updates a CHG, the mock remembers, so the next read shows the new state)
- **Real Confluence** (or also mocked) — pick based on what we have access to
- **Rovo** — only if accessible; otherwise the Copilot Studio reasoning step covers the AI need

**Why stateful mock, not Postman static mocks:** the demo needs to show state moving — the agent updates a CHG, audience sees the new state. Postman static responses won’t show that.

**Where the mock runs** — this is the question still open (Section 7 OF-SCOPE1). Either:

- **Outside the network** (Vercel/Render/Fly free tier) — simplest, but needs organizer permission
- **Inside the network** (PCF) — possible, but Copilot Studio reaching internal PCF likely needs Apigee inbound

-----

## 6. Pre-work we can do NOW, regardless of which path the office hours picks

These are all the same across every scope answer. Don’t wait — start these.

1. **Design the mock API contract.** List every endpoint Copilot Studio will call, the URL pattern, the request/response shape. Mirror real Jira & SNOW REST API shapes exactly (so “architecture is real”). One doc; ~30 endpoints across Jira + SNOW.
1. **Create the mock data.** Three fake Release Stories under one Fix Version, ~7 subtasks each (mix of complete + one deliberately incomplete for the agent to flag), 8 fake CHGs, 15-20 fake historical incidents for later risk-reasoning. (Workstream 1 packet covers this.)
1. **Write the Copilot Studio prompts.** P1 (release summary), P2 (subtask classification), P3 (Confluence page body), P5 (approver card), etc. These are agent design, not infrastructure — write them now in plain text, paste into Copilot Studio later.
1. **Design the Adaptive Card layouts** for Teams (confirm-scope card, draft-review card, approval card). Use `adaptivecards.io/designer` — no access needed.
1. **Define the demo flow** — minute-by-minute, what shows on screen, what gets said. (Workstream 4 packet.)
1. **Draft the Confluence + Excel templates** (Workstream 3 packet) — the shape of what the agent fills in.
1. **Draft the pitch deck** (Workstream 2 packet) — pain, value math, architecture slide, “why agentic” slide, path-to-pilot.

When office hours resolves scope, you plug URLs/credentials into the already-built pieces. **You’ll be building, not deciding.**

-----

## 7. Office-hours questions (the ones that decide everything)

### Scope (where can we build and run?)

- **OF-SCOPE1.** “Per the no-real-data rule, we plan to mock Jira/ServiceNow. Is the mock permitted to run on a **public host outside the SYF network** (e.g., Vercel), or must it run on SYF infrastructure (PCF/internal)?”
- **OF-SCOPE2.** “Are **personal sandboxes** (e.g., personal free Jira Cloud) acceptable for the demo, or must everything use SYF-tenant systems?”
- **OF-SCOPE3.** “If we must stay on SYF infrastructure: does the SYF-provided **Copilot Studio environment have the ability to call internal SYF endpoints directly**, or do we need to expose mocks through Apigee?”

### Copilot Studio (the one technical question that matters most)

- **OF-CS-CREDITS.** “How does our hackathon Copilot Studio environment **meter usage** — per message, per AI generation, or per HTTP-node call — and how many credits do we have? Enough for repeated demo runs?”

### Rovo

- **OF-ROVO.** “Is Rovo licensed and reachable from our hackathon Copilot Studio agent? If yes, what’s the supported way to invoke it from a Copilot Studio topic?”

### ServiceNow

- **OF-SNOW.** “Is there a sanctioned non-prod ServiceNow we can hit with REST API + write rights on change_request? If not, does the FAQ’s mocking permission cover building our own mock SNOW?”

### Confluence + Teams

- **OF-CONF.** “Can our hackathon Copilot Studio agent publish a page in a demo Confluence space?”
- **OF-TEAMS.** “Can our agent post to a Teams channel of our choosing?”

-----

## 8. What to ask Rovo (use this BEFORE office hours)

Rovo can search SYF Confluence and Jira. It probably has answers to several of our open questions buried in internal documentation. Try these prompts, in order. **For each: capture the answer, the source page Rovo cites, and note any gaps.**

### About Copilot Studio at SYF

1. *“What Copilot Studio environments does Synchrony provide for development or hackathons, and what licenses or message capacity are included?”*
1. *“How does a Copilot Studio agent in our environment call external REST APIs — is the HTTP request node available, and are there usage limits?”*
1. *“Can a Copilot Studio agent call internal Synchrony endpoints directly, or must calls go through Apigee?”*
1. *“Are there any approved patterns or example agents that integrate with Jira or ServiceNow at Synchrony?”*

### About connecting tools to outside services

1. *“From a Synchrony-hosted application, what is the approved way to call an external REST API on the public internet? Do all outbound calls need to go through Apigee, or are there exceptions?”*
1. *“For a hackathon, are we permitted to host code or APIs on external services like Vercel, Render, or Fly.io for the demo?”*
1. *“What is the policy on using personal Atlassian or Microsoft accounts for hackathon demos?”*

### About ServiceNow access

1. *“Is there a non-production ServiceNow instance that developers can use for testing, with REST API access?”*
1. *“What is the process to get REST API credentials for ServiceNow with permission to update change_request records in a non-prod environment?”*

### About Jira

1. *“Does Synchrony have a non-production Jira instance available for development or hackathon use? How do we request access?”*
1. *“What is the supported way for an automated agent to read stories and post comments in Jira using an API token or service account?”*

### About the hackathon specifically

1. *“What tools, environments, and credentials are provided to participants of the [hackathon name] event? Where is the official setup documentation?”*
1. *“For the hackathon, are participants allowed to build solutions that run partially or wholly outside the Synchrony network if they only use mocked data?”*
1. *“Are there example projects from past Synchrony hackathons that integrated Copilot Studio with Jira or ServiceNow?”*

### About Rovo itself

1. *“What is Rovo licensed to do at Synchrony, and which Atlassian content can Rovo agents search and reason over?”*
1. *“Can Rovo be invoked from a Copilot Studio agent, and if so, how?”*

**After Rovo:** the answers you get either resolve a question (cross it off the OF list) or sharpen it (rephrase the OF question with the new context). Either way, you walk into office hours with fewer unknowns.

-----

## 9. Decisions still open (and what each answer triggers)

|Open question                             |If YES                                  |If NO                                       |
|------------------------------------------|----------------------------------------|--------------------------------------------|
|**OF-SCOPE1**: mock outside SYF allowed?  |Build mock on Vercel; cleanest path     |Mock on PCF; deal with Apigee inbound       |
|**OF-SCOPE2**: personal sandboxes allowed?|Use personal Jira Cloud for live trigger|Mock Jira too / use SYF Jira QA             |
|**OF-SCOPE3**: SYF CS reaches internal?   |Internal-only path is easy              |Apigee plumbing required for internal mocks |
|**OF-CS-CREDITS**: enough credits?        |Run demo freely                         |Limit demo runs; pre-record key segments    |
|**OF-ROVO**: Rovo accessible?             |Use for Atlassian reasoning + risk      |Skip — CS reasoning covers the same need    |
|**OF-SNOW**: non-prod SNOW available?     |Use real-shape integration              |Mock SNOW (which is the plan anyway per FAQ)|

-----

## 10. Action list (this week, in order)

1. **Run the Rovo prompts** above. Capture answers. ~1 hour.
1. **Send the four workstream packets** (B1-B4) to teammates with the 60-second project overview. They can start the pre-work today regardless of office-hours outcome.
1. **Bring the OF questions to office hours.** Ask the SCOPE ones first; everything else branches from those.
1. **Update this plan** with answers as they come in.
1. **Start building** on the path the answers point to.

-----

## Notes for the record

- The hackathon is **June 8-9**. Office hours on **May 29** and **June 5**. Copilot Studio session **June 4**.
- Approved tools: **Copilot Studio, Rovo, GitHub Copilot**. (Power Automate is NOT approved.)
- The biggest risk is no longer “can we build it” — it’s “where are we permitted to run the demo.” That’s a permission question, not an engineering one, and the OF-SCOPE questions resolve it.
- Vamshi will pursue ReleasePilot as a real project regardless of hackathon outcome. This plan supports both paths.
