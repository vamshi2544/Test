Create or enhance CHANGE-CHECKLIST.md for this repository.

Goal:
This file must serve as the mandatory change-control checklist for any non-trivial code, config, contract, database, messaging, integration, or deployment change in this repository.

Important instructions:
- Do not delete useful existing content.
- Preserve verified content and improve it.
- Organize the checklist so it is practical for daily engineering use.
- Keep it concise enough to be used, but complete enough to prevent risky misses.
- Do not create any other files unless explicitly asked.
- This file must be reusable and updated over time as the project evolves.

The document must explain:
1. What this checklist is for
2. When it must be used
3. Who should use it
4. What kinds of changes require full checklist review
5. What kinds of changes may use a lighter review
6. What documents must also be updated when applicable

The file must include these sections:

## 1. Purpose
Explain that this checklist is used to verify change completeness, reduce production risk, and ensure documentation stays current.

## 2. When To Use This Checklist
Include guidance such as:
- before raising PR for any non-trivial change
- before asking AI to finalize implementation
- during self-review
- during peer review
- before QA handoff where relevant
- before deployment / release readiness review
- after any meaningful design or dependency change

Also classify usage levels:
- Full checklist required
- Partial checklist acceptable
- Minimal checklist acceptable

## 3. Full Checklist Required For
Include examples such as:
- new endpoint
- existing endpoint contract change
- DB insert/update/delete/query change
- new repository/query logic
- new downstream API/client integration
- new Kafka producer/consumer
- new RabbitMQ producer/consumer
- retry/timeout/error-handling change
- security/auth change
- logging/monitoring change
- deployment/config/manifest/bound-service/runtime dependency change
- shared library or framework upgrade
- performance/resiliency change
- bug fix with production impact

## 4. Partial Checklist Acceptable For
Examples:
- low-risk internal refactor
- unit-test-only change
- comment/documentation-only updates
- non-behavioral cleanup with no config/runtime effect

## 5. Mandatory Documentation Update Checks
The checklist must explicitly ask whether the following were updated if impacted:
- PROJECT-CONTEXT-PROMPT.md
- DEPENDENCY-INTEGRATION-MAP.md
- ARCHITECTURE.md
- API-CONVENTIONS.md
- ERROR-HANDLING-STANDARDS.md
- LOGGING-MONITORING-STANDARDS.md
- DEPLOYMENT-RUNBOOK.md
- any relevant SPEC-*.md or design file

## 6. Change Review Checklist
Create a practical checkbox section covering:
- objective clearly understood
- impacted files identified
- upstream/downstream impact reviewed
- backward compatibility reviewed
- API contract impact reviewed
- DB impact reviewed
- messaging impact reviewed
- config/runtime impact reviewed
- security impact reviewed
- logging/monitoring impact reviewed
- health check impact reviewed
- rollback considered
- test coverage updated
- edge cases reviewed
- documentation updated

## 7. API Change Checks
Checklist for:
- endpoint/method/URI unchanged or intentionally changed
- request schema reviewed
- response schema reviewed
- validation reviewed
- headers reviewed
- status codes preserved
- swagger/docs impact reviewed
- consumers impacted identified

## 8. Database Change Checks
Checklist for:
- table/entity impact reviewed
- insert/update/read/delete behavior reviewed
- query/index/performance impact reviewed
- transaction impact reviewed
- null handling reviewed
- duplicate/idempotency behavior reviewed
- migration/schema dependency reviewed
- rollback/data correction considerations reviewed

## 9. Messaging Change Checks
Checklist for:
- topic/queue/exchange/routing key impact reviewed
- producer/consumer behavior reviewed
- retry/DLT/DLQ reviewed
- ack/nack semantics reviewed
- duplicate handling reviewed
- ordering/concurrency considerations reviewed
- downstream consumers/producers identified

## 10. Integration Change Checks
Checklist for:
- downstream contract reviewed
- auth/token/header needs reviewed
- timeout/retry/fallback reviewed
- error mapping reviewed
- monitoring/logging reviewed
- config/base URL updates reviewed

## 11. Runtime / Deployment Change Checks
Checklist for:
- manifest changes reviewed
- env var changes reviewed
- bound service changes reviewed
- app name/route changes reviewed
- autoscaling implications reviewed
- health check implications reviewed
- smoke validation steps identified
- rollback steps identified

## 12. Testing Checklist
Checklist for:
- unit tests added/updated
- integration tests added/updated
- manual validation identified
- negative scenarios reviewed
- regression areas identified
- production-like config concerns reviewed if applicable

## 13. Final Reviewer Sign-Off Prompts
Include a small section with questions like:
- What changed?
- What could break?
- How do we verify?
- How do we roll back?
- Which docs were updated?

## 14. Update Triggers
State that this file itself should be updated when:
- new architecture patterns are introduced
- new risk areas are discovered
- new deployment/runtime patterns are adopted
- post-incident learnings require stricter checks

Instructions for style:
- Use markdown checkboxes.
- Keep it practical and readable.
- Optimize for engineers, reviewers, leads, and AI assistants.
- Prefer direct, action-oriented wording.

Now generate the full CHANGE-CHECKLIST.md.
