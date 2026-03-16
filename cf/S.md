Cash Flow Integration
Complete Story List — All Epics
Prequal Credit Apply + Apigee Integration
 
Version 1.0  |  March 2026
 
 
Status Legend
In Progress
New

EPIC 1 — Prequal PCF App
Handles detection of decision code 0082 from Synapps, invokes the CF Integration JAR to get the Plaid hosted link, persists details to the cash flow table, and returns the enriched response to the client.
 
#
Story Title
Status
1.1
Cashflow | Prequal PCF | Detect decision code 0082 from Synapps response and route to cash flow handling logic
In Progress
1.2
Cashflow | Prequal PCF | Integrate CF Integration JAR and invoke Plaid create hosted link API upon 0082 detection
In Progress
1.3
Cashflow | Prequal PCF | Persist cash flow details (app id, decision code, cf link, expiry) into cash flow table
In Progress
1.4
Cashflow | Prequal PCF | Return 200 response to client with decision code 0082, decision message, app id, cf link, and cf link expiry
In Progress
1.5
Cashflow | Prequal PCF | Handle polling GET endpoint correctly detecting 0082 when Synapps initially returns processing response
New
1.6
Cashflow | Prequal PCF | Handle Plaid create hosted link API failure — error classification, fallback response, and client-safe error message
New
1.7
Cashflow | Prequal PCF | Handle Plaid API timeout and retry with idempotency to avoid duplicate hosted link creation for same app id
New
1.8
Cashflow | Prequal PCF | Handle cash flow table insert failure after 0082 — transactional rollback and structured exception propagation
New
1.9
Cashflow | Prequal PCF | Validate cf link expiry before returning to client — handle edge case of null or already expired link from Plaid
New
1.10
Cashflow | Prequal PCF | Splunk structured logging and alerts for 0082 detection, Plaid API call outcome, and db persist result
New
 

EPIC 2 — Cash Flow Integration JAR
Shared JAR library consumed by Prequal PCF and Cashflow Consumer. Contains all Plaid and Synapps CF Pass2 API clients, error classification, idempotency headers, and correlation ID propagation.
 
#
Story Title
Status
2.1
Cashflow | CF Integration JAR | Implement Plaid create hosted link API client with request/response models, timeout config, and error handling
In Progress
2.2
Cashflow | CF Integration JAR | Implement Plaid token exchange API client (public token to access token) with error handling and retry
New
2.3
Cashflow | CF Integration JAR | Implement Plaid get token API client for scheduler fallback scenario
New
2.4
Cashflow | CF Integration JAR | Implement Synapps CF Pass2 API client supporting consent yes/no and access token as inputs
New
2.5
Cashflow | CF Integration JAR | Implement Plaid error code classification per Confluence spec — categorize as retryable, non-retryable, and terminal
New
2.6
Cashflow | CF Integration JAR | Implement idempotency headers and correlation ID propagation across all outbound API calls
New
2.7
Cashflow | CF Integration JAR | Implement circuit breaker pattern using Resilience4j on Plaid API calls and Synapps CF Pass2
New
2.8
Cashflow | CF Integration JAR | Unit and contract test coverage for all API clients — success, timeout, retryable error, and terminal error scenarios
New
2.9
Cashflow | CF Integration JAR | Publish JAR to internal Nexus/Artifactory and validate integration from both Prequal PCF and Cashflow Consumer
New
 

EPIC 3 — Apigee New Proxies
Two new Apigee proxies: consent proxy and webhook proxy. Both require auth, request validation, rate limiting, and standardized fault handling.
 
#
Story Title
Status
3.1
Cashflow | Apigee | Create consent proxy — route POST consent request to Consent API PCF app with auth and request validation policies
New
3.2
Cashflow | Apigee | Create webhook proxy — route Plaid webhook POST to Webhooks PCF app and validate Plaid signature or headers
New
3.3
Cashflow | Apigee | Apply rate limiting and spike arrest policy on webhook proxy to protect against Plaid retry storms
New
3.4
Cashflow | Apigee | Add standardized fault response policies on both consent and webhook proxies
New
3.5
Cashflow | Apigee | Propagate correlation ID from Apigee through to PCF apps on both new proxies
New
3.6
Cashflow | Apigee | Smoke test both consent proxy and webhook proxy end to end in lower environment
New
 

