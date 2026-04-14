Read all required context and standards files first before doing anything else.

Read these files first and use them as mandatory guidance:
1. .copilot/PROJECT-CONTEXT-PROMPT.md
2. .copilot/RULES.md
3. .copilot/OUTPUT-FORMAT.md
4. .copilot/BEST-PRACTICES.md
5. .copilot/DEPENDENCY-INTEGRATION-MAP.md
6. .copilot/CHANGE-CHECKLIST.md
7. .copilot/templates/SPEC-SPRING-BOOT-UPGRADE.md (if present)
8. Any relevant supporting docs if present:
   - ARCHITECTURE.md
   - API-CONVENTIONS.md
   - ERROR-HANDLING-STANDARDS.md
   - LOGGING-MONITORING-STANDARDS.md
   - SECURITY-GUIDELINES.md
   - TEST-STRATEGY.md
   - DEPLOYMENT-RUNBOOK.md

Read all upgrade-relevant repository files, including but not limited to:
- custom parent pom / parent.xml / shared parent build file
- project pom.xml
- module pom files if applicable
- application.yml / application.properties / profile configs
- manifest.yml / deployment config files
- security config classes
- web / MVC config classes
- actuator / health / metrics config
- repository / JPA config
- integration / client config
- test config and relevant test classes
- any upgrade notes or migration docs present in the repo

Goal of this Prompt:
Upgrade this project to Spring Boot 3.5.12 using approved parent-managed dependency versions where applicable, then make only the necessary project-level dependency, code, configuration, plugin, and compatibility adjustments required for a safe and production-ready migration.

Source:
- Repository code and configuration
- Custom parent pom / parent.xml / approved dependency management
- Existing project pom.xml and module build files
- Existing repository standards and documentation

Important Assumptions:
- Terminal/build/test/security scan execution is NOT available to you
- You must NOT claim to have run Maven, tests, scans, or builds
- Developer will execute all commands manually after your work is complete
- Parent-managed versions should be respected unless strong justification exists

Expectations:

1. Understand Current State
- Identify current Spring Boot version and managed dependency versions
- Review parent pom / parent.xml before changing project pom.xml
- Determine which dependency/plugin versions are inherited vs locally overridden
- Review project structure and upgrade-sensitive areas

2. Create Upgrade Plan
- Build a minimal-risk migration plan
- Separate the plan into:
  a. Dependency / Plugin Updates
  b. Configuration / Property Changes
  c. Code Compatibility Changes
  d. Security / Framework Changes
  e. Test / Validation Changes
  f. Deployment / Runtime Review Items

3. Audit the Plan
- Review the plan for:
  - unnecessary changes
  - missed compatibility risks
  - dependency conflicts
  - backward compatibility concerns
  - deployment/runtime implications
  - security implications
  - obvious outdated/vulnerable dependency concerns visible from repository evidence

4. Present Plan for Signoff
- Create upgrade-plan-spec.md
- Include:
  - current state summary
  - target state summary
  - exact files expected to change
  - detailed migration plan
  - risks / assumptions / open questions
  - manual verification steps for developer
- Stop and wait for developer signoff before implementation

5. Implement Approved Plan
After signoff:
- Update pom.xml and module poms carefully
- Prefer parent-managed versions where applicable
- Adjust plugins if required
- Fix Spring Boot 3.x / Spring Framework 6.x incompatibilities
- Review for Jakarta namespace migration issues
- Review and fix:
  - security config compatibility
  - actuator / health property changes
  - validation / servlet / JPA compatibility
  - test dependency compatibility
- Avoid unnecessary refactors or unrelated cleanup
- Avoid business logic changes unless required for compatibility

6. Post-Implementation Audit
Audit all changes again after implementation:
- Verify only necessary files were changed
- Verify migration intent was followed
- Identify residual risks / areas needing manual validation
- Identify documentation needing updates if impacted:
  - PROJECT-CONTEXT-PROMPT.md
  - DEPENDENCY-INTEGRATION-MAP.md
  - DEPLOYMENT-RUNBOOK.md
  - CHANGE-CHECKLIST.md

7. Final Output
Present final output using OUTPUT-FORMAT.md and include:
- Objective
- Understanding / Assumptions
- Files Reviewed
- Files Changed
- Dependency / Plugin Changes
- Code / Config Changes
- Risks / Edge Cases
- Security Review Notes
- Manual Developer Verification Steps
- Required Maven / Scan Commands
- Anything Still To Be Confirmed

Developer Verification Guidance Must Include Suggested Commands:
- mvn clean install
- mvn test
- mvn dependency:tree
- mvn versions:display-dependency-updates
- enterprise-approved vulnerability/security scan command if applicable
- smoke/integration validation steps relevant to this service

Guardrails:
- Do NOT execute terminal commands
- Do NOT invent build/test/scan results
- Do NOT override parent-managed versions without justification
- Do NOT remove dependencies blindly
- Do NOT perform unrelated cleanup/refactors
- Do NOT modify business logic unless upgrade compatibility requires it
- Do NOT mark migration complete without clear manual verification steps
- Mark uncertain items clearly as:
  - Assumption
  - To Be Confirmed
  - Requires Developer Validation
