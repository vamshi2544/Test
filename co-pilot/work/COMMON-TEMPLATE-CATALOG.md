# TEMPLATE-TASK-CATALOG

## Purpose
This file is the master inventory of reusable documentation and specification templates to be created for enterprise repositories. It is used for backlog planning, team standardization, and AI-assisted development rollout.

---

## 1. Core AI Context Files

### PROJECT-CONTEXT-PROMPT.md
- Priority: P1
- Classification: Mandatory
- Purpose: Living project context for engineers and AI assistants
- When to use: Read before starting any meaningful task
- Typical consumers: Engineers, leads, architects, AI tools

### RULES.md
- Priority: P1
- Classification: Mandatory
- Purpose: Non-negotiable engineering and AI safety rules
- When to use: Read before implementation or review
- Typical consumers: Engineers, reviewers, AI tools

### OUTPUT-FORMAT.md
- Priority: P1
- Classification: Mandatory
- Purpose: Standard response/output format for design, implementation, and analysis work
- When to use: Before producing task output
- Typical consumers: Engineers, reviewers, AI tools

### BEST-PRACTICES.md
- Priority: P1
- Classification: Mandatory
- Purpose: Shared engineering best practices for consistent implementation quality
- When to use: Before implementation and during review
- Typical consumers: Engineers, reviewers, AI tools

### DEPENDENCY-INTEGRATION-MAP.md
- Priority: P1
- Classification: Mandatory
- Purpose: Living inventory of all endpoints, DB operations, integrations, runtime dependencies, and messaging connections
- When to use: Read before implementation; update when dependencies change
- Typical consumers: Engineers, architects, support teams, AI tools

### CHANGE-CHECKLIST.md
- Priority: P1
- Classification: Mandatory
- Purpose: Pre-PR / pre-deploy change completeness and risk checklist
- When to use: Before marking implementation complete, before PR, before release review
- Typical consumers: Engineers, reviewers, leads

---

## 2. Architecture and Standards Files

### ARCHITECTURE.md
- Priority: P1
- Classification: Recommended
- Purpose: High-level architecture, key flows, and boundaries
- When to use: During onboarding, design, and major change analysis

### API-CONVENTIONS.md
- Priority: P1
- Classification: Recommended
- Purpose: Endpoint, status code, validation, and contract standards
- When to use: API design and contract changes

### ERROR-HANDLING-STANDARDS.md
- Priority: P1
- Classification: Recommended
- Purpose: Error translation, status code mapping, retry semantics, exception handling guidance
- When to use: API, integration, and resiliency work

### LOGGING-MONITORING-STANDARDS.md
- Priority: P1
- Classification: Recommended
- Purpose: Logging, traceability, and observability expectations
- When to use: Any behavior, supportability, or production issue-related work

### SECURITY-GUIDELINES.md
- Priority: P2
- Classification: Recommended
- Purpose: Sensitive data, token, auth, and secure coding guidance
- When to use: Security-sensitive implementation or review

### TEST-STRATEGY.md
- Priority: P1
- Classification: Recommended
- Purpose: Test expectations across unit, integration, and regression layers
- When to use: All behavior-impacting work

### DEPLOYMENT-RUNBOOK.md
- Priority: P1
- Classification: Recommended
- Purpose: Build, deploy, smoke validation, and rollback guidance
- When to use: Release and deployment work

### DOMAIN-GLOSSARY.md
- Priority: P2
- Classification: Optional
- Purpose: Business terms and domain vocabulary
- When to use: Onboarding, analysis, cross-team communication

---

## 3. Reusable Implementation Spec Templates

### SPEC-NEW-ENDPOINT.md
- Priority: P1
- Classification: Mandatory Template
- Purpose: Standard spec for creating a new endpoint
- When to use: New REST API endpoint implementation

### SPEC-EXISTING-ENDPOINT-ENHANCEMENT.md
- Priority: P1
- Classification: Recommended Template
- Purpose: Standard spec for modifying existing endpoint behavior
- When to use: Existing endpoint logic changes

### SPEC-API-CONTRACT-CHANGE.md
- Priority: P1
- Classification: Recommended Template
- Purpose: Standard spec for request/response/header/schema changes
- When to use: Backward compatibility and contract-impacting changes

### SPEC-DB-INTEGRATION-INSERT.md
- Priority: P1
- Classification: Mandatory Template
- Purpose: Standard spec for DB insert logic
- When to use: New persistence/record creation flow

### SPEC-DB-INTEGRATION-UPDATE.md
- Priority: P1
- Classification: Mandatory Template
- Purpose: Standard spec for DB update logic
- When to use: Status updates, partial/full record updates

### SPEC-DB-INTEGRATION-READ.md
- Priority: P1
- Classification: Mandatory Template
- Purpose: Standard spec for DB read/query logic
- When to use: New lookup/search/query flow

