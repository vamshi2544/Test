Create or enhance RULES.md for this repository.

Goal:
This file must contain the non-negotiable rules that any AI assistant or engineer should follow when making changes in this repository.

Important instructions:
- Preserve existing useful content.
- Improve, expand, and organize it.
- Do not create any other files unless asked.
- Write concise but strong rules.
- These rules should apply especially to Spring Boot APIs, client jars, common jars, Kafka/RabbitMQ projects, and enterprise PCF deployments.

The document must include sections for:

1. Core Safety Rules
- Never assume behavior without checking code/config/tests
- Never remove existing logic without justification
- Never change public contracts silently
- Never hardcode secrets, environment values, or credentials
- Never bypass validation, security, or logging standards

2. Change Discipline
- Understand impact before coding
- Trace request/event flow first
- Review related configs/tests/integrations before editing
- Keep changes minimal and scoped
- Preserve backward compatibility unless explicitly approved

3. API Rules
- Do not change endpoint contracts casually
- Respect existing request/response structures
- Preserve status code semantics
- Maintain validation and error response consistency

4. Data Rules
- Do not alter DB logic blindly
- Respect transactions and rollback behavior
- Avoid unsafe updates/deletes
- Handle nullability and schema assumptions carefully

5. Messaging Rules
- Do not change topic/queue/exchange/routing behavior without impact analysis
- Preserve ordering, retry, DLQ, ack, and idempotency expectations
- Avoid duplicate publish/consume side effects

6. Observability Rules
- Preserve correlation IDs
- Do not remove essential logs, metrics, health indicators, or audit behavior
- Add logs carefully without exposing sensitive data

7. Security Rules
- Never log secrets or PII
- Respect auth/authz boundaries
- Preserve encryption/masking/token handling

8. Testing Rules
- Every non-trivial change must include validation approach
- Update/add tests when behavior changes
- Do not claim code is complete without identifying test impact

9. Deployment/Config Rules
- Respect PCF/deployment assumptions
- Do not break manifest/config compatibility casually
- Validate env/config dependency impact

10. AI Behavior Rules
- If uncertain, explicitly say so
- Ask for clarification when repository evidence is insufficient
- Prefer repository truth over assumption
- Provide impact summary before major refactors

Tone:
Clear, strict, enterprise-grade, easy to scan.

Now generate the full markdown for RULES.md.
