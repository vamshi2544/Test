# ApplyAdmin UI Redesign Prompts

> **Project:** ApplyAdmin — Synchrony Financial Internal Platform  
> **Stack:** Java Spring Boot · Thymeleaf · Redis · PCF  
> **Design System:** Editorial Light · Playfair Display + DM Sans + IBM Plex Mono  
> **Brand:** Golden Poppy `#FFC500` · Graphite `#1a1a1a`  
> **Themes:** SYF (default) · Light · Dark  
> **Rule:** Backend logic, Spring Security, controller mappings, th:* bindings — NEVER modified. UI only.

-----

## How to Use

1. Open the target template file in your editor
1. Open Copilot Chat (or Claude / Cursor)
1. Paste the corresponding prompt below
1. Apply in order: `layout` → `dashboard` → `pr-review` → `settings` → `ai`

-----

## PROMPT 1 — `layout.html` / `base.html`

> **Apply this first.** Sets up the entire token system, sidebar, topbar, and theme switcher.
> All other prompts depend on the CSS variables defined here.

```
Redesign the shared Thymeleaf layout template (layout.html or base.html — whichever wraps
all pages with sidebar + topbar) with a premium editorial design system.

═══ FONTS — add to <head> ═══
Google Fonts: Playfair Display (700,800,900,italic), DM Sans (300,400,500,600), IBM Plex Mono (300,400,500)

═══ CSS THEME TOKENS — define on <html data-theme="syf"> ═══

[data-theme="syf"] {
  --gold: #FFC500;
  --gold-deep: #c49500;
  --gold-tint: rgba(255,197,0,0.10);
  --gold-focus: rgba(255,197,0,0.18);
  --bg: #faf7f2;
  --surface: #ffffff;
  --surface-2: #f4efe6;
  --surface-3: #ede8df;
  --sidebar-bg: #1a1a1a;
  --sidebar-text: rgba(255,255,255,0.6);
  --sidebar-active: rgba(255,197,0,0.12);
  --sidebar-active-text: #FFC500;
  --topbar-bg: #ffffff;
  --topbar-border: rgba(26,26,26,0.08);
  --ink: #1a1a1a;
  --ink-mid: #3a3a3a;
  --ink-light: #6b6b6b;
  --ink-faint: rgba(26,26,26,0.10);
  --card-border: rgba(26,26,26,0.08);
  --card-shadow: 0 1px 4px rgba(0,0,0,0.06), 0 4px 16px rgba(0,0,0,0.04);
  --btn-primary-bg: #1a1a1a;
  --btn-primary-text: #FFC500;
  --btn-gold-bg: #FFC500;
  --btn-gold-text: #111;
  --input-bg: #ffffff;
  --input-border: rgba(26,26,26,0.12);
  --tag-bg: rgba(255,197,0,0.10);
  --tag-text: #c49500;
  --tag-border: rgba(255,197,0,0.25);
  --table-header: #f4efe6;
  --table-border: rgba(26,26,26,0.07);
}

[data-theme="light"] {
  --gold: #e6a800;
  --gold-deep: #b8860b;
  --gold-tint: rgba(230,168,0,0.08);
  --gold-focus: rgba(230,168,0,0.15);
  --bg: #f5f5f5;
  --surface: #ffffff;
  --surface-2: #eeeeee;
  --surface-3: #e5e5e5;
  --sidebar-bg: #f0f0f0;
  --sidebar-text: rgba(0,0,0,0.5);
  --sidebar-active: rgba(230,168,0,0.10);
  --sidebar-active-text: #b8860b;
  --topbar-bg: #ffffff;
  --topbar-border: rgba(0,0,0,0.08);
  --ink: #111111;
  --ink-mid: #333333;
  --ink-light: #666666;
  --ink-faint: rgba(0,0,0,0.09);
  --card-border: rgba(0,0,0,0.08);
  --card-shadow: 0 1px 3px rgba(0,0,0,0.05), 0 4px 12px rgba(0,0,0,0.03);
  --btn-primary-bg: #222222;
  --btn-primary-text: #e6a800;
  --btn-gold-bg: #e6a800;
  --btn-gold-text: #ffffff;
  --input-bg: #ffffff;
  --input-border: rgba(0,0,0,0.10);
  --tag-bg: rgba(230,168,0,0.08);
  --tag-text: #b8860b;
  --tag-border: rgba(230,168,0,0.2);
  --table-header: #eeeeee;
  --table-border: rgba(0,0,0,0.07);
}

[data-theme="dark"] {
  --gold: #FFC500;
  --gold-deep: #e6b000;
  --gold-tint: rgba(255,197,0,0.08);
  --gold-focus: rgba(255,197,0,0.14);
  --bg: #111111;
  --surface: #1c1c1c;
  --surface-2: #242424;
  --surface-3: #2c2c2c;
  --sidebar-bg: #0d0d0d;
  --sidebar-text: rgba(255,255,255,0.45);
  --sidebar-active: rgba(255,197,0,0.10);
  --sidebar-active-text: #FFC500;
  --topbar-bg: #161616;
  --topbar-border: rgba(255,255,255,0.06);
  --ink: #f0f0f0;
  --ink-mid: #cccccc;
  --ink-light: #888888;
  --ink-faint: rgba(255,255,255,0.07);
  --card-border: rgba(255,255,255,0.07);
  --card-shadow: 0 1px 4px rgba(0,0,0,0.3), 0 4px 20px rgba(0,0,0,0.2);
  --btn-primary-bg: #FFC500;
  --btn-primary-text: #111111;
  --btn-gold-bg: #FFC500;
  --btn-gold-text: #111111;
  --input-bg: #1c1c1c;
  --input-border: rgba(255,255,255,0.08);
  --tag-bg: rgba(255,197,0,0.08);
  --tag-text: #FFC500;
  --tag-border: rgba(255,197,0,0.18);
  --table-header: #1c1c1c;
  --table-border: rgba(255,255,255,0.06);
}

═══ TOPBAR ═══
Height 52px, background var(--topbar-bg), border-bottom 1px var(--topbar-border).
Left side:
  - 28px gold square logo: background var(--gold), border-radius 6px,
    "AA" text color #111, DM Sans 11px bold
  - "APPLY ADMIN TOOL" IBM Plex Mono 11px uppercase letter-spacing 0.12em color var(--ink)
Right side:
  - Username: DM Sans 13px 500, color var(--ink) — keep existing th:text binding
  - SSO: IBM Plex Mono 10px var(--ink-light) in parentheses — keep existing th:text binding
  - "Admin" role pill: background var(--gold), color #111, IBM Plex Mono 9px,
    padding 3px 9px, border-radius 100px — keep existing th:if / th:text role binding
  - Logout button: transparent bg, 1px var(--card-border) border,
    IBM Plex Mono 9.5px var(--ink-light) — keep existing th:action

═══ SIDEBAR ═══
Width 210px, background var(--sidebar-bg), border-right 1px var(--ink-faint), flex column.

Profile block (padding 20px 16px, border-bottom 1px rgba(255,255,255,0.05)):
  - Avatar circle: 38px, background var(--gold), color #111, DM Sans 13px 600
    Show initials — keep existing th:text binding
  - Name: DM Sans 13px 600 — keep existing th:text binding
    Color: rgba(255,255,255,0.85) when sidebar is dark (syf/dark themes),
           var(--ink) when sidebar is light (light theme)
  - Role: IBM Plex Mono 9px uppercase letter-spacing 0.12em var(--sidebar-text)
    Keep existing th:text binding

Section labels: IBM Plex Mono 8.5px uppercase letter-spacing 0.18em,
  var(--sidebar-text) opacity 0.5, padding 12px 16px 4px — keep "NAVIGATION" and "TOOLS" labels

Nav items (padding 9px 16px, DM Sans 13px, color var(--sidebar-text)):
  - border-left 2px solid transparent
  - transition all 0.15s
  - Active: background var(--sidebar-active), color var(--sidebar-active-text),
            border-left-color var(--gold), font-weight 500
  - Hover: background rgba(255,255,255,0.04), color rgba(255,255,255,0.8)
  - Light theme hover: background rgba(0,0,0,0.04), color var(--ink)
  - "New" badge: IBM Plex Mono 8px, background var(--gold), color #111,
    padding 1px 6px, border-radius 100px, margin-left auto
  Keep ALL existing th:classappend active state bindings and th:href links

Theme switcher block (margin-top auto, padding 16px,
  border-top 1px rgba(255,255,255,0.05), light theme: rgba(0,0,0,0.07)):
  Label: IBM Plex Mono 8px uppercase "THEME", var(--sidebar-text) opacity 0.6, margin-bottom 10px
  Three equal-width swatch tiles in a flex row (gap 6px):
    Each tile: flex column, align-items center, gap 4px,
               border 1px rgba(255,255,255,0.08), border-radius 6px, padding 6px 4px,
               cursor pointer, transition all 0.2s
    Active tile: border var(--gold), background var(--gold-tint)
    Hover: border-color rgba(255,197,0,0.3)
    Preview block (24x16px, border-radius 3px):
      SYF:   linear-gradient(135deg, #FFC500 50%, #1a1a1a 50%)
      Light: background #f5f5f5, border 1px #dddddd
      Dark:  background #1a1a1a
    Tile label: IBM Plex Mono 7.5px uppercase, var(--sidebar-text)
      Text: "SYF" | "LIGHT" | "DARK"
  On click: document.documentElement.setAttribute('data-theme', value),
            localStorage.setItem('applyAdminTheme', value),
            update active class on all three tiles

═══ THEME INIT SCRIPT ═══
Add this as the FIRST script in <head> (before any CSS loads, prevents flash):

<script>
  (function() {
    var t = localStorage.getItem('applyAdminTheme') || 'syf';
    document.documentElement.setAttribute('data-theme', t);
  })();
</script>

═══ SHARED UTILITY CLASSES (add to shared CSS file) ═══

/* Typography */
.page-title {
  font-family: 'Playfair Display', serif;
  font-size: 30px; font-weight: 800;
  color: var(--ink); letter-spacing: -0.5px; line-height: 1.1;
}
.page-title em { color: var(--gold-deep); font-style: italic; }
.page-subtitle {
  font-family: 'DM Sans', sans-serif;
  font-size: 13px; color: var(--ink-light);
  margin-top: 5px; line-height: 1.5;
}
.section-label {
  font-family: 'IBM Plex Mono', monospace;
  font-size: 10px; font-weight: 500;
  letter-spacing: 0.16em; text-transform: uppercase;
  color: var(--ink-light); margin-bottom: 12px;
  display: flex; align-items: center; gap: 8px;
}
.section-label::after {
  content: ''; flex: 1; height: 1px; background: var(--ink-faint);
}

/* Cards */
.card {
  background: var(--surface);
  border: 1px solid var(--card-border);
  border-radius: 12px;
  box-shadow: var(--card-shadow);
}

/* Buttons */
.btn {
  font-family: 'IBM Plex Mono', monospace;
  font-size: 10px; font-weight: 500;
  letter-spacing: 0.10em; text-transform: uppercase;
  padding: 9px 18px; border-radius: 8px;
  border: none; cursor: pointer;
  transition: all 0.2s cubic-bezier(0.4,0,0.2,1);
  white-space: nowrap;
}
.btn-gold    { background: var(--btn-gold-bg); color: var(--btn-gold-text); }
.btn-primary { background: var(--btn-primary-bg); color: var(--btn-primary-text); }
.btn-outline { background: transparent; border: 1.5px solid var(--input-border); color: var(--ink-light); }
.btn-danger  { background: transparent; border: 1.5px solid rgba(220,38,38,0.3); color: #dc2626; }
.btn-gold:hover    { filter: brightness(1.05); transform: translateY(-1px); }
.btn-primary:hover { opacity: 0.88; transform: translateY(-1px); }
.btn-outline:hover { border-color: var(--gold); color: var(--ink); }
.btn-danger:hover  { background: rgba(220,38,38,0.06); }

/* Form fields */
.field-label {
  font-family: 'IBM Plex Mono', monospace;
  font-size: 9.5px; font-weight: 500;
  letter-spacing: 0.14em; text-transform: uppercase;
  color: var(--ink-light); display: block; margin-bottom: 6px;
}
.field-input,
.field-select {
  width: 100%;
  background: var(--input-bg);
  border: 1.5px solid var(--input-border);
  border-radius: 8px; color: var(--ink);
  font-family: 'DM Sans', sans-serif;
  font-size: 13px; padding: 10px 12px;
  outline: none; appearance: none;
  transition: border-color 0.2s, box-shadow 0.2s;
}
.field-input:focus,
.field-select:focus {
  border-color: var(--gold);
  box-shadow: 0 0 0 3px var(--gold-tint);
}
.field-input::placeholder { color: var(--ink-light); opacity: 0.5; }

/* Status badges */
.badge {
  display: inline-flex; align-items: center; gap: 4px;
  padding: 2px 8px; border-radius: 100px;
  font-family: 'IBM Plex Mono', monospace;
  font-size: 10px; letter-spacing: 0.06em;
}
.badge-gold     { background: rgba(255,197,0,0.12); color: var(--gold-deep); border: 1px solid rgba(255,197,0,0.2); }
.badge-success  { background: rgba(22,163,74,0.10); color: #16a34a; border: 1px solid rgba(22,163,74,0.2); }
.badge-danger   { background: rgba(220,38,38,0.08); color: #dc2626; border: 1px solid rgba(220,38,38,0.15); }

DO NOT change: any Spring Security config, th:fragment, th:replace, th:layout,
               controller mappings, session attributes, or any Java backend code.
Only modify: the HTML layout template and the shared CSS file.
```

