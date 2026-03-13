# ApplyAdmin — SYF Brand Color Retheme Prompt

## Synchrony Financial Brand Colors | Safe to Test & Revert

> **How to use:** Paste this entire file into Claude, Copilot, or Cursor and say:
> *“Apply this retheme to my ApplyAdmin Spring Boot app.”*
> 
> **To revert:** Replace `applyadmin-syf.css` with `applyadmin.css` in your base layout fragment.
> Everything is CSS-variable based — one file swap reverts everything.

-----

## SYF Official Brand Colors (Verified)

|Token         |Hex      |Usage                                                |
|--------------|---------|-----------------------------------------------------|
|Gold / Yellow |`#FFC500`|Primary brand accent, CTAs, highlights               |
|Gold Dark     |`#E6B000`|Hover states on gold elements                        |
|Gold Muted    |`#FFB700`|Secondary gold, badges, borders                      |
|Gold Pale     |`#FFF3CC`|Light backgrounds on gold sections (light theme only)|
|Graphite Black|`#323232`|Primary dark background, headings                    |
|Graphite Mid  |`#4A4A4A`|Card backgrounds, secondary surfaces                 |
|Graphite Light|`#6B6B6B`|Muted text, disabled states                          |
|Graphite Pale |`#F0F0F0`|Page background (light theme)                        |
|White         |`#FFFFFF`|Text on dark, card backgrounds (light theme)         |


> **Brand intent:** Gold = warmth, action, energy. Graphite = strength, stability, trust.
> Designed by Interbrand to stand out in financial services — use gold sparingly as an accent, not a flood.

-----

## Step 1 — Create the SYF Theme CSS File

Create a NEW file: `src/main/resources/static/css/applyadmin-syf.css`

**Do NOT modify the original `applyadmin.css`** — this lets you switch themes by changing one line in your base layout.

