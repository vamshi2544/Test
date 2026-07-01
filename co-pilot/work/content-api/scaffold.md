Build a Spring Boot REST API called "Decision Content Service" with the following spec:

ENDPOINT
GET /v1/decisions/{applicationId}/content
- Query param: locale (optional, default "en-US")
- Header: Authorization Bearer token (validate via existing security filter pattern)

BEHAVIOR
1. Look up the application's decision reason code from the acquisition decision engine
   (assume a service interface DecisionEngineClient.getReasonCode(applicationId) exists — stub it).
2. Resolve a content template by (reasonCode, locale, version) from a template repository.
   Templates are stored in MySQL, cached in GemFire with a configurable TTL.
3. Inject dynamic values (e.g. reference number) into the template at response time —
   never persist dynamic values inside the template itself.
4. Return a JSON response with this exact structure:
   - applicationId, reasonCode, templateVersion, locale
   - content.title (string)
   - content.blocks (array of typed blocks: paragraph, divider, reference, note —
     each with a "type" field and type-specific fields)
   - content.links (map of linkKey -> {label, url, external})
   - content.renderedHtml (server-rendered HTML string derived from blocks + links,
     scoped CSS classes only, no inline styles, no <script>/<style>/<form> tags,
     external links get target="_blank" rel="noopener")
   - meta.generatedAt (ISO 8601 timestamp), meta.cacheTtlSeconds

REQUIREMENTS
- Layered architecture: Controller -> Service -> Repository, DTOs separate from entities.
- Use a template engine (Thymeleaf fragment or manual StringBuilder — your call, keep it
  testable) to generate renderedHtml FROM the same block data used in the JSON blocks
  array, so blocks and renderedHtml can never drift out of sync.
- Cache template lookups in GemFire; do NOT cache application-specific reference numbers.
- Handle missing reason code / missing template gracefully — return a generic fallback
  template rather than a 500.
- Add request/response logging via Splunk pattern (mask applicationId partially in logs).
- Add New Relic custom transaction naming for this endpoint.
- Include OpenAPI/Swagger annotations on the controller.
- Write unit tests for: template resolution, HTML rendering from blocks, missing-template
  fallback, and locale fallback (unsupported locale -> default en-US).
- Include a Postman-style example request/response in a README section.

Generate the package structure, controller, service, repository interfaces, DTOs, and
test skeletons. Use constructor injection, no field injection. Follow standard Java
naming conventions and package-by-feature structure.