-----

## PROMPT 2 — `dashboard.html`

> Apply after layout.html. CSS variables already defined — do NOT redefine them.

```
Redesign dashboard.html using the shared theme tokens already defined in layout.html.
Do NOT redefine any CSS variables or touch the layout fragment.

═══ PAGE HEADING ═══
<div class="page-heading" style="margin-bottom:28px;">
  <h2 class="page-title">Welcome back, <em th:text="${username}">Vamshi</em>!</h2>
  <p class="page-subtitle">Here's what's happening with your work today.</p>
</div>
Keep existing th:text binding for ${username}.

═══ STAT CARDS ═══
4-column CSS grid, gap 14px, margin-bottom 24px.
Each stat card styling:
  background var(--surface), border 1px var(--card-border), border-radius 12px,
  padding 20px, box-shadow var(--card-shadow), position relative, overflow hidden.
Left accent strip (::before pseudo-element):
  position absolute, left 0 top 0, width 3px, height 100%,
  background var(--gold), opacity 0.5, border-radius 12px 0 0 12px.
Icon wrap: 36px square, border-radius 9px, background rgba(255,197,0,0.10),
  display flex align-items center justify-content center, font-size 16px, margin-bottom 12px.
Stat value: Playfair Display 32px 800, color var(--ink), letter-spacing -1px, line-height 1.
Stat label: IBM Plex Mono 9px uppercase letter-spacing 0.16em, var(--ink-light), margin-top 6px.
Stat delta (optional sub-text): DM Sans 11px, var(--ink-light), margin-top 8px.
Keep ALL existing th:text bindings for the stat values (prReviews, completedToday, etc.).
Four cards: PR Reviews | Completed Today | AI Topics Assigned | Avg Review Time (min)

═══ QUICK ACTIONS ═══
Section label above: <div class="section-label">Quick Actions</div>
4-column grid, gap 12px, margin-bottom 24px.
Each action card:
  background var(--surface), border 1px var(--card-border), border-radius 10px,
  padding 16px, cursor pointer, transition all 0.2s, box-shadow var(--card-shadow),
  display flex, align-items center, gap 10px.
Hover: border-color var(--gold), box-shadow 0 0 0 1px var(--gold), translateY(-1px).
Icon wrap: 32px square, border-radius 8px, background var(--gold-tint),
  display flex, align-items center, justify-content center, font-size 14px, flex-shrink 0.
Label: DM Sans 12px 500, color var(--ink).
Keep ALL existing href and onclick handlers on action cards — only restyle the elements.
Four actions: Start New PR Review | Parse 105 Request | AI Learning Tracker | Edit Profile

═══ RECENT ACTIVITY ═══
Section label: <div class="section-label">Recent Activity</div>
Outer card: class="card", padding 0, overflow hidden.
Empty state (th:if="${activities.isEmpty()}"):
  Flex column, align-items center, padding 32px, gap 8px.
  Icon: font-size 28px, opacity 0.3.
  Text: DM Sans 13px, var(--ink-light).
  Sub: IBM Plex Mono 9.5px, var(--ink-light), opacity 0.6, letter-spacing 0.06em.
Activity rows (th:each, if data exists):
  Flex row space-between, padding 12px 16px,
  border-bottom 1px var(--table-border).
  Left: DM Sans 12px var(--ink). Right: IBM Plex Mono 10px var(--ink-light).

DO NOT change: any model attributes, th:text, th:href, th:each, th:if,
               controller methods, or any Java/backend code.
Only restyle the HTML structure and CSS classes.
```

