Read all required context and standards files first before doing anything else.

Read these files first and use them as mandatory guidance:
1. .copilot/PROJECT-CONTEXT-PROMPT.md
2. .copilot/RULES.md
3. .copilot/OUTPUT-FORMAT.md
4. .copilot/BEST-PRACTICES.md
5. .copilot/DEPENDENCY-INTEGRATION-MAP.md
6. .copilot/CHANGE-CHECKLIST.md
7. .copilot/templates/SPEC-SPRING-BOOT-UPGRADE.md (if present)
8. Any existing ARCHITECTURE.md, API-CONVENTIONS.md, ERROR-HANDLING-STANDARDS.md, LOGGING-MONITORING-STANDARDS.md, TEST-STRATEGY.md, DEPLOYMENT-RUNBOOK.md if present

Then read all upgrade-relevant project files, including but not limited to:
- parent pom / custom parent xml / shared parent build file
- project pom.xml
- Maven profiles and module poms if applicable
- application.yml / application.properties and environment-specific config files
- manifest.yml and deployment config files
- security config classes
- web config classes
- exception handling classes
- actuator / health / metrics config
- integration/client configuration
- test configuration and test classes
- any migration notes or upgrade-related documentation already in the repo

Goal of this prompt:
Upgrade this project to Spring Boot 3.5.12 using the versions and dependency management already provided by the custom parent pom where applicable, then make only the necessary project-level changes required for compatibility, correctness, and maintainability.

Source:
- Existing repository code and configuration
- Custom parent pom / parent.xml as the source of approved managed versions
- Existing project pom.xml and codebase
- No terminal execution is available to you
- Do not claim to have executed Maven, tests, scans, or builds
- The developer will run commands after your changes are complete

Expectations:

1. Understand Current State
- Identify the current Spring Boot version and relevant dependency versions
- Read the custom parent pom / parent.xml carefully and determine what versions are already managed there
- Identify project-level dependencies in pom.xml that must be updated, removed, aligned, or left unchanged
- Review code and config for Spring Boot 3.x / Spring Framework 6.x compatibility concerns
- Review security-related dependencies and configurations for obvious upgrade concerns
- Identify any use of deprecated, incompatible, legacy, or suspicious patterns based on repository evidence

2. Create Upgrade Plan
- Create a minimal, low-risk upgrade plan
- Avoid unnecessary refactoring or unrelated cleanup
- Keep existing behavior intact unless a compatibility fix is required
- Explicitly separate:
  - pom.xml dependency/version changes
  - config/property changes
  - code-level compatibility changes
  - test updates if needed
  - deployment/runtime review items
  - security/vulnerability review items

3. Audit the Plan
- Review the plan for:
  - unnecessary changes
  - hidden compatibility risks
  - backward compatibility concerns
  - deployment/runtime implications
  - actuator/health/security issues
  - dependency conflicts
  - dependency vulnerability concerns visible from versions or repository evidence
- Tighten the plan so only justified changes remain

4. Present Plan for Signoff
- Create a file named upgrade-plan-spec.md
- In that file include:
  - current state summary
  - target state summary
  - exact files likely to change
  - dependency update plan
  - code/config compatibility plan
  - security review notes
  - risks
  - manual verification steps for developer
  - open questions / assumptions
- Present the plan in the output structure defined by OUTPUT-FORMAT.md
- Stop at this point and wait for developer signoff before implementing

5. Implement Approved Plan
After signoff, implement only the approved changes.
During implementation:
- Prefer parent-managed versions where applicable
- Update pom.xml carefully
- Remove obsolete or incompatible dependency declarations only when justified
- Check imports, annotations, configs, and framework usage across the codebase for compatibility with Spring Boot 3.5.12
- Review for Jakarta namespace migration issues if still present
- Review actuator, validation, security, servlet, JPA, and test-related compatibility concerns where relevant
- Avoid changing business logic unless required for upgrade compatibility
- Avoid broad refactors, renames, formatting-only edits, or unrelated cleanup

6. Post-Implementation Audit
After implementation, audit the repository changes again:
- confirm only necessary files were changed
- confirm upgrade intent was followed
- identify any remaining areas the developer should review manually
- identify any security-sensitive areas affected by the upgrade
- identify any dependency versions that should be checked with enterprise vulnerability tooling after build access is available
- review whether documentation files should also be updated:
  - PROJECT-CONTEXT-PROMPT.md
  - DEPENDENCY-INTEGRATION-MAP.md
  - DEPLOYMENT-RUNBOOK.md
  - CHANGE-CHECKLIST.md
  - any relevant spec file

7. Final Output
Present final output using OUTPUT-FORMAT.md and include:
- objective
- understanding / assumptions
- files reviewed
- files changed
- dependency changes made
- code/config changes made
- risks / edge cases
- security review notes
- manual commands the developer must run
- manual validations the developer must perform
- anything still to be confirmed

Important guardrails:
- Do not execute terminal commands
- Do not claim tests were run
- Do not claim vulnerability scans were run
- Do not invent build results
- Do not make unnecessary changes
- Do not override parent-managed versions without clear reason
- Do not remove dependencies blindly
- Do not modify unrelated business logic
- Do not mark the task complete without a clear manual verification list for the developer

Developer verification section must include suggested commands such as:
- mvn clean install
- mvn test
- mvn dependency:tree
- mvn versions:display-dependency-updates
- enterprise-approved vulnerability or SCA scan command if applicable
- smoke validation steps relevant to the application

If an issue cannot be confirmed from repository evidence, mark it clearly as:
- Assumption
- To Be Confirmed
- Requires developer execution
