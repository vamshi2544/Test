Create or enhance the file PROJECT-CONTEXT-PROMPT.md for this repository.

Goal:
This file must become the primary project context document that AI tools can use before doing any work in this codebase. It should help an AI quickly understand the project without requiring tribal knowledge.

Important instructions:
- Do not delete existing useful content.
- Preserve prior good content and improve it.
- Expand where necessary.
- Do not create any other files unless explicitly asked.
- Write the output as production-quality markdown suitable for long-term team usage.
- The audience is engineers, tech leads, architects, and AI coding assistants.
- This repository may be a Spring Boot app, generic client jar, common jar, Kafka consumer/publisher, or RabbitMQ consumer/publisher running in enterprise PCF environments.
- Make this document generic enough to reuse across similar projects, but specific enough to be useful for this repository.

The document must include these sections:

1. Project Overview
- What this application/library does
- Business purpose
- Key responsibilities
- High-level boundaries and non-goals

2. Project Type
- Identify whether this is an API service, batch service, client library, common utility jar, Kafka consumer, Kafka publisher, RabbitMQ consumer, RabbitMQ publisher, or hybrid

3. Technology Stack
- Java version
- Spring Boot version
- Build tool
- Key frameworks/libraries
- Messaging technologies
- Database technologies
- Cache technologies
- Deployment platform
- Observability tools

4. Runtime Architecture
- Request flow or event flow
- Main layers/components
- Controllers, services, clients, repositories, mappers, configs
- Inbound and outbound integrations
- Async patterns

5. Key Functional Flows
- Main business flows supported by this project
- Step-by-step flow descriptions
- Input → processing → downstream call → persistence → response/event behavior

6. Data and Integration Dependencies
- Databases used
- Tables/entities if known
- External APIs
- Internal services
- Kafka topics / RabbitMQ exchanges / queues / routing keys
- Cache usage
- Config dependencies

7. Deployment and Environment Details
- Environment model
- PCF deployment assumptions
- Manifest/config patterns
- Secrets/config source expectations
- Health check expectations
- Rollback considerations

8. Logging and Monitoring
- Logging style/patterns
- Correlation identifiers
- Important searchable fields
- Splunk / monitoring expectations
- Error visibility expectations

9. Error Handling and Resiliency
- Standard exception behavior
- Retry expectations
- Timeout/circuit breaker patterns if applicable
- Fallback behavior
- Idempotency behavior if relevant

10. Testing Expectations
- Unit test expectations
- Integration test expectations
- Contract test expectations
- What must be validated before merge

11. Important Code Areas
- Key packages, folders, modules, and their responsibilities
- Files AI should inspect first before making changes

12. Safe Change Guidance
- What can usually be changed safely
- What areas are sensitive/high-risk
- What changes require extra caution or approval

13. Local Development Guidance
- How to run locally if known
- What configs/mocks/stubs may be needed
- Common local troubleshooting notes

14. Domain Glossary
- Business terms and abbreviations used in this repo

15. Open Questions / Unknowns
- Explicitly list gaps where repository evidence is insufficient rather than inventing facts

Instructions for content generation:
- Infer only from repository evidence.
- Do not fabricate unknown business logic.
- Where information is missing, mark it clearly as “To Be Confirmed”.
- Prefer precise, structured, maintainable writing.
- Make it useful as both human documentation and AI context.
- Include practical examples where helpful.
- Optimize for future AI discoverability and reuse.

Now generate the complete updated markdown for PROJECT-CONTEXT-PROMPT.md.