-----

## PROMPT 3 — `pr-review.html`

> Apply after layout.html. CSS variables already defined — do NOT redefine them.

```
Redesign pr-review.html (PR Review Logging page) using shared theme tokens from layout.html.
Do NOT redefine any CSS variables or touch the layout fragment.

═══ PAGE HEADING ═══
<h2 class="page-title">PR Review <em>Logging</em></h2>
<p class="page-subtitle">Track and log your code review activities.</p>

═══ FORM CARD ═══
Wrap the existing form in a card div:
  background var(--surface), border 1px var(--card-border),
  border-radius 12px, padding 24px, box-shadow var(--card-shadow), margin-bottom 24px.

Inner layout: 2-column CSS grid, gap 18px.
Field pairs (keep ALL existing th:field, th:object, name attributes unchanged):
  Row 1: User Story (text input) | PR URL (text input)
  Row 2: PR Raised By (select dropdown) | PR Reviewed By (select dropdown)
  Row 3: No. of Comments (number input) | Comments Addressed (number input)

Each field group (div wrapping label + input):
  display flex, flex-direction column, gap 6px.
Label: class="field-label" (uses shared utility class from layout).
Input/Select: class="field-input" or "field-select" (uses shared utility class from layout).
Select dropdowns: keep existing th:field and th:each for dropdown population.
  Add appearance none, custom chevron via background-image SVG or ::after pseudo.

Action bar (padding-top 20px, display flex, gap 8px, flex-wrap wrap):
  All buttons use class="btn" base + modifier:
  "Start Review"   → class="btn btn-gold"    — keep existing onclick/th:action
  "End Review"     → class="btn btn-primary" — keep existing onclick/th:action
  "Lookup"         → class="btn btn-outline" — keep existing onclick/th:action
  "Download Logs"  → class="btn btn-outline" — keep existing onclick/th:action
  "Clear Records"  → class="btn btn-danger"  — keep existing onclick/th:action
  Do NOT change any button handlers, form bindings, or JS functions.

═══ REVIEW LOG TABLE ═══
Wrapper div:
  background var(--surface), border 1px var(--card-border),
  border-radius 12px, overflow hidden, box-shadow var(--card-shadow).

Table header bar (padding 16px 20px, border-bottom 1px var(--card-border),
  display flex, align-items center, justify-content space-between):
  Left: "Review Log" IBM Plex Mono 10px uppercase letter-spacing 0.14em var(--ink).
  Right: record count IBM Plex Mono 9px var(--ink-light) — keep existing th:text count binding.

Table (width 100%, border-collapse collapse):
  thead: background var(--table-header), border-bottom 1px var(--table-border).
  th: IBM Plex Mono 8.5px uppercase letter-spacing 0.14em, var(--ink-light), padding 10px 14px, text-align left.
  td: DM Sans 12px, var(--ink), padding 11px 14px, border-bottom 1px var(--table-border).
  tr:nth-child(even) td: background var(--surface-2).
  tr:last-child td: border-bottom none.
  Keep ALL existing th:each, th:text, th:field table row bindings.

Status column cell: wrap value in badge span.
  In Progress → class="badge badge-gold"
  Done        → class="badge badge-success"
  Keep existing th:text for the status value, just add badge wrapper span around it.

Empty state row (th:if="${logs.isEmpty()}"):
  Single td colspan all, padding 32px, text-align center,
  DM Sans 13px var(--ink-light): "No logs found. Start a review to populate this table."

DO NOT change: form submit logic, Redis service calls, th:action, th:object,
               any onclick handlers, or any Java/backend code.
Only restyle HTML structure and add/change CSS classes.
```