### SPEC-DB-INTEGRATION-DELETE.md
- Priority: P2
- Classification: Optional Template
- Purpose: Standard spec for delete/cleanup logic
- When to use: Delete/archive/purge flows

### SPEC-EXTERNAL-INTEGRATION.md
- Priority: P1
- Classification: Mandatory Template
- Purpose: Standard spec for new external/downstream API integration
- When to use: Adding or changing downstream systems

### SPEC-INTERNAL-SERVICE-INTEGRATION.md
- Priority: P1
- Classification: Recommended Template
- Purpose: Standard spec for new internal service-to-service integration
- When to use: Internal platform/service dependency changes

### SPEC-KAFKA-CONSUMER.md
- Priority: P1
- Classification: Mandatory Template
- Purpose: Standard spec for Kafka consumer work
- When to use: New or changed Kafka consumption flow

### SPEC-KAFKA-PUBLISHER.md
- Priority: P1
- Classification: Mandatory Template
- Purpose: Standard spec for Kafka publisher work
- When to use: New or changed Kafka publish flow

### SPEC-RABBITMQ-CONSUMER.md
- Priority: P1
- Classification: Mandatory Template
- Purpose: Standard spec for RabbitMQ consumer work
- When to use: New or changed Rabbit consumer flow

### SPEC-RABBITMQ-PUBLISHER.md
- Priority: P1
- Classification: Mandatory Template
- Purpose: Standard spec for RabbitMQ publisher work
- When to use: New or changed Rabbit publish flow

### SPEC-CACHE-INTEGRATION.md
- Priority: P2
- Classification: Optional Template
- Purpose: Standard spec for Redis/GemFire/cache dependency work
- When to use: Caching introduction or modification

### SPEC-FEATURE-FLAG.md
- Priority: P2
- Classification: Optional Template
- Purpose: Standard spec for introducing or changing feature flags
- When to use: Controlled rollout or behavior gating

### SPEC-HEALTH-CHECK-UPDATE.md
- Priority: P2
- Classification: Optional Template
- Purpose: Standard spec for actuator/health dependency changes
- When to use: Readiness/liveness/health supportability work

### SPEC-ERROR-CODE-IMPLEMENTATION.md
- Priority: P2
- Classification: Optional Template
- Purpose: Standard spec for status/error mapping changes
- When to use: Error contract or resiliency behavior work

### SPEC-IDEMPOTENCY-IMPLEMENTATION.md
- Priority: P2
- Classification: Optional Template
- Purpose: Standard spec for idempotency design and implementation
- When to use: Duplicate protection or retry-safe write flows

### SPEC-LOGGING-ENHANCEMENT.md
- Priority: P2
- Classification: Optional Template
- Purpose: Standard spec for observability/logging improvements
- When to use: Supportability and production diagnostics improvements

### SPEC-SECURITY-HARDENING.md
- Priority: P2
- Classification: Optional Template
- Purpose: Standard spec for auth/token/PII/security changes
- When to use: Security-driven implementation work

### SPEC-PERFORMANCE-IMPROVEMENT.md
- Priority: P2
- Classification: Optional Template
- Purpose: Standard spec for latency/throughput/reliability improvements
- When to use: Performance tuning and scalability work

### SPEC-BUGFIX.md
- Priority: P1
- Classification: Mandatory Template
- Purpose: Standard spec for defect analysis and fix
- When to use: Production and non-production bug fixes

### SPEC-REFACTORING.md
- Priority: P2
- Classification: Optional Template
- Purpose: Standard spec for structured code refactoring
- When to use: Low-risk or medium-risk code cleanup

### SPEC-SPRING-BOOT-UPGRADE.md
- Priority: P1
- Classification: Mandatory Template
- Purpose: Standard spec for Spring Boot version upgrades
- When to use: Framework upgrade work

### SPEC-LIBRARY-UPGRADE.md
- Priority: P1
- Classification: Recommended Template
- Purpose: Standard spec for dependency/library version upgrades
- When to use: Shared lib or package upgrade work

### SPEC-PCF-DEPLOYMENT-CHANGE.md
- Priority: P1
- Classification: Recommended Template
- Purpose: Standard spec for manifest/runtime/binding/deployment changes
- When to use: PCF deployment-impacting changes

### SPEC-ROLLBACK-PLAN.md
- Priority: P2
- Classification: Optional Template
- Purpose: Dedicated rollback planning template
- When to use: Higher-risk changes and releases

### SPEC-TEST-AUTOMATION.md
- Priority: P2
- Classification: Optional Template
- Purpose: Test automation design and coverage planning
- When to use: Expanding automated validation

---

## 4. Operational / Release / Governance Templates

### RELEASE-READINESS-CHECKLIST.md
- Priority: P2
- Classification: Optional
- Purpose: Final readiness review before release

