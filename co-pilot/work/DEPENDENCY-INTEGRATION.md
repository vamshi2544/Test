Create or enhance DEPENDENCY-INTEGRATION-MAP.md for this repository.

Goal:
This file must serve as a living integration inventory for the application. It should document everything this app/project connects to, depends on, exposes, consumes, or requires at runtime.

Important instructions:
- Do not delete existing useful content.
- Preserve prior verified information and improve it.
- Expand the document with any newly discovered dependencies.
- Update existing sections if code/config evidence shows they are outdated.
- Mark uncertain or partially inferred items clearly as “To Be Confirmed”.
- Do not invent dependency details without repository evidence.
- Do not create other files unless explicitly asked.
- Optimize the document for long-term maintainability, production support, onboarding, architecture review, and AI-assisted development.

The document must include and continuously maintain these sections:

1. Purpose
- Explain that this is a living dependency and integration inventory for the repository.

2. Usage Guidance
- Explain how engineers and AI assistants should use this file.
- Explain that it must be updated whenever integrations, configs, contracts, DB operations, or messaging patterns change.

3. Dependency Summary Table
- Provide a high-level summary of all known dependencies with columns such as:
  - Dependency ID
  - Dependency Name
  - Category
  - Direction
  - Purpose
  - Code Location
  - Config Location
  - Last Verified

4. Inbound Dependencies
Document all ways this application is invoked, triggered, or receives input.
Include:
- external or internal callers
- inbound REST endpoints
- Kafka topics consumed
- RabbitMQ queues consumed
- schedulers, jobs, webhooks, event triggers
For each dependency include:
- dependency ID
- name
- source system / caller
- dependency category
- protocol/mechanism
- entry point in code
- request/event contract summary
- auth requirements if any
- business purpose
- downstream actions triggered
- logs/correlation IDs
- failure impact
- config references
- last verified date

5. Outbound Dependencies
Document all downstream systems, services, and destinations this application calls or publishes to.
Include:
- internal APIs
- external APIs
- shared services
- Kafka topics published to
- RabbitMQ exchanges/routing keys published to
- notification systems
For each dependency include:
- dependency ID
- name
- target system
- category
- protocol/mechanism
- code location
- config location
- purpose
- trigger condition
- request/payload summary
- auth/security expectations
- retry/timeout/fallback behavior
- logs/monitoring clues
- failure impact
- last verified date

6. Database Dependencies
Document database usage at operation level, not only database level.
Capture:
- database/schema/table/entity names where known
- read operations
- insert operations
- update operations
- delete operations if any
- repositories/DAOs/services involved
- business flow using the operation
- lookup criteria / key fields
- fields written or updated
- transaction considerations
- idempotency or duplicate prevention behavior
- performance/index concerns
- failure impact
- last verified date

7. Messaging Dependencies
Document Kafka and RabbitMQ dependencies in detail.
For Kafka include:
- consumer/publisher role
- topic
- consumer group where applicable
- event schema/purpose
- key selection if known
- retry/DLT handling
- ordering/duplicate considerations
For RabbitMQ include:
- producer/consumer role
- exchange
- queue
- routing key
- binding purpose
- ack/nack/retry/DLQ behavior
- concurrency/ordering concerns
- downstream actions triggered

8. Internal Library / Module Dependencies
Document important internal jars, shared clients, common modules, utility libraries, starters, or platform libraries this repository relies on.
For each item include:
- artifact/module name
- purpose
- where used
- sensitivity to version changes
- risk notes

9. Runtime / Platform Dependencies
Document runtime dependencies such as:
- PCF manifest entries
- bound marketplace services
- Redis/cache
- service bindings
- env vars
- feature flags
- health checks
- autoscaling dependencies
- routes/app names
- truststores/keystores/certs
- secret/config providers
For each include:
- dependency name
- purpose
- source/config location
- required at startup or runtime
- failure impact

10. Endpoint Inventory
Create a dedicated section listing:
- endpoints exposed by this app
- endpoints consumed by this app
For each endpoint capture:
- method
- URI
- purpose
- controller/client class
- request/response model summary
- auth
- dependent DB/API/messaging operations
- logs/correlation identifiers

11. Observability / Support Clues
For each major dependency, add useful support information such as:
- logger names
- correlation fields
- Splunk search clues
- metric/health indicator references
- common failure signatures

12. Dependency Change Risk Notes
Document the major risks when changing contracts, DB operations, messaging bindings, configs, runtime services, or shared libraries.

13. Unknowns / To Be Confirmed
List anything that could not be confidently derived from repository evidence.

14. Update Procedure
Describe how this file should be maintained in future.
Include triggers such as:
- new endpoint
- new DB operation
- new downstream service
- new Kafka/Rabbit dependency
- config change
- deployment manifest change
- shared library change
- health check change

15. Last Reviewed
Add a simple section for:
- last reviewed date
- review source (code/config/test/manual verification)
- recommended next review points

Instructions for analysis:
- Inspect controllers, services, clients, repositories, configs, manifests, properties, message listeners, publishers, and tests where available.
- Prefer repository evidence over assumptions.
- If multiple dependencies are part of the same flow, connect them clearly.
- Use structured markdown tables where useful, but include detailed subsections for high-value dependencies.
- Keep the document readable and maintainable.

Now generate or update the full DEPENDENCY-INTEGRATION-MAP.md accordingly.