-----

## PROMPT 4 — `settings.html`

> Apply after layout.html. CSS variables already defined — do NOT redefine them.

```
Redesign settings.html using shared theme tokens from layout.html.
Do NOT redefine any CSS variables or touch the layout fragment.

═══ PAGE HEADING ═══
<h2 class="page-title">Settings</h2>
<p class="page-subtitle">Customize your Apply Admin experience.</p>

═══ SETTINGS SECTION CARD PATTERN ═══
Each settings section uses this structure:
  Outer div: background var(--surface), border 1px var(--card-border),
    border-radius 12px, box-shadow var(--card-shadow),
    margin-bottom 20px, overflow hidden.
  Header div (padding 18px 24px, border-bottom 1px var(--card-border)):
    Title: Playfair Display 16px 700, color var(--ink).
    Subtitle: DM Sans 12px var(--ink-light), margin-top 3px.
  Body div: padding 20px 24px.

═══ SECTION 1 — APPEARANCE ═══
Title: "Appearance"
Subtitle: "Choose your preferred theme for the application."

Replace existing theme buttons with a 3-column grid (gap 12px) of visual option cards.

Each theme option card:
  border 1.5px var(--card-border), border-radius 10px, padding 14px, cursor pointer.
  Transition all 0.2s.
  Active state: border var(--gold), background var(--gold-tint), box-shadow 0 0 0 1px var(--gold).
  Hover: border-color var(--gold).

Card internal structure:
  1. Preview block (height 52px, border-radius 6px, position relative, overflow hidden):
     SYF card:   background linear-gradient(135deg, #faf7f2 60%, #1a1a1a 60%)
                 ::after — position absolute, bottom 6px right 10px,
                           width 20px height 8px, border-radius 3px, background #FFC500
     Light card: background #f5f5f5, border 1px #e0e0e0
                 ::after — same position, background #e6a800
     Dark card:  background #111111
                 ::after — same position, background #FFC500
  2. Header row (display flex, align-items center, justify-content space-between, margin-top 10px):
     Left block:
       Name: IBM Plex Mono 9.5px uppercase letter-spacing 0.12em, var(--ink).
             Values: "SYF Brand" | "Light" | "Dark"
       Desc: DM Sans 11px var(--ink-light), margin-top 2px.
             Values: "Golden Poppy on warm paper" | "Clean minimal white mode" | "Easy on the eyes"
     Checkmark: 16px circle, background var(--gold), color #111, font-size 9px,
                display none by default, display flex when card is active.

On card click: call the existing theme-change function/form POST (keep whatever
  mechanism already handles theme persistence — session, Redis, POST endpoint).
  Additionally add JS to immediately update data-theme attribute and active card class
  for instant visual feedback without waiting for page reload:
  document.documentElement.setAttribute('data-theme', selectedTheme);
  localStorage.setItem('applyAdminTheme', selectedTheme);

═══ SECTION 2 — ACCOUNT ═══
Title: "Account"
Subtitle: "Manage your account settings."

Replace existing account buttons with styled action rows:
  Each row: width 100%, padding 12px 16px, border-radius 8px,
    DM Sans 13px 500, color var(--ink),
    border 1.5px var(--card-border), background var(--surface),
    text-align left, display flex, align-items center, gap 10px,
    cursor pointer, transition all 0.2s, margin-bottom 8px.
  Hover: border-color var(--gold), background var(--gold-tint).

  "Edit Profile" row: normal styling above.
    Keep existing href/th:action.
  "Logout" row: border-color rgba(220,38,38,0.2), color #dc2626.
    Hover: background rgba(220,38,38,0.06).
    Keep existing href/th:action.

DO NOT change: theme persistence logic (server-side session/Redis),
               logout handler, edit profile handler,
               any Spring Security config, or any Java backend code.
Only restyle HTML and add visual JS for immediate theme feedback.
```