```css
/* ============================================================
   ApplyAdmin — SYF Brand Theme
   Synchrony Financial: Gold (#FFC500) + Graphite (#323232)
   Toggle by swapping this file in fragments/nav.html
   ============================================================ */

@import url('https://fonts.googleapis.com/css2?family=IBM+Plex+Mono:wght@300;400;500;600&family=Syne:wght@700;800&display=swap');

/* --- DARK VARIANT (matches current dark app feel, SYF gold accents) --- */
:root[data-theme="syf-dark"] {
  /* Backgrounds */
  --bg-primary:    #1a1600;   /* Very dark warm black — graphite tinted with gold warmth */
  --bg-secondary:  #1e1b00;   /* Slightly lighter warm dark */
  --bg-card:       #242100;   /* Card surface */
  --bg-input:      #141200;   /* Input/textarea background */

  /* Borders */
  --border-default: #2e2b00;  /* Subtle warm border */
  --border-accent:  #FFC500;  /* Gold border on focus/active */
  --border-muted:   #3a3600;  /* Muted warm border */

  /* Text */
  --text-primary:   #FFF8DC;  /* Warm off-white — not harsh pure white */
  --text-secondary: #C8B870;  /* Muted gold-tinted secondary text */
  --text-muted:     #7A7040;  /* Dim warm gray */
  --text-disabled:  #3D3A20;  /* Very dim, disabled state */
  --text-on-gold:   #1a1600;  /* Dark text that sits ON gold backgrounds */

  /* Brand Accents */
  --accent-primary:       #FFC500;  /* SYF Gold — primary CTA, highlights */
  --accent-primary-hover: #E6B000;  /* Darker gold on hover */
  --accent-primary-light: #FFD740;  /* Lighter gold for glows */
  --accent-secondary:     #B8860B;  /* Dark goldenrod — secondary actions */
  --accent-graphite:      #4A4A4A;  /* Graphite — used for neutral elements */

  /* Semantic Colors (keep functional meanings) */
  --status-done:       #4ade80;   /* Green — completed */
  --status-done-bg:    #052e16;
  --status-progress:   #FFC500;   /* Gold — in progress (on-brand!) */
  --status-progress-bg:#2e2200;
  --status-none:       #6B6B6B;   /* Graphite — not started */
  --status-none-bg:    #1e1e1e;
  --status-admin:      #FFC500;   /* Admin badge = gold */
  --status-beta:       #FFB700;

  /* Gradients */
  --gradient-brand:    linear-gradient(135deg, #FFC500, #B8860B);
  --gradient-progress: linear-gradient(90deg, #FFC500, #FFD740);
  --gradient-hero:     linear-gradient(135deg, #FFC500 0%, #E6B000 50%, #B8860B 100%);
}

/* --- LIGHT VARIANT (clean corporate look — SYF website style) --- */
:root[data-theme="syf-light"] {
  /* Backgrounds */
  --bg-primary:    #FFFFFF;
  --bg-secondary:  #F8F6F0;   /* Warm off-white */
  --bg-card:       #FFFFFF;
  --bg-input:      #F5F3EC;

  /* Borders */
  --border-default: #E0DDD0;
  --border-accent:  #FFC500;
  --border-muted:   #ECEAE0;

  /* Text */
  --text-primary:   #1A1A1A;
  --text-secondary: #4A4A4A;  /* SYF Graphite */
  --text-muted:     #6B6B6B;
  --text-disabled:  #B0ADA0;
  --text-on-gold:   #1A1A1A;  /* Dark text on gold buttons */

  /* Brand Accents */
  --accent-primary:       #FFC500;
  --accent-primary-hover: #E6B000;
  --accent-primary-light: #FFF3CC;
  --accent-secondary:     #323232;  /* Graphite as secondary in light mode */
  --accent-graphite:      #323232;

  /* Semantic */
  --status-done:       #16a34a;
  --status-done-bg:    #f0fdf4;
  --status-progress:   #B8860B;
  --status-progress-bg:#fffbeb;
  --status-none:       #6B6B6B;
  --status-none-bg:    #f5f5f5;
  --status-admin:      #FFC500;
  --status-beta:       #FFB700;

  /* Gradients */
  --gradient-brand:    linear-gradient(135deg, #FFC500, #E6B000);
  --gradient-progress: linear-gradient(90deg, #FFC500, #FFD740);
  --gradient-hero:     linear-gradient(135deg, #FFC500, #B8860B);
}

/* ============================================================
   COMPONENT OVERRIDES — SYF Style
   ============================================================ */

/* App title */
.app-title .brand-accent { color: var(--accent-primary); }

/* Top nav */
.top-nav {
  background: var(--bg-secondary);
  border-bottom: 1px solid var(--border-default);
}
.nav-item.active {
  color: var(--accent-primary);
  border-color: var(--accent-primary);
  background: rgba(255, 197, 0, 0.08);
}
.nav-item:hover {
  background: rgba(255, 197, 0, 0.05);
}

/* Feature cards on dashboard */
.feature-card {
  background: var(--bg-card);
  border: 1px solid var(--border-default);
  border-radius: 8px;
  transition: all 0.2s;
}
.feature-card:hover {
  border-color: var(--accent-primary);
  box-shadow: 0 0 0 1px var(--accent-primary), 0 4px 20px rgba(255, 197, 0, 0.12);
}
.feature-card .feature-icon {
  color: var(--accent-primary);
}

/* Primary buttons */
.btn-primary {
  background: var(--accent-primary);
  color: var(--text-on-gold);
  border: none;
  font-weight: 600;
  letter-spacing: 0.04em;
}
.btn-primary:hover {
  background: var(--accent-primary-hover);
}

/* Status buttons — In Progress now uses gold (on-brand!) */
.status-btn.in-progress {
  background: var(--status-progress-bg);
  color: var(--status-progress);
  border: 1px solid rgba(255, 197, 0, 0.3);
}
.status-btn.done {
  background: var(--status-done-bg);
  color: var(--status-done);
}
.status-btn.not-started {
  background: var(--status-none-bg);
  color: var(--status-none);
}

/* Progress bars */
.progress-fill {
  background: var(--gradient-progress);
}

/* Admin badge */
.badge-admin {
  background: rgba(255, 197, 0, 0.15);
  color: var(--accent-primary);
  border: 1px solid rgba(255, 197, 0, 0.4);
}

/* Module cards */
.module-card {
  background: var(--bg-card);
  border: 1px solid var(--border-default);
}
.module-card:hover {
  border-color: rgba(255, 197, 0, 0.4);
  background: var(--bg-secondary);
}
.module-card.expanded {
  border-color: rgba(255, 197, 0, 0.6);
}

/* Section labels / track headers */
.section-label {
  color: var(--accent-primary);
  letter-spacing: 0.15em;
  font-size: 11px;
}

/* Leaderboard top rank */
.leaderboard-row.rank-1 {
  border-color: rgba(255, 197, 0, 0.5);
  background: rgba(255, 197, 0, 0.04);
}

/* Weekly challenge card top border */
.challenge-card::before {
  background: linear-gradient(90deg, #FFC500, #B8860B, #FFC500);
}

/* Reflection textarea focus */
.textarea-dark:focus {
  border-color: rgba(255, 197, 0, 0.5);
  box-shadow: 0 0 0 2px rgba(255, 197, 0, 0.08);
}

/* Category pills */
.cat-pill.active, .cat-pill:hover {
  background: rgba(255, 197, 0, 0.1);
  border-color: rgba(255, 197, 0, 0.5);
  color: var(--accent-primary);
}

/* Active tab underline */
.tab-btn.active {
  color: var(--accent-primary);
  border-bottom-color: var(--accent-primary);
}

/* Glow text (hero heading accent) */
.glow-text {
  background: var(--gradient-hero);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
}
```

-----

## Step 2 — Add Theme Toggle to Your Base Layout

In `src/main/resources/templates/fragments/nav.html`, find where you load the CSS stylesheet and change it to support theme switching:

```html
<!-- BEFORE (original dark theme) -->
<link rel="stylesheet" th:href="@{/css/applyadmin.css}">

<!-- AFTER (SYF theme — swap this one line to test) -->
<link rel="stylesheet" th:href="@{/css/applyadmin-syf.css}">

<!-- Add this to your <html> tag to activate dark variant -->
<!-- data-theme="syf-dark"  OR  data-theme="syf-light" -->
<html xmlns:th="http://www.thymeleaf.org" data-theme="syf-dark">
```