EPIC 4 — Consent API (New PCF App)
New Spring Boot PCF app. Accepts applicant consent decision (yes/no), persists to cash flow table, publishes RabbitMQ message. Must handle idempotency, expiry validation, and MQ failure gracefully.
 
#
Story Title
Status
4.1
Cashflow | Consent API | Implement POST consent endpoint accepting app id and consent boolean
New
4.2
Cashflow | Consent API | Persist consent status yes or no to cash flow table for given app id
New
4.3
Cashflow | Consent API | Publish RabbitMQ consent yes message upon successful consent persist
New
4.4
Cashflow | Consent API | Publish RabbitMQ consent no message upon successful consent persist
New
4.5
Cashflow | Consent API | Handle duplicate consent request for same app id — idempotent response and suppress duplicate MQ publish
New
4.6
Cashflow | Consent API | Validate cf link expiry before accepting consent — reject with structured error if link is expired
New
4.7
Cashflow | Consent API | Handle unknown or invalid app id in consent request — return 404 with descriptive error message
New
4.8
Cashflow | Consent API | Handle DB persist failure — do not publish MQ message, return 500 with structured error
New
4.9
Cashflow | Consent API | Handle RabbitMQ publish failure after successful DB persist — retry with backoff and alert on exhaustion
New
4.10
Cashflow | Consent API | Implement secrets management for DB credentials and RabbitMQ connection via PCF credential hub
New
4.11
Cashflow | Consent API | Splunk structured logging and alerts for consent received, persist outcome, and MQ publish success or failure
New
4.12
Cashflow | Consent API | Add New Relic instrumentation to Consent API — track endpoint response times, error rates, and throughput
New
 

EPIC 5 — Webhooks PCF App (New)
New Spring Boot PCF app. Receives Plaid webhook events, validates signature, persists session finished event and public token, publishes RabbitMQ message to trigger cashflow consumer. Must handle all Plaid error event codes.
 
#
Story Title
Status
5.1
Cashflow | Webhooks App | Implement POST webhook endpoint to receive Plaid session finished event
New
5.2
Cashflow | Webhooks App | Validate Plaid webhook signature and authenticity — reject unauthorized requests with 401
New
5.3
Cashflow | Webhooks App | Persist session finished event and public token to cash flow table
New
5.4
Cashflow | Webhooks App | Publish RabbitMQ message upon successful persist to trigger cashflow consumer processing
New
5.5
Cashflow | Webhooks App | Handle unknown or unsupported Plaid event types — log and return 200 to prevent Plaid retry storms
New
5.6
Cashflow | Webhooks App | Handle duplicate webhook for same session — idempotent persist and suppress duplicate MQ publish
New
5.7
Cashflow | Webhooks App | Implement all Plaid error event code handling per Confluence spec — classify and persist error state appropriately
New
5.8
Cashflow | Webhooks App | Handle DB persist failure — do not publish MQ, return 500, and trigger Splunk alert
New
5.9
Cashflow | Webhooks App | Handle RabbitMQ publish failure after successful DB persist — retry with backoff and DLQ fallback
New
5.10
Cashflow | Webhooks App | Implement secrets management for Plaid signing secret and RabbitMQ credentials via PCF credential hub
New
5.11
Cashflow | Webhooks App | Splunk structured logging and alerts for webhook receipt, event type, persist outcome, and MQ publish result
New
5.12
Cashflow | Webhooks App | Add New Relic instrumentation to Webhooks App — track response times, error rates, and throughput
New
 

EPIC 6 — Cashflow Consumer App
Listens to consent yes and consent no RabbitMQ messages. Orchestrates token exchange, CF Pass2 call to Synapps, and final prequal /update poll. Must handle sync and async Pass2 responses, DLQ routing, idempotency, and all error scenarios.
 
