# Cash Flow Integration — Missing Stories & Production Readiness

> **Purpose:** Stories missing from existing Jira tracker + production readiness checklist
> **Updated:** March 2026
> **Note:** All stories below are net new — not in current tracker

-----

## Missing Stories

-----

### Prequal PCF App

**M1.** Cashflow | Prequal PCF App | Validate cf link expiry before returning to client — handle null or already expired link returned from Plaid

**M2.** Cashflow | Prequal PCF App | Handle Plaid create hosted link API timeout and retry with idempotency to avoid duplicate hosted link creation for same app id

**M3.** Cashflow | Prequal PCF App | Handle cash flow table insert failure after 0082 detection — transactional rollback and structured exception propagation to client

-----

### CF Integration JAR

**M4.** Cashflow | CF Integration JAR | Implement Plaid error code classification per Confluence spec — categorize all error codes as retryable, non-retryable, and terminal

**M5.** Cashflow | CF Integration JAR | Implement idempotency headers and correlation ID propagation across all outbound Plaid and Synapps API calls

**M6.** Cashflow | CF Integration JAR | Implement circuit breaker pattern using Resilience4j on Plaid API calls and Synapps CF Pass2 client

**M7.** Cashflow | CF Integration JAR | Unit and contract test coverage for all API clients — success, timeout, retryable error, and terminal error scenarios

**M8.** Cashflow | CF Integration JAR | Publish JAR to internal Nexus/Artifactory and validate integration from both Prequal PCF App and Cashflow Consumer PCF App

-----

### Consent PCF App

**M9.** Cashflow | Consent PCF App | Generate RabbitMQ message when consent is true — happy path MQ publish after successful DB persist

**M10.** Cashflow | Consent PCF App | Validate cf link expiry before accepting consent — reject request with structured error if link is already expired

**M11.** Cashflow | Consent PCF App | Handle unknown or invalid app id in consent request — return 404 with descriptive error message

**M12.** Cashflow | Consent PCF App | Handle RabbitMQ publish failure after successful DB persist — retry with exponential backoff and trigger Splunk alert on exhaustion

**M13.** Cashflow | Consent PCF App | Implement secrets management for DB credentials and RabbitMQ connection via PCF credential hub

**M14.** Cashflow | Consent PCF App | Add New Relic instrumentation — track endpoint response times, error rates, and throughput

-----

### Webhooks PCF App

**M15.** Cashflow | Webhooks PCF App | Validate Plaid webhook signature and request authenticity — reject unauthorized or tampered requests with 401

**M16.** Cashflow | Webhooks PCF App | Handle unknown or unsupported Plaid event types — log event type and return 200 to prevent Plaid retry storms

**M17.** Cashflow | Webhooks PCF App | Implement all Plaid error event code handling per Confluence spec — classify and persist error state to cash flow table

**M18.** Cashflow | Webhooks PCF App | Implement secrets management for Plaid signing secret and RabbitMQ credentials via PCF credential hub

**M19.** Cashflow | Webhooks PCF App | Add New Relic instrumentation — track webhook endpoint response times, error rates, and throughput

-----

### Cashflow Consumer PCF App

**M20.** Cashflow | Cashflow Consumer PCF App | Handle partial state scenarios — app id exists in cash flow table but public token missing, access token missing, or Pass2 already completed

**M21.** Cashflow | Cashflow Consumer PCF App | Implement cash flow table state machine enforcement — reject out-of-order state transitions and trigger Splunk alert on invalid state detected

**M22.** Cashflow | Cashflow Consumer PCF App | Implement secrets management for Synapps credentials, Plaid API keys, and RabbitMQ credentials via PCF credential hub

**M23.** Cashflow | Cashflow Consumer PCF App | Add New Relic instrumentation — track MQ consumption rate, processing latency per step, and error rates

-----

### Cashflow Scheduler PCF App

