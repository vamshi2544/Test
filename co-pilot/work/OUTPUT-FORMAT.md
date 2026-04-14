Create or enhance OUTPUT-FORMAT.md for this repository.

Goal:
This file must define exactly how AI should present results for coding, analysis, design, bug fixes, upgrades, and implementation work in this repository.

Important instructions:
- Preserve any useful existing content.
- Organize for clarity.
- Do not create any other files unless explicitly asked.

The document must define a standard response format with sections like:

1. Objective
- Brief restatement of the requested change

2. Understanding / Assumptions
- What was inferred from code/config
- What remains uncertain

3. Impact Analysis
- Files/components affected
- Upstream/downstream impact
- Contract/data/messaging/deployment impact

4. Proposed Approach
- Clear step-by-step implementation plan

5. Code Changes
- What changed and why
- Package/class/file level summary

6. Risks / Edge Cases
- Backward compatibility
- Null cases
- Retry/timeout/error cases
- Transaction/messaging concerns

7. Testing Plan
- Unit tests
- Integration tests
- Manual validation
- Negative scenarios

8. Deployment / Rollback Notes
- Config changes
- Infra considerations
- Rollback approach

9. Final Checklist
- Contract preserved?
- Logs preserved?
- Security preserved?
- Tests added/updated?
- Config reviewed?

10. Preferred Style Rules
- Be concrete, not vague
- Mention exact files/classes when possible
- Separate facts from assumptions
- Call out anything needing approval

The file should also include:
- A “short response mode” template for smaller changes
- A “full implementation mode” template for major changes
- A “bug analysis mode” template
- An “upgrade mode” template

Now generate the full markdown for OUTPUT-FORMAT.md.