-----

## PROMPT 5 — `ai.html` (AI Tracker)

> Apply after layout.html. This page has its own topbar (no sidebar).
> CSS variables already defined — do NOT redefine them.

```
Redesign ai.html (AI Features page) using shared theme tokens from layout.html.
This page uses its own standalone topbar — it does NOT use the sidebar layout fragment.
Do NOT redefine any CSS variables.

═══ PAGE TOPBAR ═══
Height 52px, background var(--topbar-bg), border-bottom 1px var(--topbar-border),
padding 0 28px, display flex, align-items center, justify-content space-between.
Left: "APPLY ADMIN TOOL — AI FEATURES" IBM Plex Mono 11px uppercase letter-spacing 0.14em var(--ink).
Right: "Back to App" as class="btn btn-outline" (font-size 9px, padding 6px 12px)
      + Logout as class="btn-logout" (transparent, border 1px var(--card-border),
        IBM Plex Mono 9.5px var(--ink-light)).
Keep existing th:href / href links on both buttons.

═══ TAB BAR ═══
Below topbar, border-bottom 1px var(--card-border),
padding 0 28px, background var(--topbar-bg), display flex.
Each tab:
  DM Sans 13px 500, color var(--ink-light),
  padding 12px 0, margin-right 28px,
  border-bottom 2px solid transparent, margin-bottom -1px,
  cursor pointer, transition all 0.2s.
Active tab: color var(--gold-deep), border-bottom-color var(--gold).
[data-theme="dark"] active: color var(--gold).
Keep ALL existing tab-switching JS (onclick handlers, th:classappend) — only restyle.

═══ CONTENT AREA (padding 32px 28px) ═══

Hero block (display flex, justify-content space-between, align-items flex-start,
  margin-bottom 28px, flex-wrap wrap, gap 16px):

  Left — hero text:
    Eyebrow: IBM Plex Mono 9px uppercase letter-spacing 0.22em,
             color var(--gold-deep), margin-bottom 10px.
             [data-theme="dark"]: color var(--gold).
             Text: "Reference Architecture"
    h1: Playfair Display 36px 800, color var(--ink), line-height 1.1, letter-spacing -0.5px.
        Keep existing th:text if dynamic, otherwise keep static text.
        Second line em: color var(--gold-deep), font-style italic.
    Subtitle: DM Sans 13px var(--ink-light), line-height 1.6, max-width 540px, margin-top 8px.
    Divider: 1px var(--ink-faint), max-width 540px, margin-top 20px.

  Right — controls:
    Color dot row: 4x 18px squares (border-radius 3px, gap 4px):
      #FFC500 | #1a1a1a | #e6a800 | #c49500
    Download button: class="btn btn-primary" font-size 9.5px padding 7px 14px.
    Keep existing download th:href / onclick.

═══ ARCHITECTURE LAYER PATTERN ═══
Repeat this structure for each architecture layer (Orchestration, Agents, Tools/Memory, Output, Infra):

Layer wrapper: margin-bottom 16px.

Layer header row (display flex, align-items center, gap 6px):
  Vertical row label:
    writing-mode vertical-rl, text-orientation mixed, transform rotate(180deg),
    IBM Plex Mono 8px uppercase letter-spacing 0.18em,
    var(--ink-light) opacity 0.5, flex-shrink 0, width 20px.
  Horizontal rule: flex 1, height 1px, background var(--ink-faint).

Card grid below header (CSS grid, gap 10px):
  3 cards → grid-template-columns repeat(3,1fr)
  2 cards → grid-template-columns repeat(2,1fr)
  1 card  → grid-template-columns 1fr

Each arch card:
  background var(--surface), border 1px var(--card-border),
  border-radius 10px, padding 14px 16px,
  cursor pointer, transition all 0.2s, box-shadow var(--card-shadow).
  Hover: border-color var(--gold), box-shadow 0 0 0 1px var(--gold).
  Active/selected (keep existing JS selection class): border-color var(--gold).
  Card title: DM Sans 12px 600, color var(--ink), margin-bottom 6px.
  Card description: DM Sans 11px var(--ink-light), line-height 1.5.
  Tags row: display flex, flex-wrap wrap, gap 5px, margin-top 10px.
    Each tag: IBM Plex Mono 9px, background var(--tag-bg), color var(--tag-text),
              border 1px var(--tag-border), padding 2px 7px, border-radius 4px.
Keep ALL existing th:text, th:each, th:classappend card bindings.

Arrow connector between layers (between each layer wrapper):
  text-align center, IBM Plex Mono 9px, var(--ink-light) opacity 0.4,
  padding 6px 0, letter-spacing 0.12em.
  Example content: "↓ task dispatch to agents"

═══ TEAM DASHBOARD TAB ═══
Keep ALL existing markup, th:each bindings, and data display logic.
Only apply the same card, table, badge, and typography styles from the shared utility classes.
Progress bars (if any): background var(--surface-2), fill var(--gold), border-radius 100px.

DO NOT change: tab switching logic, card selection handlers, download logic,
               team dashboard data bindings, any th:* attributes,
               or any Java/backend code.
Only modify HTML structure, CSS styling, and class names for visual elements.
```

