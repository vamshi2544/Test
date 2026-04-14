# Engineering AI Standards Repository

## Purpose

This repository serves as the centralized source of truth for AI-assisted engineering standards, reusable prompts, governance rules, and implementation templates used across development teams.

Its purpose is to standardize how engineers and AI assistants collaborate during design, implementation, review, upgrades, troubleshooting, and operational workflows.

---

## Objectives

- Improve consistency across teams and repositories
- Reduce AI misuse and hallucination risk through structured guidance
- Standardize implementation planning, review, and delivery workflows
- Accelerate development while preserving enterprise engineering quality
- Create reusable templates for common engineering tasks
- Establish long-term maintainable AI governance for engineering teams

---

## Repository Structure

```text
/
├── prompts/
│   ├── task-prompts/
│   ├── workflow-prompts/
│   └── upgrade-prompts/
│
├── standards/
│   ├── RULES.md
│   ├── OUTPUT-FORMAT.md
│   ├── BEST-PRACTICES.md
│   ├── CHANGE-CHECKLIST.md
│   └── ...
│
├── templates/
│   ├── specs/
│   ├── checklists/
│   └── design/
│
└── examples/
    └── sample-usage/
```

---

## How Teams Should Use This Repository

### Before Starting Any Non-Trivial Task
Teams should review and/or copy applicable standards into their project repositories, including:

- PROJECT-CONTEXT-PROMPT.md
- RULES.md
- OUTPUT-FORMAT.md
- BEST-PRACTICES.md
- DEPENDENCY-INTEGRATION-MAP.md
- CHANGE-CHECKLIST.md

---

### During Task Planning
Use the relevant reusable specification template from `/templates/specs/` to define the work before implementation.

Examples:
- New Endpoint
- DB Integration
- Kafka/RabbitMQ Changes
- Spring Boot Upgrade
- Bug Fix
- Deployment Changes

---

### Before PR / Completion
Use `CHANGE-CHECKLIST.md` to verify:
- Risk areas reviewed
- Docs updated
- Dependencies assessed
- Tests identified
- Rollback considered

---

## Governance Principles

All prompts and templates in this repository should follow these principles:

1. **Safety First**  
   Avoid unnecessary or risky AI-generated changes.

2. **Minimal Change Philosophy**  
   Prefer targeted modifications over broad refactors.

3. **Repository Truth Over Assumption**  
   AI must rely on repository evidence.

4. **Explicit Uncertainty**  
   Unknowns must be marked clearly.

5. **Human Sign-Off for Major Changes**  
   Significant implementations require review before execution.

---

## Contribution Guidelines

When updating this repository:

- Preserve backward compatibility of existing prompts/templates where practical
- Improve clarity without unnecessary expansion
- Keep prompts reusable across teams/projects
- Avoid overfitting prompts to one repository
- Update README / documentation when adding major categories

---

## Ownership

Primary Owners:
- Engineering Leads / Architects

Contributors:
- Senior Engineers
- Platform Engineers
- Team Leads

---

## Long-Term Vision

This repository is intended to evolve into the enterprise engineering AI playbook for all supported repositories and development teams.

It should remain maintainable, practical, and aligned with real engineering workflows.
