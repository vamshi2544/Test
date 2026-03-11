# Prompt: Regenerate Our Team AI Learning Roadmap Page

Paste this entire prompt into Claude (claude.ai or IntelliJ Copilot with Claude model) to regenerate or update our interactive roadmap page.

---

## THE PROMPT

```
Create a single-file self-contained interactive HTML page for our small team (2-5 people, beginners to AI) learning Agentic AI. We are self-paced and our end goal is to build something using agentic AI — we don't have a specific product in mind yet.

---

DESIGN REQUIREMENTS:
- Dark theme, background color #0a0a0f
- Font: Syne (headings) + JetBrains Mono (body) from Google Fonts
- Subtle noise/grain texture overlay using inline SVG filter
- Staggered fade-up animation on load for all cards and phases
- CSS variables for all colors
- Mobile-friendly using CSS Grid and Flexbox
- All CSS and JS inline — single HTML file, no external files except Google Fonts CDN

COLOR PALETTE:
- Background: #0a0a0f
- Surface: #111118
- Border: #1e1e2e
- Muted text: #6b6b80
- Main text: #e8e8f0
- Phase 1 accent: #7c6af7 (purple)
- Phase 2 accent: #f76a8a (pink)
- Phase 3 accent: #6af7c8 (teal)
- Phase 4 accent: #f7c46a (amber)
- Phase 5 accent: #a06af7 (violet)

---

PAGE STRUCTURE — 4 tabs:

TAB 1: ROADMAP
A vertical timeline with 5 phases. Each phase has:
- A numbered dot on the left spine (colored per phase)
- Phase number, title, duration badge
- A grid of topic cards (click to mark done — adds checkmark, dims card, saves to localStorage)
- An insight callout at the bottom of each phase

PHASES AND TOPICS:

Phase 01 — AI Foundations (3–4 weeks) — color #7c6af7
Topics:
1. What is AI? — Types of AI: narrow vs general. ML vs rule-based systems. Where we are in 2025. [tag: concept]
2. How LLMs Work — Transformers, tokens, context windows, temperature. What "training" means. [tag: concept]
3. Prompt Engineering — System prompts, few-shot examples, chain-of-thought. How to talk to AI. [tag: hands-on]
4. API Basics — Calling the Anthropic / OpenAI API. Inputs, outputs, costs, rate limits. [tag: hands-on]
5. AI Limitations — Hallucinations, knowledge cutoffs, bias. What AI cannot do reliably. [tag: concept]
6. Ethics & Safety — Responsible AI use, data privacy, model safety basics. [tag: concept]
Insight: Start here. Before touching any code or frameworks, your team needs a solid mental model of what AI actually is, how LLMs work, and the vocabulary to discuss it confidently.

Phase 02 — Python & Dev Environment (2–3 weeks) — color #f76a8a
Topics:
1. Python Basics — Variables, loops, functions, dicts/lists. Enough to read and modify code. [tag: skill]
2. pip & Libraries — Installing packages, virtual environments, requirements.txt. [tag: skill]
3. JSON & APIs — Reading JSON, making HTTP requests with Python. REST basics. [tag: skill]
4. Jupyter Notebooks — Running experiments, documenting findings, sharing results. [tag: hands-on]
5. Git Basics — Version control for your team. Commits, branches, pull requests. [tag: skill]
Insight: Tools of the trade. You don't need to be expert Python developers, but your team needs to be comfortable running scripts, installing libraries, and reading code.

Phase 03 — RAG & Knowledge Systems (3–4 weeks) — color #6af7c8
Topics:
1. Embeddings — How text becomes vectors. Semantic similarity and search. [tag: concept]
2. Vector Databases — Pinecone, Chroma, Weaviate. Storing and querying embeddings. [tag: hands-on]
3. RAG Pipeline — Chunking docs → embedding → storing → retrieving → prompting. [tag: hands-on]
4. Document Loaders — Ingesting PDFs, websites, databases into your knowledge base. [tag: hands-on]
5. Reranking & Eval — How to measure if your RAG system is actually working. [tag: concept]
Insight: Give AI a memory. RAG is the #1 most practically useful technique. It lets your AI answer questions about YOUR data, not just what it was trained on.

Phase 04 — AI Agents & Tools (4–5 weeks) — color #f7c46a
Topics:
1. What is an Agent? — The perceive → think → act loop. How agents differ from chatbots. [tag: concept]
2. Tool Use / Function Calling — Giving the LLM tools it can invoke. Defining schemas. [tag: hands-on]
3. ReAct Pattern — Reason + Act. The core loop behind most agent architectures. [tag: concept]
4. LangChain / LlamaIndex — Popular frameworks to build agents without reinventing the wheel. [tag: hands-on]
5. Memory Types — Short-term (context), long-term (vector DB), episodic, semantic. [tag: concept]
6. Error Handling — Agents fail. Retry logic, fallbacks, human-in-the-loop patterns. [tag: hands-on]
Insight: The core of agentic AI. Agents are LLMs that can take actions — calling APIs, running code, browsing the web, managing files. This is where it gets powerful.

Phase 05 — Multi-Agent Systems & Build (4–6 weeks) — color #a06af7
Topics:
1. Multi-Agent Patterns — Orchestrator + worker. Parallel agents. Hierarchical systems. [tag: concept]
2. Agent Communication — How agents pass messages, share state, and coordinate. [tag: concept]
3. AutoGen / CrewAI — Frameworks purpose-built for multi-agent collaboration. [tag: hands-on]
4. Evaluation & Evals — Testing agent behavior. Benchmarks, human eval, automated grading. [tag: hands-on]
5. Deployment — Running agents in production. APIs, queues, monitoring, costs. [tag: skill]
6. Your First Agent Product — Pick a use case and ship it. Document everything your team learned. [tag: project]
Insight: Build something real. Multi-agent systems divide complex tasks between specialized agents. This phase is about putting it all together and shipping your first agentic product.

---

TAB 2: ARCHITECTURE
Title: "Agentic AI System Architecture"
Subtitle: "Every agentic AI system is built from these layers. Click each layer to understand what it does and what you need to learn to build it."

Show a top flow diagram first:
User Input → LLM Brain → Planning → Tools → Memory → Output
(styled divs with arrows, dark surface cards)

Then 6 expandable accordion layers (collapsed by default, click to expand):

Layer 1 — 🧠 LLM Core (The Brain) — bg rgba(124,106,247,0.12)
Desc: The language model that reasons, plans, and generates responses
Components:
- Foundation Model: Claude, GPT-4, Gemini — the base reasoning engine you call via API
- System Prompt: Instructions that define the agent's personality, rules, and available tools
- Context Window: The "working memory" — everything the LLM can see at once (messages, tool results)
- Temperature / Sampling: Controls randomness and creativity in outputs

Layer 2 — 🛠 Tool Layer (Actions) — bg rgba(247,106,138,0.12)
Desc: What the agent can DO in the world — APIs, code execution, file access
Components:
- Web Search: Agent can look up current information in real-time
- Code Interpreter: Run Python, do math, process data, generate charts
- API Connectors: Talk to Slack, email, databases, CRMs, any external service
- File I/O: Read/write documents, PDFs, spreadsheets
- Browser Control: Navigate websites, fill forms, extract data (Playwright, Puppeteer)

Layer 3 — 💾 Memory Layer — bg rgba(106,247,200,0.10)
Desc: How the agent stores and retrieves information over time
Components:
- Conversation History: Short-term: the current chat thread in the context window
- Vector Database: Long-term: semantic search over documents and past interactions
- Key-Value Store: Fast structured memory for user preferences, entity facts
- Episodic Memory: Records of past actions and their outcomes for learning

Layer 4 — 🤔 Planning & Reasoning — bg rgba(247,196,106,0.10)
Desc: How the agent breaks down complex tasks and decides what to do next
Components:
- ReAct Loop: Reason → Act → Observe → Repeat until goal is met
- Task Decomposition: Breaking a complex goal into smaller, executable sub-tasks
- Chain-of-Thought: Prompting the model to think step-by-step before answering
- Self-Reflection: Agent reviews its own output and corrects mistakes

Layer 5 — 🤝 Multi-Agent Orchestration — bg rgba(160,106,247,0.12)
Desc: Coordinating multiple specialized agents to solve complex problems
Components:
- Orchestrator Agent: The "manager" that receives the task and delegates to specialists
- Specialist Agents: Focused agents: researcher, writer, coder, reviewer, etc.
- Message Bus: How agents communicate results and handoff tasks to each other
- Human-in-the-Loop: Checkpoints where humans approve or redirect agent decisions

Layer 6 — 📡 Infrastructure & Observability — bg rgba(106,200,247,0.10)
Desc: The plumbing that makes agents reliable and scalable in production
Components:
- Task Queues: Managing async agent jobs at scale (Celery, BullMQ)
- Logging & Tracing: Seeing exactly what your agent did and why (LangSmith, Langfuse)
- Cost Tracking: Monitoring API usage, tokens, and spend per agent run
- Rate Limiting: Handling API limits gracefully without crashing

---

TAB 3: GLOSSARY
Title: "Key Concepts Glossary"
Show as a responsive card grid (3 columns on desktop, 1 on mobile)

Terms:
1. LLM — Large Language Model. A neural network trained on massive text data that can generate human-like text, answer questions, and reason. Example: Claude (Anthropic), GPT-4 (OpenAI), Gemini (Google)
2. Agent — An AI system that can perceive its environment, make decisions, and take actions to achieve a goal — not just answer questions. Example: An agent can search the web, write code, send emails, and check if the task succeeded.
3. Tool Use / Function Calling — The ability for an LLM to call external functions or APIs instead of just generating text. Example: You give Claude a "search_web" tool — it decides when to call it and uses the result.
4. RAG — Retrieval-Augmented Generation. Finding relevant documents from a knowledge base and including them in the prompt before the LLM answers. Example: Ask "What is our refund policy?" → retrieve policy doc → LLM answers using it.
5. Embeddings — A way of representing text as a list of numbers (a vector) that captures semantic meaning. Similar text = similar vectors. Example: "Happy" and "joyful" would have very similar embedding vectors.
6. Vector Database — A database designed to store and search embeddings. Lets you find semantically similar content at scale. Example: Pinecone, Chroma, Weaviate, pgvector
7. Context Window — The maximum amount of text (measured in tokens) an LLM can "see" at once in a single call. Example: Claude 3.5 has a 200K token context — roughly 150,000 words.
8. Token — The basic unit of text an LLM processes. Roughly 1 token ≈ 0.75 words. You pay per token when using APIs. Example: "Hello world" = 2 tokens. "Unbelievable" = 3–4 tokens.
9. Prompt Engineering — The skill of designing inputs to an LLM to get the best possible outputs. Includes system prompts, examples, and formatting. Example: Adding "Think step by step" often dramatically improves complex reasoning.
10. ReAct — Reason + Act. A pattern where the agent alternates between reasoning about what to do and taking an action, until the goal is complete. Example: Think: "I need to find the price" → Act: search_web("iPhone 16 price") → Observe result → Think again…
11. Orchestrator — In multi-agent systems, the agent that receives the high-level goal and coordinates other specialist agents to complete subtasks. Example: Research agent + Writing agent + Review agent, all managed by an Orchestrator.
12. Hallucination — When an LLM confidently states something that is factually wrong. A fundamental limitation of current AI. Example: Asking for citations and getting plausible-looking but completely fake paper titles.
13. Fine-tuning — Training a pretrained LLM further on your own data to specialize its behavior. Expensive but powerful. Example: Fine-tuning GPT on your company's support tickets to improve tone and accuracy.
14. Chain-of-Thought — A prompting technique that instructs the LLM to show its reasoning step-by-step before giving a final answer. Example: Adding "Let's think through this step by step:" to a math problem improves accuracy.
15. System Prompt — Instructions given to the LLM at the start of every conversation, before user messages. Defines behavior, persona, and rules. Example: "You are a helpful customer service agent for Acme Corp. Always be polite."

---

TAB 4: PROGRESS TRACKER
Title: "Team Progress Tracker"
Subtitle: "Click topics in the Roadmap to mark them complete. Track your team's journey here."

Show:
- A top progress bar (gradient from #7c6af7 to #f76a8a) showing overall % complete
- One row per phase showing: colored dot, phase name, "X/Y topics complete", a mini progress bar, and a % label
- Progress is driven by the same localStorage state as the roadmap topic cards

---

FOOTER:
Left: "Agentic AI Roadmap · Team Edition"
Right: "Click topics to mark as done"

---

OUTPUT: A single complete working HTML file. Fill in all content exactly as specified. No placeholders.
```

---

## HOW TO USE THIS PROMPT

1. Copy everything between the triple backticks above
2. Paste into **claude.ai** (best — you get live preview) or **IntelliJ Copilot with Claude model**
3. Claude will output the complete HTML
4. Save the output as `ai-roadmap.html`
5. Open in any browser — works offline

## HOW TO UPDATE THE ROADMAP

To **add a new topic** to a phase, paste this after the prompt:
> "Add a new topic to Phase 3 called 'Hybrid Search' — description: combining vector search with keyword search for better retrieval accuracy. Tag: hands-on"

To **add a new phase**, paste:
> "Add a Phase 6 called 'Production & Scale' (duration: ongoing) with color #f76a8a covering: monitoring, cost optimization, CI/CD for agents, and user feedback loops"

To **change the color scheme**, paste:
> "Change the color scheme to be warmer — replace purples with deep oranges (#f76a3a) and keep the teal accent"

---

*Prompt saved: March 2026 — Team AI Learning Initiative*