-----

## Theme Token Quick Reference

|Token               |SYF      |Light    |Dark     |
|--------------------|---------|---------|---------|
|`--gold`            |`#FFC500`|`#e6a800`|`#FFC500`|
|`--gold-deep`       |`#c49500`|`#b8860b`|`#e6b000`|
|`--bg`              |`#faf7f2`|`#f5f5f5`|`#111111`|
|`--surface`         |`#ffffff`|`#ffffff`|`#1c1c1c`|
|`--sidebar-bg`      |`#1a1a1a`|`#f0f0f0`|`#0d0d0d`|
|`--ink`             |`#1a1a1a`|`#111111`|`#f0f0f0`|
|`--btn-primary-bg`  |`#1a1a1a`|`#222222`|`#FFC500`|
|`--btn-primary-text`|`#FFC500`|`#e6a800`|`#111111`|

-----

## Apply Order

```
1. layout.html     ← MUST be first (defines all tokens + sidebar + theme switcher)
2. dashboard.html
3. pr-review.html
4. settings.html
5. ai.html
```

-----

## Rules (apply to all prompts)

- **NEVER** modify Spring Security config, filters, or auth logic
- **NEVER** change `th:action`, `th:object`, `th:field`, `th:each`, `th:text`, `th:href`, `th:if`
- **NEVER** rewrite controller methods, service classes, or Redis operations
- **NEVER** change form submit handlers or API endpoint paths
- **ONLY** modify HTML structure, CSS classes, and inline styles
- **ONLY** add JS for visual interactions (theme toggle, hover states, active classes)