6A — Happy Path: Consent Yes
#
Story Title
Status
6.1
Cashflow | Consumer App | Listen to RabbitMQ consent yes message and initiate happy path cash flow processing
In Progress
6.2
Cashflow | Consumer App | Validate incoming MQ message against cash flow table — verify app id, session state, and allowed state transition
In Progress
6.3
Cashflow | Consumer App | Invoke Plaid token exchange API using public token retrieved from cash flow table
In Progress
6.4
Cashflow | Consumer App | Persist access token returned from token exchange to cash flow table
In Progress
6.5
Cashflow | Consumer App | Invoke Synapps CF Pass2 with access token and consent yes
In Progress
6.6
Cashflow | Consumer App | Invoke prequal-credit-apply /update endpoint with cfpoll register to trigger final decision retrieval
In Progress
 
6B — Consent No Flow
#
Story Title
Status
6.7
Cashflow | Consumer App | Listen to RabbitMQ consent no message and invoke Synapps CF Pass2 with consent no to decline application
New
6.8
Cashflow | Consumer App | Invoke prequal-credit-apply /update with cfpoll register after consent no Pass2 to retrieve final decline decision
New
 
6C — Error Handling and Resiliency
#
Story Title
Status
6.9
Cashflow | Consumer App | Handle token exchange API failure — classify error, retry with exponential backoff, route to DLQ after retry exhaustion
New
6.10
Cashflow | Consumer App | Handle Synapps CF Pass2 synchronous failure — error classification, retry logic, and DLQ routing on exhaustion
New
6.11
Cashflow | Consumer App | Handle Synapps CF Pass2 async response — implement configurable poll loop with max attempt limit and delay between retries
New
6.12
Cashflow | Consumer App | Handle prequal /update call failure — retry with idempotency gate and alert on repeated failures
New
6.13
Cashflow | Consumer App | Handle stale or invalid MQ message — validate current state in cash flow table before processing and discard if in terminal state
New
6.14
Cashflow | Consumer App | Handle partial state scenarios — app id exists but public token missing, access token missing, or pass2 already completed
New
6.15
Cashflow | Consumer App | Implement idempotency gate for duplicate MQ message for same app id using cash flow table state check
New
6.16
Cashflow | Consumer App | Enable and configure DLQ for cashflow consumer queues based on existing apply DLQ pattern — test fully for cashflow queues
New
6.17
Cashflow | Consumer App | Implement DLQ consumer for cashflow queues — structured logging, Splunk alert, and support for manual replay
New
6.18
Cashflow | Consumer App | Implement cash flow table state machine enforcement — reject out-of-order state transitions and alert on invalid state
New
6.19
Cashflow | Consumer App | Implement secrets management for Synapps, Plaid API keys, and RabbitMQ credentials via PCF credential hub
New
6.20
Cashflow | Consumer App | Splunk structured logging and alerts for all consumer steps, retry attempts, DLQ routing events, and final decision outcomes
New
6.21
Cashflow | Consumer App | Add New Relic instrumentation to Consumer App — track processing latency, MQ consumption rate, and error rates
New
 

EPIC 7 — Cashflow Scheduler App (New PCF App)
New Spring Boot PCF app responsible for detecting apps where Plaid webhook was not received within a configurable time window. Triggers Plaid get token API as a non-happy path recovery mechanism.
 
#
Story Title
Status
7.1
Cashflow | Scheduler App | Implement scheduled job to query cash flow table for apps with no webhook received after configurable timeout threshold
New
7.2
Cashflow | Scheduler App | Invoke Plaid get token API for timed-out apps as non-happy path recovery trigger
New
7.3
Cashflow | Scheduler App | Publish RabbitMQ message or trigger consumer flow upon get token success to continue processing
New
7.4
Cashflow | Scheduler App | Handle apps where Plaid get token also fails — mark app as scheduler-exhausted in cash flow table and trigger alert
New
7.5
Cashflow | Scheduler App | Implement DB-level lock or status flag to prevent concurrent scheduler runs processing same app id twice
New
7.6
Cashflow | Scheduler App | Skip apps already in terminal state during scheduler run — no-op with audit log entry
New
7.7
Cashflow | Scheduler App | Make scheduler interval, timeout threshold, and retry count fully configurable via PCF environment variables
New
7.8
Cashflow | Scheduler App | Implement secrets management for all downstream API credentials via PCF credential hub
New
7.9
Cashflow | Scheduler App | Splunk logging and alerts for each scheduler run — apps evaluated, triggered, skipped, and failed counts
New
7.10
Cashflow | Scheduler App | Add New Relic instrumentation to Scheduler App — track job execution frequency, duration, and error events
New
 

