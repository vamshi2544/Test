# Copilot Master Workflow Prompt

Read the following files carefully:
- context.md
- story.md (or provided requirement)
- best-practices.md
- rules.md
- output-format.md

## Step 1: Planning
- Analyze all inputs
- Generate a complete end-to-end implementation plan
- Do NOT generate code
- Clearly mention impacted components, data flow, and edge cases

## Step 2: Self-Audit Plan
- Validate the plan against:
  - requirements completeness
  - rules.md compliance
  - best practices
- Identify gaps, risks, or missing scenarios
- Refine the plan if needed

## Step 3: Approval Gate
- STOP and wait for user approval before implementation

---

## Step 4: Implementation (After Approval)
- Implement strictly based on approved plan
- Follow:
  - best-practices.md
  - rules.md
- Ensure:
  - proper logging
  - exception handling
  - no hardcoding
  - clean structure

---

## Step 5: Final Audit
- Validate implementation against:
  - original requirements
  - approved plan
  - rules.md
  - best practices

---

## Step 6: Output
- Provide final result in format defined in output-format.md
- Include:
  - summary
  - files/components impacted
  - risks or assumptions
  - validation checks