### DEPLOYMENT-VALIDATION-CHECKLIST.md
- Priority: P2
- Classification: Optional
- Purpose: Smoke and validation checklist during deployment

### POST-DEPLOY-VALIDATION.md
- Priority: P2
- Classification: Optional
- Purpose: Post-release health and behavior verification

### INCIDENT-IMPACT-ASSESSMENT.md
- Priority: P2
- Classification: Optional
- Purpose: Structured impact assessment during incidents

### RCA-TEMPLATE.md
- Priority: P2
- Classification: Optional
- Purpose: Root cause analysis standard template

### DESIGN-REVIEW-NOTES.md
- Priority: P2
- Classification: Optional
- Purpose: Structured design review outcomes and follow-ups

---

## 5. Optional Advanced Templates

### SPEC-CANARY-ROLLOUT.md
- Priority: P3
- Classification: Optional
- Purpose: Controlled rollout / canary routing changes

### SPEC-BLUE-GREEN-CHANGE.md
- Priority: P3
- Classification: Optional
- Purpose: Blue-green release strategy planning

### SPEC-CONFIG-MIGRATION.md
- Priority: P3
- Classification: Optional
- Purpose: Config source/property migration work

### SPEC-DATA-BACKFILL.md
- Priority: P3
- Classification: Optional
- Purpose: Historical data correction/backfill work

### SPEC-BATCH-JOB.md
- Priority: P3
- Classification: Optional
- Purpose: Batch process design and implementation

### SPEC-SCHEDULER-JOB.md
- Priority: P3
- Classification: Optional
- Purpose: Scheduled job design and implementation

### SPEC-WEBHOOK-INTEGRATION.md
- Priority: P3
- Classification: Optional
- Purpose: Webhook-based inbound/outbound integration

### SPEC-AUDIT-EVENTS.md
- Priority: P3
- Classification: Optional
- Purpose: Audit event creation and traceability design

---

## Recommended Rollout Order

### Phase 1 — Mandatory Foundation
- PROJECT-CONTEXT-PROMPT.md
- RULES.md
- OUTPUT-FORMAT.md
- BEST-PRACTICES.md
- DEPENDENCY-INTEGRATION-MAP.md
- CHANGE-CHECKLIST.md

### Phase 2 — Standards
- ARCHITECTURE.md
- API-CONVENTIONS.md
- ERROR-HANDLING-STANDARDS.md
- LOGGING-MONITORING-STANDARDS.md
- TEST-STRATEGY.md
- DEPLOYMENT-RUNBOOK.md

### Phase 3 — High-Value Spec Templates
- SPEC-NEW-ENDPOINT.md
- SPEC-DB-INTEGRATION-INSERT.md
- SPEC-DB-INTEGRATION-UPDATE.md
- SPEC-DB-INTEGRATION-READ.md
- SPEC-EXTERNAL-INTEGRATION.md
- SPEC-KAFKA-CONSUMER.md
- SPEC-KAFKA-PUBLISHER.md
- SPEC-RABBITMQ-CONSUMER.md
- SPEC-RABBITMQ-PUBLISHER.md
- SPEC-BUGFIX.md
- SPEC-SPRING-BOOT-UPGRADE.md
- SPEC-PCF-DEPLOYMENT-CHANGE.md

### Phase 4 — Advanced / Governance
- everything else based on need

---

## Lean Starter Pack
Use these first:
- PROJECT-CONTEXT-PROMPT.md
- RULES.md
- OUTPUT-FORMAT.md
- BEST-PRACTICES.md
- DEPENDENCY-INTEGRATION-MAP.md
- CHANGE-CHECKLIST.md
- SPEC-NEW-ENDPOINT.md
- SPEC-DB-INTEGRATION-INSERT.md
- SPEC-KAFKA-CONSUMER.md
- SPEC-RABBITMQ-CONSUMER.md
- SPEC-BUGFIX.md

---

## Full Enterprise Pack
Use all templates in this catalog.

---

## Suggested Owners

### Engineering Leads / Architects
- PROJECT-CONTEXT-PROMPT.md
- ARCHITECTURE.md
- DEPENDENCY-INTEGRATION-MAP.md
- API-CONVENTIONS.md
- CHANGE-CHECKLIST.md

### Senior Engineers / Feature Owners
- SPEC-*.md templates
- TEST-STRATEGY.md
- LOGGING-MONITORING-STANDARDS.md

### Platform / DevOps / Release Owners
- DEPLOYMENT-RUNBOOK.md
- RELEASE-READINESS-CHECKLIST.md
- DEPLOYMENT-VALIDATION-CHECKLIST.md
- POST-DEPLOY-VALIDATION.md

### Security / Senior Reviewers
- SECURITY-GUIDELINES.md
- SPEC-SECURITY-HARDENING.md
