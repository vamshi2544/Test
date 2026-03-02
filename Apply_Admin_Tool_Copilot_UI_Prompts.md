
# Apply Admin Tool – Copilot UI Redesign Prompts

This document contains structured prompts to use with GitHub Copilot
to redesign the Apply Admin Tool UI into a modern enterprise-style portal
similar to the Wiremock Service Virtualization Portal.

---

## MASTER PROMPT – Full Enterprise UI Redesign

```
I want you to redesign the Apply Admin Tool UI to look like a modern enterprise portal similar to the “Service Virtualization Portal” (Wiremock portal).

Tech context:
- This app is running at localhost:8080 (Spring Boot web app).
- Keep the existing functionality: a feature selector (105 mapping), a large text area input, and a “Parse & Map” action.
- I want ONLY UI/UX + styling improvements, but do not break existing endpoints or server-side logic.

Design requirements:

1) Layout:
- Sticky top header with branding: “synchrony” style + title “APPLY ADMIN TOOL”.
- Left vertical sidebar with icon-only nav buttons (Home, Tools, Settings).
- Clean main content area with centered container.

2) Visual Style:
- CSS Variables:
    --primary-teal: #0f9aa8
    --dark-slate: #2f3a40
    --light-bg: #f6f8fa
    --card-bg: #ffffff
    --accent-gold: #f4b400
- Modern font stack (system-ui / Inter fallback).
- Rounded corners, spacing, subtle shadows, consistent form styling.

3) Page Content:
- Hero title: “Welcome to Apply Admin Tool”
- Subtitle: “Your one-stop solution for SynApps request mapping and more.”
- Features card:
    * Dropdown: “Select Feature” → 105 (extensible later)
    * Feature badge below dropdown
- 105 Mapping card:
    * Label: “Paste SynApps 105 Request String”
    * Large textarea
    * Teal “Parse & Map” button
- Output card placeholder: “Parsed output will appear here.”

Implementation:
- Use Bootstrap 5 CDN OR clean custom CSS.
- Modify /static/css/app.css and main HTML template.
- Ensure responsive layout.
- Do NOT rename existing form fields, ids, controller mappings, or endpoints.
- Only modify UI structure and styling.

Deliver updated HTML, CSS, and minimal JS if needed.
```

---

## ENHANCEMENT PROMPT – If UI Still Looks Plain

```
The UI is still too plain. Improve it to look like a standard enterprise portal.

Enhancements:
- Sticky top header with teal accent bar.
- Dark slate left sidebar with hover states.
- White content cards with consistent spacing.
- Hero title: 36–40px.
- Section titles: 18–20px.
- Modern buttons with padding, border-radius 10px, hover/active states.
- Styled inputs and textarea with focus glow.

Refactor CSS using variables and clean naming.
Keep backend logic unchanged.
```

---

## FEATURE SELECTION PROMPT – Dynamic & Extensible Feature Logic

```
Enhance the “Select Feature” dropdown behavior.

Requirements:
- Option: “105 - Request Mapping”.
- When selection changes:
    * Update feature badge dynamically.
    * Update mapping card title dynamically.
- Structure feature definitions using a JavaScript config array for future extensibility.
- Keep existing form submission behavior intact.
- Do not change backend endpoint mappings.

Implement minimal, clean JavaScript.
```

---

## UI POLISH PROMPT – Enterprise Finishing Touches

```
Refine the UI to enterprise-grade polish:

- Improve spacing consistency.
- Add subtle shadows to cards.
- Ensure consistent border radius across cards, inputs, and buttons.
- Primary button: solid teal.
- Secondary buttons (if any): outlined style.
- Improve Output card empty state with icon + muted message.
- Ensure fully responsive behavior on smaller screens.

Do not change backend logic. Improve styling only.
```

---

## SAFETY LINE – Protect Backend Logic

If Copilot modifies backend logic unexpectedly, append this to your prompt:

```
Strict rule: Do NOT rename, remove, or modify existing form field names, ids, controller mappings, or endpoints. Only modify HTML structure and CSS styling.
```