**M24.** Cashflow | Cashflow Scheduler PCF App | Implement DB-level lock or status flag to prevent concurrent scheduler runs from processing the same app id twice

**M25.** Cashflow | Cashflow Scheduler PCF App | Skip apps already in terminal state during scheduler run — no-op with audit log entry per skipped app id

**M26.** Cashflow | Cashflow Scheduler PCF App | Externalize all scheduler config — interval, timeout threshold, and retry count via PCF environment variables

**M27.** Cashflow | Cashflow Scheduler PCF App | Implement secrets management for all downstream API credentials via PCF credential hub

**M28.** Cashflow | Cashflow Scheduler PCF App | Add New Relic instrumentation — track scheduler job execution frequency, duration, and error events

-----

### Apply Consent Proxy

**M29.** Cashflow | Apply Consent Proxy | Implement rate limiting and spike arrest policy to protect Consent PCF App from traffic spikes

**M30.** Cashflow | Apply Consent Proxy | Propagate correlation ID header from proxy through to Consent PCF App on all requests

**M31.** Cashflow | Apply Consent Proxy | Add standardized fault response policy — consistent error structure for all proxy-level failures

-----

### Apply Webhooks Proxy

**M32.** Cashflow | Apply Webhooks Proxy | Implement rate limiting and spike arrest policy to protect Webhooks PCF App from Plaid retry storms

**M33.** Cashflow | Apply Webhooks Proxy | Propagate correlation ID header from proxy through to Webhooks PCF App on all requests

**M34.** Cashflow | Apply Webhooks Proxy | Add standardized fault response policy — consistent error structure for all proxy-level failures

-----

### Splunk Dashboards and Alerts

**M35.** Cashflow | Splunk | Create end-to-end cash flow journey dashboard — 0082 count, consent yes/no rate, Pass2 outcome, and final decision distribution

**M36.** Cashflow | Splunk | Create per-app component dashboards for Prequal PCF App, Consent PCF App, Webhooks PCF App, Cashflow Consumer PCF App, and Cashflow Scheduler PCF App

**M37.** Cashflow | Splunk | Alert on Plaid hosted link creation failure rate exceeding defined threshold

**M38.** Cashflow | Splunk | Alert on webhook not received within expected time window — notify on-call team before scheduler fires

**M39.** Cashflow | Splunk | Alert on DLQ message count exceeding threshold for Cashflow Consumer PCF App queues

**M40.** Cashflow | Splunk | Alert on Synapps CF Pass2 failure spike in Cashflow Consumer PCF App

**M41.** Cashflow | Splunk | Alert on Consent PCF App or Webhooks PCF App error rate spike

**M42.** Cashflow | Splunk | Alert on Cashflow Scheduler PCF App exhausted apps — apps where Plaid get token also failed and application is stuck

**M43.** Cashflow | Splunk | Build Splunk query bank for common cash flow incident investigation — searchable by app id, event type, correlation id, and time range

**M44.** Cashflow | Splunk | Validate correlation ID is present and consistent across all components in Splunk for a single end-to-end cash flow journey

-----

### Confluence Documentation

**M45.** Cashflow | Confluence | Document end-to-end cash flow architecture — sequence diagram, component map, and full data flow for happy path and all non-happy paths

**M46.** Cashflow | Confluence | Document cash flow table schema, state machine, allowed transitions, and terminal states

**M47.** Cashflow | Confluence | Document Plaid error code handling matrix — full classification per Confluence spec with defined behavior per category

**M48.** Cashflow | Confluence | Document RabbitMQ queue and exchange topology — queues, exchanges, bindings, and DLQ mappings for all cash flow queues

**M49.** Cashflow | Confluence | Document idempotency and retry strategy per component with decision rationale

**M50.** Cashflow | Confluence | Document DLQ strategy and manual replay runbook for on-call team

**M51.** Cashflow | Confluence | Document Cashflow Scheduler PCF App design — trigger conditions, Plaid get token fallback flow, and exhausted app handling

