Generate a complete OpenAPI 3.0 specification (YAML format) for the following API.
Do not generate any Java/Spring code — just the spec file.

API NAME: Decision Content Service
BASE PATH: /v1

ENDPOINT
GET /v1/decisions/{applicationId}/content

PARAMETERS
- applicationId (path, required, string) — the application identifier
- locale (query, optional, string, default "en-US") — e.g. en-US, es-US
- Authorization (header, required, string) — Bearer token

RESPONSE 200 — application/json
Top-level fields:
- applicationId (string) — echoes the path param
- reasonCode (string) — e.g. "CREDIT_FROZEN"
- templateVersion (string) — e.g. "1.2"
- locale (string) — resolved locale, e.g. "en-US"
- content (object):
    - title (string) — e.g. "Your Credit is Frozen"
    - blocks (array of objects) — polymorphic, discriminated by "type" field.
      Support these block types:
        - type: "paragraph" -> has field "text" (string, may contain
          placeholder tokens like {{link:transunion}} referencing the links map)
        - type: "divider" -> no additional fields
        - type: "reference" -> has fields "label" (string), "value" (string)
        - type: "note" -> has field "text" (string)
    - links (object, free-form map keyed by linkKey string) — each value is an
      object with:
        - label (string) e.g. "TransUnion"
        - url (string, format uri) e.g. "https://www.transunion.com/credit-freeze"
        - external (boolean)
    - renderedHtml (string) — server-rendered HTML string derived from blocks + links.
      Example value to use in the spec example:
      "<div class=\"decision-content\" data-reason-code=\"CREDIT_FROZEN\"><h2 class=\"decision-title\">Your Credit is Frozen</h2><p>To unfreeze your credit report: visit <a href=\"https://www.transunion.com/credit-freeze\" target=\"_blank\" rel=\"noopener\">TransUnion</a> and try again.</p><p>You may also need to unfreeze with <a href=\"https://www.equifax.com/personal/credit-report-services/credit-freeze/\" target=\"_blank\" rel=\"noopener\">Equifax</a> and/or <a href=\"https://www.experian.com/freeze/center.html\" target=\"_blank\" rel=\"noopener\">Experian</a>.</p><hr class=\"decision-divider\" /><p class=\"decision-reference\"><strong>Your request reference number is:</strong> R-482910</p><p class=\"decision-note\">You will receive a written explanation within 10 days.</p></div>"
- meta (object):
    - generatedAt (string, format date-time) — ISO 8601
    - cacheTtlSeconds (integer)

Provide a full worked EXAMPLE response under this schema using reasonCode
"CREDIT_FROZEN", applicationId "APP-93821-XYZ", templateVersion "1.2",
locale "en-US", title "Your Credit is Frozen", with:
  - a paragraph block: "To unfreeze your credit report: visit {{link:transunion}} and try again."
  - a paragraph block: "You may also need to unfreeze with {{link:equifax}} and/or {{link:experian}}."
  - a divider block
  - a reference block: label "Your request reference number is", value "R-482910"
  - a note block: "You will receive a written explanation within 10 days."
  - links map containing transunion, equifax, experian entries as described above
  - meta.generatedAt "2026-07-01T14:32:00Z", meta.cacheTtlSeconds 3600

ERROR RESPONSES
- 400 Bad Request — invalid/unsupported locale format
- 401 Unauthorized — missing/invalid bearer token
- 404 Not Found — applicationId does not exist
- 500 Internal Server Error — unexpected failure
  All error responses share a common schema: { "errorCode": string,
  "message": string, "timestamp": string (date-time), "traceId": string }

REQUIREMENTS FOR THE SPEC FILE
- OpenAPI version 3.0.3
- Define reusable component schemas: Block (oneOf: ParagraphBlock, DividerBlock,
  ReferenceBlock, NoteBlock, with discriminator on "type"), LinkObject, ContentObject,
  MetaObject, DecisionContentResponse, ErrorResponse
- Use $ref throughout rather than inlining repeated schemas
- Add a securitySchemes entry for Bearer auth (type: http, scheme: bearer) and apply
  it to the endpoint
- Add meaningful "description" fields on every schema property (1 sentence each)
- Tag the endpoint under "Decision Content"
- Include the full worked example both in the response schema's "example" field
  and as a named example under examples: CreditFrozenExample

Output only the YAML spec, nothing else.