EPIC 8 — Splunk Dashboards and Alerts
Observability stories covering end-to-end cash flow journey visibility, per-component alerting thresholds, and Splunk query bank for incident investigation.
 
#
Story Title
Status
8.1
Cashflow | Splunk | Create end-to-end cash flow journey dashboard — 0082 count, consent yes/no rate, pass2 outcome, and final decision distribution
New
8.2
Cashflow | Splunk | Create per-app component dashboards for Prequal PCF, Consent API, Webhooks App, Consumer, and Scheduler
New
8.3
Cashflow | Splunk | Alert on Plaid hosted link creation failure rate exceeding defined threshold
New
8.4
Cashflow | Splunk | Alert on webhook not received within expected time window — feeds awareness to on-call team before scheduler fires
New
8.5
Cashflow | Splunk | Alert on DLQ message count exceeding threshold for cashflow consumer queues
New
8.6
Cashflow | Splunk | Alert on Synapps CF Pass2 failure spike across consumer app
New
8.7
Cashflow | Splunk | Alert on consent API or webhooks app error rate spike
New
8.8
Cashflow | Splunk | Alert on scheduler exhausted apps — apps where get token also failed and application is stuck
New
8.9
Cashflow | Splunk | Build Splunk query bank for common cashflow incident investigation — queryable by app id, event type, correlation id, and time range
New
8.10
Cashflow | Splunk | Validate correlation ID is present and consistent across all components in Splunk for a single end-to-end flow
New
 

EPIC 9 — Confluence Documentation
Architecture, design decisions, runbooks, and operational documentation to support development, QA, and production on-call teams.
 
#
Story Title
Status
9.1
Cashflow | Confluence | Document end-to-end cash flow architecture — sequence diagram, component map, and full data flow for happy path and non-happy paths
New
9.2
Cashflow | Confluence | Document cash flow table schema, state machine, allowed transitions, and terminal states
New
9.3
Cashflow | Confluence | Document Plaid error code handling matrix — full classification per Confluence spec with behavior per category
New
9.4
Cashflow | Confluence | Document RabbitMQ queue and exchange topology for cashflow — queues, exchanges, bindings, and DLQ mappings
New
9.5
Cashflow | Confluence | Document idempotency and retry strategy per component with decision rationale
New
9.6
Cashflow | Confluence | Document DLQ strategy and manual replay runbook for on-call team
New
9.7
Cashflow | Confluence | Document scheduler design, trigger conditions, get token fallback flow, and exhausted app handling
New
9.8
Cashflow | Confluence | Document Splunk dashboard guide and alert runbook — what each alert means and first response steps
New
9.9
Cashflow | Confluence | Document secrets management approach and PCF credential hub setup for all new apps
New
9.10
Cashflow | Confluence | Document CF Integration JAR — API clients covered, how to consume, versioning strategy, and upgrade process
New
 

EPIC 10 — Testing and Integration Validation
Integration, contract, and load testing stories covering all paths — happy path, consent no, scheduler fallback, DLQ replay, and high-volume scenarios.
 
#
Story Title
Status
10.1
Cashflow | Testing | Integration test — happy path end to end from 0082 detection to final approved decision
New
10.2
Cashflow | Testing | Integration test — consent no path end to end from consent no through Pass2 to final decline decision
New
10.3
Cashflow | Testing | Integration test — scheduler fallback path when Plaid webhook is not received within timeout window
New
10.4
Cashflow | Testing | Integration test — DLQ routing and manual replay validation for cashflow consumer queues
New
10.5
Cashflow | Testing | Integration test — duplicate MQ message handling and idempotency gate validation across consumer and consent API
New
10.6
Cashflow | Testing | Contract test for CF Integration JAR — validate Plaid and Synapps Pass2 clients from both Prequal PCF and Consumer
New
10.7
Cashflow | Testing | Load test — simulate high volume of concurrent 0082 decisions and measure Plaid API, MQ, and consumer throughput
New
10.8
Cashflow | Testing | Negative test — expired cf link, invalid app id, unauthorized webhook, and out-of-order state transitions
New
 

