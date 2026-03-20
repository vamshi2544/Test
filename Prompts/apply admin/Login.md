Redesign the login Thymeleaf template (login.html) using the Editorial Light design.

FONTS (add to <head>):
  Google Fonts: Playfair Display (700,800,900,italic), DM Sans (300,400,500,600), IBM Plex Mono (300,400,500)

COLOR TOKENS — define all three themes as CSS classes on <html> tag:

  [data-theme="syf"] (DEFAULT — Synchrony brand):
    --gold: #FFC500 | --gold-deep: #c49500 | --gold-tint: rgba(255,197,0,0.10)
    --ink: #1a1a1a | --ink-light: #6b6b6b | --ink-faint: rgba(26,26,26,0.12)
    --paper: #faf7f2 | --paper-white: #ffffff | --paper-warm: #f4efe6
    --sidebar-bg: #FFC500 | --sidebar-text: rgba(0,0,0,0.45)
    --btn-bg: #1a1a1a | --btn-text: #FFC500

  [data-theme="light"]:
    --gold: #e6a800 | --gold-deep: #b8860b | --gold-tint: rgba(230,168,0,0.08)
    --ink: #111111 | --ink-light: #555555 | --ink-faint: rgba(0,0,0,0.10)
    --paper: #ffffff | --paper-white: #f8f8f8 | --paper-warm: #f0f0f0
    --sidebar-bg: #111111 | --sidebar-text: rgba(255,255,255,0.5)
    --btn-bg: #111111 | --btn-text: #e6a800

  [data-theme="dark"]:
    --gold: #FFC500 | --gold-deep: #e6b000 | --gold-tint: rgba(255,197,0,0.08)
    --ink: #f0f0f0 | --ink-light: #999999 | --ink-faint: rgba(255,255,255,0.08)
    --paper: #141414 | --paper-white: #1e1e1e | --paper-warm: #242424
    --sidebar-bg: #FFC500 | --sidebar-text: rgba(0,0,0,0.45)
    --btn-bg: #FFC500 | --btn-text: #111111

THEME TOGGLE WIDGET (floating, bottom-right corner):
  - Fixed position: bottom:24px, right:24px
  - Small pill with three clickable swatches:
      · SYF: filled #FFC500 circle
      · Light: white circle with border
      · Dark: #1a1a1a circle
  - Active swatch has a ring (box-shadow 0 0 0 2px --gold)
  - On click: set document.documentElement.setAttribute('data-theme', value)
  - Persist selection to localStorage key 'applyAdminTheme'
  - On page load: read localStorage and apply theme, default to 'syf'
  - DM Sans 9px label "Theme" above the swatches
  - Subtle pill background: --paper-white, border 1px --ink-faint, box-shadow 0 4px 12px rgba(0,0,0,0.10)

LAYOUT: Full-height flex row with three zones:
  LEFT: 52px sidebar bg=--sidebar-bg — vertical IBM Plex Mono 9px label
        "Synchrony Financial · Apply APIs", color --sidebar-text, two small dots top/bottom
  CENTER: flex-centered, max-width 440px, no card box — content sits on --paper
  RIGHT: 52px sidebar — vertical label "Apply APIs", diamond marks, 1px border-left --ink-faint

HEADER BLOCK:
  - Animated pill badge: pulsing gold dot + "Internal Platform · Authorized Access" IBM Plex Mono 9.5px
  - h1 Playfair Display 900 44px color --ink: "Apply" line 1, italic --gold-deep "Admin Tool" line 2
  - Subtitle DM Sans 13px --ink-light

DIVIDER: two --ink-faint lines with 7px --gold diamond (rotated 45deg) center

USER FIELD:
  - Keep existing th:field and th:each binding — do NOT change controller or model attributes
  - Styled select: --paper-white bg, 1.5px --ink-faint border, 10px radius, DM Sans 14px, padding 12px 40px 12px 14px
  - Custom SVG chevron right:14px (polyline 6,9 12,15 18,9)
  - Focus: border --gold, box-shadow 0 0 0 3px --gold-tint
  - Hint: "↑ Populated from team registry" IBM Plex Mono 9px muted

SSO FIELD:
  - Flex row: input (flex:1) + "Verify" button, gap 9px
  - Input: IBM Plex Mono 13px, same styling as select
  - Verify: --paper-warm bg, --ink-faint border, IBM Plex Mono 9.5px uppercase
    hover: --gold-tint bg + gold border
  - Keep existing SSO validation method call on verify click — do NOT rewrite validation logic
  - On success: add class is-valid (border #16a34a + green glow), on fail: is-invalid (border #dc2626 + red glow)
  - Animated inline message: colored dot + text, fade-in from translateY(-4px)
  - Enter key on input triggers verify

LOGIN BUTTON:
  - Full width, bg --btn-bg, color --btn-text, Playfair Display 16px 700 italic
  - Text: "Sign in to ApplyAdmin →", arrow animates translateX(4px) on hover
  - Hover: translateY(-2px), deeper shadow
  - Keep existing th:action and form submit — do NOT change Spring Security bindings

FOOTER: "© Birlasoft · Synchrony Financial" left (IBM Plex Mono 9px muted) | gold pill "v2.0.0" right

ANIMATIONS:
  - Page load: staggered fadeUp (translateY 20px → 0, opacity 0 → 1) on header, divider, form, footer
    animation-delay: 0s, 0.1s, 0.2s, 0.35s respectively
  - All transitions on inputs/buttons: 0.2s cubic-bezier(0.4,0,0.2,1)

DO NOT change: Spring Security config, controller mappings, th:action, th:object,
               validation service methods, any Java/backend code.
Only modify: login.html template, and create login.css (or inline styles in template).