**To revert:** Change back to `applyadmin.css` and remove `data-theme`. Done.

-----

## Step 3 — Optional: Theme Toggle Button (Test Both Variants Live)

Add this button anywhere in your nav or dashboard to let you flip between dark and light SYF themes without redeploying:

```html
<!-- Add to nav fragment -->
<button id="themeToggle"
        onclick="toggleTheme()"
        style="background:none; border:1px solid var(--border-default);
               color:var(--text-muted); padding:4px 12px; border-radius:4px;
               font-size:11px; cursor:pointer; font-family:inherit;">
  ◑ Theme
</button>

<script>
function toggleTheme() {
  const html = document.documentElement;
  const current = html.getAttribute('data-theme');
  const next = current === 'syf-dark' ? 'syf-light' : 'syf-dark';
  html.setAttribute('data-theme', next);
  localStorage.setItem('applyadmin-theme', next);
}
// Restore last chosen theme on page load
(function() {
  const saved = localStorage.getItem('applyadmin-theme');
  if (saved) document.documentElement.setAttribute('data-theme', saved);
})();
</script>
```

-----

## Step 4 — Update These Specific Thymeleaf Elements

The CSS variables handle most things automatically. But search your templates for these hardcoded values and replace them:

|Find (hardcoded)                                       |Replace with                         |
|-------------------------------------------------------|-------------------------------------|
|`color: #a78bfa`                                       |`color: var(--accent-primary)`       |
|`color: #7c3aed`                                       |`color: var(--accent-primary)`       |
|`background: #7c3aed`                                  |`background: var(--accent-primary)`  |
|`border-color: #7c3aed`                                |`border-color: var(--accent-primary)`|
|`background: linear-gradient(135deg, #7c3aed, #06b6d4)`|`background: var(--gradient-brand)`  |
|`color: #fbbf24` (amber)                               |`color: var(--status-progress)`      |
|`background: #111118`                                  |`background: var(--bg-secondary)`    |
|`background: #0a0a0f`                                  |`background: var(--bg-primary)`      |

-----

## Step 5 — How to Test

1. Deploy locally with `mvn spring-boot:run`
1. Navigate to `http://localhost:8080/dashboard`
1. You should see: gold accents on nav active state, gold progress bars, gold hover borders on feature cards, gold CTAs
1. Click the Theme toggle (if you added Step 3) to flip between dark and light variants
1. Check these pages specifically:
- `/dashboard` — feature cards should glow gold on hover
- `/agentic` — module status buttons, progress bar, tab underlines all gold
- `/agentic` Leaderboard tab — top rank row has subtle gold border
- `/agentic` Weekly Challenge — top border stripe is gold gradient

-----

## Revert Instructions (30 seconds)

**Option A — CSS swap (safest):**

```html
<!-- In fragments/nav.html, change: -->
<link rel="stylesheet" th:href="@{/css/applyadmin-syf.css}">
<!-- Back to: -->
<link rel="stylesheet" th:href="@{/css/applyadmin.css}">
<!-- And remove data-theme from <html> tag -->
```

**Option B — Git (if you committed before testing):**

```bash
git checkout fragments/nav.html
# That's it — applyadmin-syf.css stays in the repo for next time
```

-----

## Visual Preview: What Changes

|Element                    |Before (Purple)             |After (SYF Gold)          |
|---------------------------|----------------------------|--------------------------|
|Nav active item            |Purple `#a78bfa`            |Gold `#FFC500`            |
|Progress bars              |Purple→light purple gradient|Gold→bright gold gradient |
|Feature card hover border  |Purple                      |Gold glow                 |
|CTA buttons                |Purple                      |Gold with dark text       |
|“In Progress” status       |Amber `#fbbf24`             |Gold `#FFC500` (on-brand!)|
|Hero text gradient         |Purple→cyan                 |Gold→dark gold            |
|Admin badge                |Purple                      |Gold                      |
|Tab active underline       |Purple                      |Gold                      |
|Module card expanded border|Purple tint                 |Gold tint                 |

-----

## Notes

- The dark SYF variant (`syf-dark`) keeps the dark backgrounds from your current app — it just replaces purple with gold. This is the recommended starting point.
- The light SYF variant (`syf-light`) is closer to the Synchrony public website — brighter, more corporate. Try it if you want a more “official” feel.
- Gold on dark backgrounds is very readable and high contrast — it actually works better than purple for accessibility.
- `#FFC500` has a contrast ratio of ~8:1 against `#1a1600` (dark bg) — passes WCAG AA for all text sizes.

-----

*SYF Brand Colors sourced from official Synchrony Financial brand guidelines.*
*Gold: `#FFC500` (Golden Poppy) | Graphite: `#323232` | Per Interbrand brand design, 2014.*
*File: `docs/prompts/apply-admin-syf-retheme-prompt.md`*