Best Practices — Recommended for This Integration
The following best practices apply across all components of this integration. Each should be treated as acceptance criteria on relevant stories or as dedicated stories as noted.
 
#
Area
Best Practice
1
Correlation ID / Trace ID
Generate a single correlationId at Apigee entry point. Propagate through every PCF app, MQ message payload, and outbound API call. Log in every Splunk event so a full end-to-end journey is traceable with one ID. This is the foundation of incident investigation.
2
Cash Flow State Machine
Define explicit allowed state transitions in the cash flow table: 0082_RECEIVED → CONSENT_YES / CONSENT_NO → TOKEN_EXCHANGED → PASS2_SENT → FINAL_DECISION. Enforce these transitions in code — reject or alert on out-of-order updates. Prevents race conditions and duplicate processing across all apps.
3
Idempotency Gates
Every consumer processing step must check table state before proceeding. Consent API rejects duplicate consent for same app id. Webhooks app suppresses duplicate session events. Token exchange and Pass2 calls skip if already completed per state. No step should ever process twice silently.
4
Retry and Backoff Strategy
All outbound calls (Plaid APIs, Synapps Pass2, prequal /update) must use configurable retry count with exponential backoff. Max retry exhaustion must route to DLQ — never silently swallow failures. Retry config should be externalized via PCF env vars.
5
DLQ Design and Replay
Enable DLQ for all cashflow queues — consent queue and webhook queue. DLQ consumer must log structured events and trigger Splunk alerts. A manual replay runbook must be documented in Confluence. Based on apply pattern but fully tested for cashflow queues.
6
CF Link Expiry Enforcement
Consent API must validate link expiry before accepting consent. Scheduler must also skip apps with expired links and mark them appropriately. This prevents ghost processing of sessions where the Plaid window has already closed.
7
Plaid Webhook Security
Validate Plaid webhook signature on every inbound request at both Apigee (header policy) and Webhooks App (Spring filter). Reject unauthorized or tampered webhooks with 401. Alert on repeated invalid webhook attempts — could indicate a misconfiguration or security probe.
8
Plaid Error Code Matrix
Classify all Plaid error codes per Confluence spec as retryable, non-retryable, or terminal. Each category must have a defined, consistent behavior implemented in the CF Integration JAR. This prevents ad-hoc error handling scattered across consuming apps.
9
Circuit Breaker
Apply Resilience4j circuit breaker on Plaid API calls and Synapps CF Pass2 calls in the CF Integration JAR. Configure open/half-open/closed thresholds appropriately for financial API tolerances. Prevents cascading failures during downstream degradation.
10
Scheduler Safety
Scheduler must use a DB-level status flag or optimistic lock to prevent two concurrent runs picking up the same app. Skip apps already in terminal state. Log every decision — triggered, skipped, exhausted. All thresholds externalized via PCF env vars.
11
Secrets Management
Plaid API keys, signing secrets, Synapps credentials, and RabbitMQ passwords must never be in code or config files. Store in PCF Credential Hub or Vault. Each new PCF app requires its own secrets setup story before go-live.
12
Contract Testing for JAR
CF Integration JAR must have contract tests for all API clients. Both consumers — Prequal PCF and Cashflow Consumer — must run these contract tests on JAR upgrade. Prevents silent breaking changes from propagating into production.
13
New Relic APM Coverage
All new PCF apps (Consent API, Webhooks App, Consumer App, Scheduler App) must have New Relic instrumentation from day one. Track response times, error rates, and throughput per endpoint. Complement Splunk for app-level vs infrastructure-level visibility.
 
