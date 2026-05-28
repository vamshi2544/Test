# Copilot Show & Tell Deck — Generation Prompt

> Paste this entire file into Copilot (or any LLM agent) to regenerate the deck.
> Output target: PowerPoint (.pptx). Keep the visual language identical to our prior leadership deck.

-----

## 1. CONTEXT

You are creating a **PowerPoint deck** for an internal engineering “AI Show & Tell” session at Synchrony Financial.

**Audience:** ~130 engineers across 10 teams on the Agile Release Train.
**Format:** ~20 minute walkthrough + Q&A.
**Tone:** Show-and-tell, NOT a rollout mandate. The presenter is *sharing* what their team has been building. Audience walks away either wanting to try it OR simply aware that it exists.

**Critical vocabulary rule:**

- DO NOT use the words “PI,” “sprint,” “PI planning,” or “PI objectives” anywhere.
- Use “iteration,” “Copilot-first stories,” and “lead reviews” instead.
- This is for an Agile Release Train audience; the presenter does not want to be quoted as defining process.

-----

## 2. VISUAL LANGUAGE (match the prior leadership deck exactly)

**Palette (use these hex values, no others):**

- Charcoal (primary ink): `#1A1A1A`
- Gold (accent): `#FFC72C`
- Soft gold (eyebrow background): `#FFE9A8`
- Deep gold (eyebrow text): `#B88600`
- Slate (secondary text): `#4A4A4A`
- Muted (footer/labels): `#6B6B6B`
- Card cream: `#F6F4ED`
- Alt background: `#FBFAF6`
- Line/divider: `#E8E6E0`
- Status colors: green `#1F7A4D`, amber `#B45309`, blue `#1F4E79`
- White: `#FFFFFF`

**Typography:**

- Heading font: Calibri Bold
- Body font: Calibri
- Code/monospace: Consolas

**Layout:**

- Slide size: 13.3” × 7.5” (LAYOUT_WIDE)
- Outer margin: 0.55”

**Recurring visual motifs (use on every content slide):**

- **Topbar:** small gold square + “AI SHOW & TELL” in ink, right-aligned slide topic label in muted gray, thin separator line below.
- **Eyebrow pill:** soft-gold background, deep-gold uppercase text in the format `0X  —  TOPIC NAME`. Size the pill width to fit on one line (allow ~0.105” per character + 0.4” padding, minimum 1.4” wide).
- **Footer:** thin top line, “SHOW & TELL  •  COPILOT IN PRACTICE” lower-left, page number “0X / 13” lower-right.
- **Cards:** cream fill (`#F6F4ED`), thin line border, 4px left accent in gold, optional top accent in gold.
- **Dark cards:** charcoal fill, white text, gold left accent — used for “developer owns,” “status,” “boundary,” contact info.

**Do NOT use:**

- Decorative full-width colored bars, ribbons, or stripes
- Accent lines under titles (use whitespace instead)
- Centered body text
- Cream/beige slide backgrounds — main slide background must be pure white
- More than 2 columns of bullets per slide
- Emoji in slide text (acceptable in icons/markers like ✓ and ⚠)

-----

## 3. DECK STRUCTURE (13 slides — keep this exact order)

### Slide 1 — Title

**Layout:** Two-panel. Left ~60% width is charcoal background; right ~40% is gold background.

**Left panel (charcoal):**

- Kicker text in gold: `AI SHOW & TELL  •  ENGINEERING`
- Headline in white with the word “Copilot” in gold:

