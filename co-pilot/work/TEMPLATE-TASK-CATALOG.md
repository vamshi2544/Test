Create a file named TEMPLATE-TASK-CATALOG.md.

Goal:
This file must serve as the master inventory of reusable documentation, specification, and task templates that should be created and standardized for enterprise Java / Spring Boot / PCF repositories.

Important instructions:
- Organize the file so it can be directly used for backlog creation and team rollout planning.
- Group templates by category.
- For each template include:
  - file name
  - priority
  - purpose
  - when to use it
  - typical consumers
  - whether it is mandatory, recommended, or optional
- Do not generate the actual template contents in this file.
- This file is only the catalog / task list of templates to be created.
- Keep it practical and rollout-oriented.

The file must include these categories:

1. Core AI Context Files
2. Architecture and Standards Files
3. Reusable Implementation Spec Templates
4. Operational / Release / Governance Templates
5. Optional Advanced Templates

Include at minimum these template files:

Core AI Context Files:
- PROJECT-CONTEXT-PROMPT.md
- RULES.md
- OUTPUT-FORMAT.md
- BEST-PRACTICES.md
- DEPENDENCY-INTEGRATION-MAP.md
- CHANGE-CHECKLIST.md

Architecture and Standards Files:
- ARCHITECTURE.md
- API-CONVENTIONS.md
- ERROR-HANDLING-STANDARDS.md
- LOGGING-MONITORING-STANDARDS.md
- SECURITY-GUIDELINES.md
- TEST-STRATEGY.md
- DEPLOYMENT-RUNBOOK.md
- DOMAIN-GLOSSARY.md

Reusable Implementation Spec Templates:
- SPEC-NEW-ENDPOINT.md
- SPEC-EXISTING-ENDPOINT-ENHANCEMENT.md
- SPEC-API-CONTRACT-CHANGE.md
- SPEC-DB-INTEGRATION-INSERT.md
- SPEC-DB-INTEGRATION-UPDATE.md
- SPEC-DB-INTEGRATION-READ.md
- SPEC-DB-INTEGRATION-DELETE.md
- SPEC-EXTERNAL-INTEGRATION.md
- SPEC-INTERNAL-SERVICE-INTEGRATION.md
- SPEC-KAFKA-CONSUMER.md
- SPEC-KAFKA-PUBLISHER.md
- SPEC-RABBITMQ-CONSUMER.md
- SPEC-RABBITMQ-PUBLISHER.md
- SPEC-CACHE-INTEGRATION.md
- SPEC-FEATURE-FLAG.md
- SPEC-HEALTH-CHECK-UPDATE.md
- SPEC-ERROR-CODE-IMPLEMENTATION.md
- SPEC-IDEMPOTENCY-IMPLEMENTATION.md
- SPEC-LOGGING-ENHANCEMENT.md
- SPEC-SECURITY-HARDENING.md
- SPEC-PERFORMANCE-IMPROVEMENT.md
- SPEC-BUGFIX.md
- SPEC-REFACTORING.md
- SPEC-SPRING-BOOT-UPGRADE.md
- SPEC-LIBRARY-UPGRADE.md
- SPEC-PCF-DEPLOYMENT-CHANGE.md
- SPEC-ROLLBACK-PLAN.md
- SPEC-TEST-AUTOMATION.md

Operational / Release / Governance Templates:
- RELEASE-READINESS-CHECKLIST.md
- DEPLOYMENT-VALIDATION-CHECKLIST.md
- POST-DEPLOY-VALIDATION.md
- INCIDENT-IMPACT-ASSESSMENT.md
- RCA-TEMPLATE.md
- DESIGN-REVIEW-NOTES.md

Optional Advanced Templates:
- SPEC-CANARY-ROLLOUT.md
- SPEC-BLUE-GREEN-CHANGE.md
- SPEC-CONFIG-MIGRATION.md
- SPEC-DATA-BACKFILL.md
- SPEC-BATCH-JOB.md
- SPEC-SCHEDULER-JOB.md
- SPEC-WEBHOOK-INTEGRATION.md
- SPEC-AUDIT-EVENTS.md

At the end, add:
- a recommended rollout order
- a lean starter pack
- a full enterprise pack
- suggested owners for maintaining each category

Now generate the full TEMPLATE-TASK-CATALOG.md.
