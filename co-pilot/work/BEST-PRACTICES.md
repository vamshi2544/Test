Create or enhance BEST-PRACTICES.md for this repository.

Goal:
This file must capture the recommended engineering practices for this type of project so that AI and engineers produce consistent, maintainable, production-ready work.

Important instructions:
- Preserve existing useful content.
- Expand and organize it.
- Do not create any other files unless explicitly asked.
- Assume enterprise Java/Spring Boot/PCF style projects with possible DB, Kafka, RabbitMQ, cache, and downstream integration patterns.

The document must include best practices for:

1. Code Design
- Separation of concerns
- Small focused classes/methods
- Avoiding tight coupling
- Reusable helper patterns
- Clear naming conventions

2. API Design
- Backward compatibility
- Versioning awareness
- Validation discipline
- Response consistency
- Proper HTTP semantics

3. Database Integration
- Safe insert/update/read patterns
- Transaction boundaries
- Defensive null handling
- SQL/query efficiency
- Schema awareness
- Idempotent write considerations

4. Messaging
- Consumer safety
- Publisher reliability
- Retry/DLQ awareness
- Duplicate handling
- Ordering awareness
- Correlation IDs

5. Error Handling
- Domain vs system exceptions
- Consistent error translation
- Actionable logs
- Avoid swallowing exceptions

6. Logging and Monitoring
- Structured searchable logs
- Correlation tracing
- Enough logging for production support without noise
- Business event visibility

7. Security
- No sensitive data leakage
- Token/header handling discipline
- Config/secrets management awareness

8. Testing
- Meaningful unit tests
- Integration tests for key flows
- Negative testing
- Contract regression awareness

9. Performance and Reliability
- Timeout awareness
- Retry awareness
- Avoid unnecessary DB calls
- Avoid chatty downstream calls
- Thread/resource safety

10. Deployment Readiness
- Config compatibility
- Health check considerations
- Backward-compatible rollout thinking
- Rollback readiness

11. AI Usage Guidance
- AI should first inspect related files before editing
- AI should avoid mass refactoring without explicit approval
- AI should document assumptions clearly

Now generate the full markdown for BEST-PRACTICES.md.