> How we’re actually using **Copilot** in day-to-day work.
- Subtitle in light gray (#D6D4CE):

> A walkthrough of the prompts, templates, and agents our team has been building. Not a rollout — a look under the hood, with everything live in Bitbucket if you want to see it.
- Meta row at bottom — three columns: Format / Audience / Time
  - `FORMAT` → `Show & Tell`
  - `AUDIENCE` → `10 teams • 130 engineers`
  - `TIME` → `~20 min + Q&A`
- Soft gold radial glow in upper right corner (subtle, low opacity)

**Right panel (gold):**

- Header in charcoal, letter-spaced: `WHAT WE'LL COVER`
- Five pillar boxes stacked vertically. Each: slightly darker gold background, charcoal left accent bar, bold title in charcoal, sub-line in dark gray:
1. **Adoption story** — How we’re easing into it — Copilot-first stories, lead reviews
1. **Master prompt + templates** — What drives the agents, and how templates flex per task
1. **Story Implementation Agent** — The workhorse — spec to audit
1. **Dependency Map Generator** — In progress — what it’ll do
1. **Release Review Agent** — Findings, suggestions, release cautions

-----

### Slide 2 — Agenda

**Eyebrow:** `02  —  WHAT WE'LL COVER`
**Title:** Five things. Twenty minutes. Then questions.
**Lede:** No takeaways required. The goal is to show what we’ve been building so you know it exists — borrow what’s useful, ignore what isn’t.

**Body:** 5-column grid. Each column is a cream card with gold top accent. Inside each: charcoal circle with gold number, bold title, muted body text.

|#|Title                     |Body                                                                              |
|-|--------------------------|----------------------------------------------------------------------------------|
|1|How we’re easing it in    |Copilot-first stories, iterations, lead reviews — what’s worked, what hasn’t.     |
|2|Master prompt + templates |The single prompt that drives the agents, and templates tuned per task.           |
|3|Story Implementation Agent|How a story goes from spec to reviewed implementation, with the human in the loop.|
|4|Dependency Map Generator  |In progress — what we’re building and the problem it solves.                      |
|5|Release Review Agent      |Pre-release findings, suggestions, and the cautions we want flagged.              |

-----

### Slide 3 — Adoption Story

**Eyebrow:** `03  —  ADOPTION STORY`
**Title:** We didn’t drop it on the team. We eased it in.
**Lede:** Four iterations from “let’s explore” to “everyone has hands on it.” Each one is short, reviewed by the lead, and the takeaways feed the next.

**Body:** 4-column grid. Each column has a charcoal header bar containing `Iteration N` (left) and a colored tag pill (right, gold background, charcoal text). Below: cream-ish body with title, bullet list, and a small gold-deep stat line at bottom.

|Iter|Tag          |Title                     |Bullets                                                                                                                                                    |Stat                       |
|----|-------------|--------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------|
|1   |EXPLORE      |Just play with it         |Everyone tries Copilot on safe tasks · Capture what works, what’s weird · No production code from Copilot yet · Identify what’s worth automating           |Goal: familiarity          |
|2   |COPILOT-FIRST|Two stories, deep review  |2 stories implemented via the agent workflow · Lead reviews the output end-to-end · Refine the master prompt + templates · Team learns from the walkthrough|2 Copilot-first stories    |
|3   |ROTATE       |Add one. Rotate ownership.|+1 story, different developer owns each · Lead review continues · Document recurring patterns and gotchas · Patterns become reusable templates             |3 stories • rotation begins|
|4+  |SPREAD       |Hands on across the team  |Scale story count gradually · Every developer authors and reviews · Release Review Agent now active · Findings shared back to the train                    |Now                        |

-----

### Slide 4 — Master Prompt

**Eyebrow:** `04  —  MASTER PROMPT`
**Title:** One prompt sets the rules. The agents follow it.
**Lede:** The master prompt is the single source of truth for how Copilot should behave when it’s working on a story. Every agent inherits from it.

**Body:** Two columns.

**Left (~42% width):** Dark charcoal code-block card with gold left accent. Use Consolas font. Syntax-color: comments in gray, tags like `[ROLE]` in purple, keywords like `Read/Use/Know/1./2./3./4.` in gold, string values in cream-yellow. Include abbreviated content:

```
# master-prompt.md  (abbreviated)

[ROLE]
You are an engineering co-pilot for the
API platform team. You follow our
standards before your own preferences.

[CONTEXT]
- Read: /standards/*.md
- Use:  /templates/<task>.md
- Know: repo structure, conventions

[WORKFLOW]
1. Read spec, audit for gaps
2. Produce a plan, ask for approval
3. Implement (after plan approved)
4. Self-audit, surface risks

… see /prompts/master-prompt.md in Bitbucket
```

**Right (~52% width):**

- Heading: “What it does”
- 6 bold-then-explanation bullets:
  - **Sets role and posture** — the agent acts as a co-pilot, not an autonomous developer
  - **Points to standards** — coding, naming, observability, security guidelines
  - **Selects templates** — picks the right template for the task (story, refactor, bug, doc)
  - **Defines workflow** — read → audit spec → plan → developer approval → implement → audit
  - **Locks in the gate** — no implementation without an approved plan
  - **Standardizes output** — every story produces the same reviewable shape
- Below the bullets, a dark callout card with gold left accent:

> Edits to the master prompt go through PR. Same review bar as code.

**Do NOT include the actual prompt text.** The presenter will open it in Bitbucket live if asked.

-----

### Slide 5 — Templates

**Eyebrow:** `05  —  TEMPLATES`
**Title:** Templates are task-specific. That’s the point.
**Lede:** A new endpoint has different requirements than a bugfix. Each template encodes “how our team does this kind of work” so the agent doesn’t have to guess.

**Body:** Two columns.

**Left (~57% width):** Stacked rows of template cards. Each row: cream background, gold left accent, small badge pill (gold for TASK, charcoal for SHARED), template name (bold), one-line description (muted).

|Badge |Name                 |Description                                                                                |
|------|---------------------|-------------------------------------------------------------------------------------------|
|TASK  |New REST endpoint    |Spec fields for path, auth, payloads, error contract, observability, test coverage targets.|
|TASK  |Backend bugfix       |Reproduce-first format: failing test, root cause hypothesis, fix scope, regression test.   |
|TASK  |Kafka consumer change|Topic, partition strategy, idempotency, DLQ handling, replay safety, telemetry checklist.  |
|TASK  |DB schema migration  |Forward + rollback, data backfill plan, lock impact, deployment ordering, observability.   |
|SHARED|Story spec           |The handshake the agent expects before it starts. Scope, AC, NFRs, assumptions.            |
|SHARED|Implementation plan  |What the agent produces and the developer approves before code is generated.               |
|SHARED|Audit report         |Findings, test gaps, risk notes, standards violations, missed requirements.                |

**Right (~37% width):**

- Heading: “Why task-specific matters”
- 5 bullets:
  - Sharper output — agent isn’t guessing what fields matter
  - Faster review — reviewer scans the same shape every time
  - Captures tribal knowledge — “we always need DLQ handling for Kafka” lives in the template
  - Easy to evolve — when a pattern breaks, fix the template, every future story benefits
  - Reusable across teams — templates work the same regardless of repo
- Below, a small cream “QUICK STAT” callout:

> After templates were tuned, time-to-plan-approval dropped noticeably — fewer back-and-forth cycles on “did you mean…”

-----

### Slide 6 — Story Implementation Agent: Workflow

**Eyebrow:** `06  —  STORY IMPLEMENTATION AGENT`
**Title:** How a story actually flows through the agent.
**Lede:** Eight steps. The middle one — in gold — is the developer’s approval gate. Code is never generated until the plan is signed off.

**Body:** 8-column horizontal flow. Each step is a small card with a charcoal numbered circle, bold title, short description. **Step 5 must be filled gold** (not cream) — this is the human approval gate.

|#    |Title            |Description                                          |
|-----|-----------------|-----------------------------------------------------|
|1    |Read context     |Repo, standards, story.                              |
|2    |Ask for spec     |Pulls from spec template.                            |
|3    |Audit spec       |Gaps, assumptions.                                   |
|4    |Plan             |Implementation plan.                                 |
|**5**|**Approval gate**|**Approve · iterate · reject · rewrite.** (GOLD FILL)|
|6    |Implement        |Only after approval.                                 |
|7    |Self-audit       |Tests, risk, standards.                              |
|8    |Output           |Reviewable format.                                   |

**Below the flow:** A small legend (cream swatch = “Agent step”, gold swatch = “Human approval gate”).

**Below the legend:** Two side-by-side cards.

- **Left (cream):** “What the agent does” with 4 bullets: Reads the story spec and the right task template · Produces a plan in a consistent format · After approval, writes code that respects standards · Audits its own output before handing back.
- **Right (charcoal, white text):** “What the developer owns” with 4 bullets: Final scope and acceptance criteria · Architecture and design choices · Test strategy adequacy · Merge and ship decision.

**IMPORTANT:** Keep the two bottom cards short enough so they don’t overlap the footer. Card height around 1.55” if using a 7.5”-tall slide.

-----

### Slide 7 — Sample Story Walkthrough

**Eyebrow:** `07  —  WHAT IT LOOKS LIKE IN PRACTICE`
**Title:** One story, three artifacts, end to end.
**Lede:** Here’s what a developer actually sees — the spec they wrote, the plan the agent produced, and the audit at the end. All version-controlled.

**Body:** 3 side-by-side document cards with an arrow circle between cards 1→2 and 2→3. Each card has a header row with the filename (Consolas font) and a colored tag pill.

**Card 1 — `STRY-2087/spec.md` (tag: Input, cream):**

- **Title:** Add cursor pagination to /v2/applications
- **Scope:** New cursor + limit query params; nextCursor in response.
- **Out of scope:** Filtering, sorting changes.
- **AC:** First page = no cursor; cursor opaque; idempotent; max limit 100.
- **NFR:** p95 ≤ 250ms; no schema changes; backward-compatible.
- **Assumptions:** Existing index on (created_at, id) sufficient.

**Card 2 — `STRY-2087/plan.md` (tag: Plan, gold):**

- **Approach:** Keyset pagination using (created_at, id).
- **Changes:**
  - ✓ DTO: add cursor, limit, nextCursor
  - ✓ Repo: keyset query with bounded limit
  - ✓ Controller: validate cursor opacity (base64)
  - ✓ Tests: 6 unit + 3 integration
- **Risks flagged:** *cursor stability if rows are updated* (in amber/bold)
- **Questions for dev:** Confirm nextCursor nullable on last page?

**Card 3 — `STRY-2087/audit.md` (tag: Audit, gold):**

- ✓ All acceptance criteria covered (green)
- ✓ 9 tests added, coverage delta +2.4% (green)
- ✓ Standards: naming, logging, errors OK (green)
- **Findings:**
  - ⚠ No test for limit>100 rejection — added. (amber)
  - ⚠ Missing metric for keyset miss — added. (amber)
- **Ready to review:** Yes — see PR #4421 (green, bold)

*Note: The presenter should replace STRY-2087 and PR #4421 with real identifiers before showing.*

-----

### Slide 8 — Dependency Map Generator (in progress)

**Eyebrow:** `08  —  IN PROGRESS`
**Title:** Dependency Map Generator — knowing what you’ll touch before you touch it.
**Lede:** When you change an endpoint, what else does it affect? Today that’s tribal knowledge. We’re building an agent that surfaces it automatically.

**Body:** 3-column card row. First two cream, third charcoal. Each has uppercase eyebrow label at top, bold subtitle, then 4 bullets.

|Column  |Label       |Subtitle                    |Bullets                                                                                                                                                                                                      |
|--------|------------|----------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
|1       |PROBLEM     |Blast radius is invisible   |Engineer changes a contract, downstream breaks · Service catalog and code aren’t in the developer’s head · Reviewers can’t catch what they can’t see · Risk gets discovered in QA or prod, not at design time|
|2       |WHAT IT DOES|Reads context, draws the map|Scans repo + service catalog + recent traffic · Identifies callers, schemas, message consumers · Surfaces what else might break if this changes · Feeds the result into the implementation plan              |
|3 (dark)|STATUS      |In progress                 |Prototype reads repo + Apigee config · Next: consumer/producer graph from Kafka · Target: blast-radius section in every plan · Will surface caution flags to Release Review                                  |

**Below the three cards:** A full-width cream callout with gold left accent:

- Eyebrow: `WHY THIS MATTERS`
- Body: A plan is only as good as what the agent knows. With the dependency map, it’ll know what’s downstream and ask better questions earlier — before code is generated.

-----

### Slide 9 — Release Review Agent

**Eyebrow:** `09  —  RELEASE REVIEW AGENT`
**Title:** Release Review Agent — findings, suggestions, cautions.
**Lede:** Separate from the implementation agent. Its job is to look at what’s about to ship and surface what should give you pause.

**Body:** Same 3-column structure as Slide 8 (two cream, one charcoal).

|Column  |Label   |Subtitle          |Bullets                                                                                                                                                                                             |
|--------|--------|------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
|1       |INPUTS  |What it reads     |Audit findings from each story in the release · Test results + coverage deltas · Schema, contract, and config changes · Open risks and accepted assumptions · Standards exceptions logged in stories|
|2       |OUTPUT  |What it produces  |Concise release summary, one page · Top concerns flagged with severity · Suggested pre-release actions · Specific cautions — schema, PII, payment, breaking · Sign-off checklist for the lead       |
|3 (dark)|BOUNDARY|What it doesn’t do|Approve or block releases on its own · Replace QA, security, or change management · Override findings from the implementation audit · Operate outside our repo’s scope                              |

-----

### Slide 10 — End-to-End Picture

**Eyebrow:** `10  —  END-TO-END PICTURE`
**Title:** How the pieces fit together.
**Lede:** One story flows through prompts, agents, and templates. Inputs in blue, agents in gold, outputs in green.

**Body:** A large cream-bordered container holding a horizontal flow of 4 boxes connected by arrows, plus a second row showing how the Dependency Map feeds in.

**Top row (left to right):**

- **Input** (blue left accent): Story spec — Scope, AC, NFRs, assumptions.
- → arrow
- **Agent** (gold left accent): Story Impl Agent — Master prompt + task template.
- → arrow
- **Output** (green left accent): Plan + audit — Approved plan, audited code.
- → arrow
- **Agent** (gold left accent): Release Review — Findings, cautions, sign-off.

**Second row:**

- **Feeding in** (amber left accent): Dependency map — *In progress — will inform planning.* (in amber, bold)
- ↗ amber arrow pointing up-right toward the agent box in row 1
- Muted italic explanation: *Map feeds the planning step so blast radius is visible before code is generated.*
- On the right side: muted italic note: *Release Review aggregates audits across stories in the release.*

-----

### Slide 11 — What’s Working / What’s Tricky

**Eyebrow:** `11  —  REAL TALK`
**Title:** What’s working. What’s tricky.
**Lede:** An honest read after a few iterations — the wins, and the things that still need work.

**Body:** Two side-by-side columns.

**Left column** (light green background `#F4FAF6`, green left accent):

- Eyebrow: `✓  WHAT'S WORKING` (in green)
- Subtitle (in green): Where it’s earning its place
- Bold-then-explanation bullets:
  - **Scaffolding new endpoints** — templates pay for themselves immediately
  - **Test generation** — once the spec is clean, coverage is faster
  - **Bugfix reproduce step** — agent writes the failing test first
  - **Plan reviews** — catching scope creep before code is written
  - **Consistency** — every story produces the same reviewable shape

**Right column** (light amber background `#FEF7EC`, amber left accent):

- Eyebrow: `⚠  WHAT'S TRICKY` (in amber)
- Subtitle (in amber): Where we’re still iterating
- Bold-then-explanation bullets:
  - **Ambiguous specs** — garbage in, garbage out — audit step eats time
  - **Cross-service awareness** — agent doesn’t see dependencies (next agent fixes this)
  - **Novel architecture choices** — agent leans toward what’s in the repo
  - **Highly-stateful logic** — race conditions need a human-first eye
  - **Some tasks are slower** — for 10-line tasks, workflow overhead doesn’t pay

-----

### Slide 12 — Want to Try It?

**Eyebrow:** `12  —  WANT TO TRY IT?`
**Title:** No pressure. But if you want to borrow this — here’s how.
**Lede:** Everything is in Bitbucket. The prompts, the templates, the agents, and a couple of fully-worked sample stories. Lift what’s useful, leave what isn’t.

**Body:** Two columns.

**Left column** (6 numbered step cards, vertically stacked). Each card: cream background, gold left accent, charcoal circle with gold number, body text:

1. Browse the repo — start with the README, then /templates and /samples
1. Pick one safe story — something boring is perfect for a first try
1. Use the story spec template — quality of spec drives everything downstream
1. Run the agent, review the plan — don’t skip the approval gate
1. Audit before merge — same review bar as any other code
1. Tell us what didn’t work — that’s how the templates improve

**Right column:**

- **Top card (dark charcoal):** “Happy to pair”

> If anyone wants to do their first Copilot-first story with someone from our team in the room, reach out. We’ll join the working session, show how we set up the prompt, and walk through the artifacts. No formal program — just pairing.
- **Bottom card (cream):** “WHERE TO FIND US”
  - **Repo:** `bitbucket / copilot-workflow`
  - **Slack:** `#ai-copilot-practice`
  - **Office hours:** bi-weekly, posted in #ai-copilot-practice

*Note: Replace repo name and Slack channel with real values before presenting.*

-----

### Slide 13 — Q&A

**Eyebrow:** `13  —  Q & A`
**(No slide title — the “Questions?” headline serves as the heading.)**

**Body:** Two columns.

**Left column:**

- Large headline: **Questions?**
- Subtitle (muted): Happy to walk through anything in Bitbucket live — the prompts, the templates, a sample story end to end. Just say the word.
- Bullet list of likely questions:
  - How does this slow you down vs speed you up?
  - What kinds of tasks should we not use this for?
  - How do you handle when the agent gets it wrong?
  - What about IP, data handling, compliance?
  - Can my team use the templates as-is?

**Right column** (dark charcoal background, full height):

- Eyebrow: `REACH OUT` (gold)
- Large headline (white): Anyone can borrow this.
- Subtitle (light gray): It’s not a program. It’s a repo and a few prompts. Take what helps, leave what doesn’t.
- Divider line
- Two contact blocks:
  - `SLACK` → `#ai-copilot-practice`
  - `REPO` → `bitbucket / copilot-workflow`

-----

## 4. SPEAKER NOTES

Every slide must include speaker notes (PowerPoint notes pane) describing what the presenter should say. Notes should be 2-4 sentences, conversational, and emphasize the show-and-tell tone (NOT process rollout).

-----

## 5. OUTPUT REQUIREMENTS

- File format: `.pptx`
- Slide count: exactly 13
- Slide dimensions: 13.3” × 7.5”
- All text must fit inside its container — no overflow, no cut-off
- Eyebrow pills must fit on a single line (size width to text length)
- Cards must not overlap the footer line
- Bullet markers should be small filled squares (▪), not unicode bullets like •
- All colors must use hex strings without `#` prefix (PowerPoint XML requirement)
- Use Calibri (heading + body) and Consolas (mono) only

-----

## 6. CRITICAL RULES SUMMARY

1. **No PI, no sprint vocabulary.** Use “iteration” and “lead reviews.”
1. **Tone is share-don’t-mandate.** “Borrow what’s useful, ignore what isn’t” — this phrasing appears across multiple slides.
1. **No actual prompt text in slides.** Slide 4 shows an abbreviated shape only. Real prompt stays in Bitbucket.
1. **Visual consistency with prior deck.** Charcoal + gold + cream cards. No new color palettes, no decorative ribbons.
1. **Slide 7 is the credibility moment.** The three doc cards must look like real artifacts a developer would recognize.
1. **Slide 11 must stay honest.** Don’t oversell — the “tricky” column is what buys trust.