**M52.** Cashflow | Confluence | Document Splunk dashboard guide and alert runbook — what each alert means and first response steps for on-call team

**M53.** Cashflow | Confluence | Document secrets management approach and PCF credential hub setup for all new cash flow apps

**M54.** Cashflow | Confluence | Document CF Integration JAR — API clients covered, how to consume, versioning strategy, and upgrade process

-----

### Integration and Testing

**M55.** Cashflow | Testing | Integration test — happy path end to end from 0082 detection through Plaid hosted link, consent yes, token exchange, CF Pass2, to final approved decision

**M56.** Cashflow | Testing | Integration test — consent no path end to end from consent no through Synapps CF Pass2 to final decline decision

**M57.** Cashflow | Testing | Integration test — Cashflow Scheduler PCF App fallback path when Plaid webhook is not received within configured timeout window

**M58.** Cashflow | Testing | Integration test — DLQ routing and manual replay validation for Cashflow Consumer PCF App queues

**M59.** Cashflow | Testing | Integration test — duplicate MQ message handling and idempotency gate validation across Cashflow Consumer PCF App and Consent PCF App

**M60.** Cashflow | Testing | Contract test for CF Integration JAR — validate all Plaid and Synapps CF Pass2 clients from both Prequal PCF App and Cashflow Consumer PCF App

**M61.** Cashflow | Testing | Load test — simulate high volume of concurrent 0082 decisions and measure Plaid API, RabbitMQ, and Cashflow Consumer PCF App throughput

**M62.** Cashflow | Testing | Negative test — expired cf link, invalid app id, unauthorized webhook request, and out-of-order state transitions

-----

## Production Readiness Checklist

-----

### 1. PCF App Readiness — All New Apps

- [ ] All new apps — Consent PCF App, Webhooks PCF App, Cashflow Consumer PCF App, Cashflow Scheduler PCF App — have correct manifest.yml with memory, instances, and env vars
- [ ] Spring Boot Actuator /health endpoints verified and accessible for each new app
- [ ] All secrets bound via PCF credential hub — no hardcoded credentials in any config file or manifest
- [ ] Instance scaling rules configured for Consent PCF App and Webhooks PCF App
- [ ] Blue-green deployment plan and rollback steps documented for each new app
- [ ] DAL and PHX foundation deployments verified — both foundations healthy before cutover

-----

### 2. RabbitMQ Readiness

- [ ] All new cash flow queues created and verified in RabbitMQ management console
- [ ] DLQ queues created and bound for consent queue and webhook queue
- [ ] All routing keys and exchange bindings validated end to end in lower environment
- [ ] Cashflow Consumer PCF App DLQ consumer deployed and listening before go-live
- [ ] All cash flow queues configured as durable — survives RabbitMQ restart
- [ ] Message TTL set appropriately — stale messages expire to DLQ
- [ ] Queue depth threshold alert configured for back pressure monitoring

-----

### 3. Plaid Integration Readiness

- [ ] Production Plaid API keys loaded into PCF credential hub — not lower environment keys
- [ ] Production webhook URL registered in Plaid dashboard pointing to Apply Webhooks Proxy
- [ ] Plaid webhook signing secret loaded in Webhooks PCF App via credential hub
- [ ] Plaid outbound IPs whitelisted in Synchrony firewall — DWSBC-26621 must be CLOSED before go-live
- [ ] Plaid hosted link expiry window confirmed — consent UI must present within that window
- [ ] All Plaid error codes classified and behavior implemented before production traffic

-----

### 4. Apigee Proxy Readiness

- [ ] Apply Consent Proxy promoted through Dev, QA, and Prod
- [ ] Apply Webhooks Proxy promoted through Dev, QA, and Prod
- [ ] Retailcard-credit-apply Proxy new fields story CLOSED and promoted to Prod
- [ ] All swagger updates approved and published in SwaggerHub before go-live
- [ ] Spike arrest policies on webhook proxy tested under load
- [ ] Correlation ID flowing from all proxies to PCF apps — validated in Splunk
- [ ] AAB approval DWSBC-25798 confirmed valid for production promotion

-----

### 5. Database Readiness

- [ ] Cash flow table schema confirmed applied to production DB — DWSBC-25649 CLOSED
- [ ] Indexes on app_id, status, and created_at columns verified for query performance
- [ ] DB connection pool sized appropriately for all new apps under peak load
- [ ] All apps tested for transient DB failure handling — retry with backoff verified
- [ ] DB rollback script prepared and reviewed in case of production incident

-----

### 6. Observability Readiness

- [ ] End-to-end cash flow journey Splunk dashboard deployed and verified in production
- [ ] All Splunk alerts deployed, thresholds set, and routed to correct on-call channel
- [ ] All new PCF apps showing in New Relic with baseline response time and error rate established
- [ ] Single transaction traced end to end in Splunk using one correlation ID — verified
- [ ] All structured log keywords verified in Splunk for each new app before go-live
- [ ] On-call runbook published in Confluence and shared with on-call team before cutover

-----

### 7. Resiliency and Idempotency Readiness

- [ ] DLQ routing tested and manual replay validated in staging or production-like environment
- [ ] Duplicate consent, duplicate webhook, and duplicate MQ message all tested and verified idempotent
- [ ] Resilience4j circuit breaker tested by simulating Plaid API and Synapps CF Pass2 downtime
- [ ] All retry exhaustion paths confirmed routing to DLQ and triggering Splunk alert
- [ ] Concurrent scheduler runs tested — confirmed same app id not processed twice
- [ ] Out-of-order state transition attempts tested and rejected correctly

-----

### 8. Security Readiness

- [ ] Plaid webhook signature validation active and tested with real Plaid payloads in staging
- [ ] Final code review confirms zero secrets or API keys in any repository or manifest file
- [ ] All PCF credential hub bindings verified for each new app in production space
- [ ] Auth policies on Apply Consent Proxy and Apply Webhooks Proxy tested and rejecting unauthorized calls
- [ ] Cash flow data handling reviewed by security team — app id and token storage in DB assessed for PCI compliance

-----

### 9. Mock and Contract Test Readiness

- [ ] Prequal proxy mock test cases story CLOSED before go-live
- [ ] Consent proxy mock test cases story CLOSED before go-live
- [ ] CF Integration JAR contract tests passing in CI pipeline for both consumers
- [ ] Apply Consent Proxy E2E and Apply Webhooks Proxy E2E both passing in QA

-----

### 10. Go-Live Deployment Sequence

Deploy in this exact order to avoid failures during production cutover:

```
Step 1  — Confirm DB schema already in prod (DWSBC-25649 CLOSED)
Step 2  — Deploy CF Integration JAR to Nexus/Artifactory
Step 3  — Deploy Cashflow Consumer PCF App
Step 4  — Deploy Consent PCF App
Step 5  — Deploy Webhooks PCF App
Step 6  — Deploy Cashflow Scheduler PCF App
Step 7  — Promote Apply Consent Proxy to production
Step 8  — Promote Apply Webhooks Proxy to production
Step 9  — Promote Retailcard-credit-apply Proxy changes to production
Step 10 — Deploy Prequal PCF App changes to production (LAST — activates 0082 flow)
Step 11 — Verify Splunk dashboards showing live traffic
Step 12 — Verify first real 0082 transaction traced end to end in Splunk
Step 13 — Keep DLQ monitor active for first 24 hours post go-live
```

> **Critical:** Prequal PCF App must be deployed LAST. It is the entry point that activates the entire 0082 flow. Every downstream app and proxy must be live and verified before Prequal PCF App is promoted to production.

-----

*Cash Flow Integration | Missing Stories and Production Readiness | March 2026*
