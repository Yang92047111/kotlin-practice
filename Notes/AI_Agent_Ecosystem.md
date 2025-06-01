# 🧠 AI Agent 生態系統總覽筆記

---

## 🧩 1. 系統角色分類

| 角色類型          | 定義                  | 代表項目                                                           |
| ------------- | ------------------- | -------------------------------------------------------------- |
| **Actor**     | 執行任務的智能實體，能感知、推理與行動 | 🧠 AI Agent                                                    |
| **Protocol**  | 定義通信、整合、協作的標準或中介    | 🔌 MCP（Model Context Protocol）、🔄 A2A（Agent-to-Agent Protocol） |
| **Technique** | 增強 AI 能力的技術手段       | 📚 RAG（Retrieval-Augmented Generation）、Tool Use、Memory         |

---

## 🧠 2. Actor：AI Agent

### 定義：

具備目標導向的智能邏輯，可以自主行動、推理、使用工具，甚至協作。

### 特性：

* ✅ 自主性（可自行規劃任務流程）
* ✅ 可感知上下文（使用記憶 / RAG / embedding）
* ✅ 可調用外部工具與系統（通常透過 protocol，如 MCP）

### 範例：

* **AutoGPT / AgentGPT**：具備自我迴圈規劃與任務執行能力
* **LangGraph Agents**：以 workflow graph 編排 agent 的決策與狀態
* **CrewAI**：具備角色與任務協調的 agent 系統
* **Meta CICERO**：多輪推理與協商的遊戲型 AI agent

---

## 🔌 3. Protocol：Agent 與世界的橋梁

### 📦 A. Model Context Protocol (MCP)

* **開發者**：Anthropic
* **用途**：讓模型（如 Claude）存取外部資源（API、DB、Filesystem）
* **架構**：Client (模型內嵌) + Server (橋接外部系統)
* **特點**：

  * 模型可以原生存取資料，不需寫外掛程式碼
  * 有良好沙箱與隔離設計

### 🔁 B. Agent-to-Agent Protocol (A2A)

* **用途**：使多個 AI agent 能夠溝通與協作
* **常見功能**：

  * 任務委派（delegation）
  * 狀態同步（state sharing）
  * 協商與角色分工（coordination）

---

## 🧱 4. Framework：協助開發與管理 Agent 的框架比較

| 框架            | 架構類型     | 支援內容                                | 適用範圍                |
| ------------- | -------- | ----------------------------------- | ------------------- |
| **LangChain** | 物件導向鏈式設計 | Memory, RAG, Tools, Agent chains    | 任務導向 Agent pipeline |
| **LangGraph** | 有狀態圖模型   | Agent node graph, state transitions | 多 Agent 協作、狀態追蹤     |
| **CrewAI**    | 團隊角色模型   | 每個 Agent 具任務與專業角色                   | 組織化、多角色任務分工場景       |
| **AutoGPT**   | 自我反覆規劃迴圈 | 規劃 → 執行 → 評估 → 迴圈                   | 通用任務自動化（探索性研究）      |

---

## 📚 5. Technique：基礎技術比較

| 技術            | 功能                     | 舉例用途             |
| ------------- | ---------------------- | ---------------- |
| **RAG**       | 結合向量搜尋與生成，提升回答準確性      | 企業知識庫查詢、技術問答     |
| **Tool Use**  | 調用外部工具（API、Calculator） | 查天氣、查資料庫、打 API   |
| **Memory**    | 長短期記憶儲存與檢索             | 多輪對話、用戶偏好學習      |
| **Embedding** | 將文字語意轉換為向量，便於比對        | 知識檢索、語意分類、文件關聯分析 |

---

## 🔚 6. 總結：角色關係圖（文字版）

```
[User]
   ↓
[AI Agent] ←→ [Other Agents via A2A]
   ↓
[MCP → DB/API/Filesystem]
   ↓
[Technique: RAG, Tool Use, Memory, Embedding]
```

* **AI Agent 是中心主角**，決定怎麼完成任務
* **MCP** 是 Agent 存取工具/資源的通道
* **A2A** 是 Agent 之間協作溝通的語言
* **RAG / Tool Use 等技術** 則強化 Agent 的智能與知識基礎

---

# 🧠 AI Agent Ecosystem – Structured Notes (English)

---

## 🧩 1. Role Classification

| Role Type     | Definition                                                 | Examples                                                          |
| ------------- | ---------------------------------------------------------- | ----------------------------------------------------------------- |
| **Actor**     | Intelligent entity that performs tasks and makes decisions | 🧠 AI Agent                                                       |
| **Protocol**  | Standards enabling communication or integration            | 🔌 MCP (Model Context Protocol), 🔄 A2A (Agent-to-Agent Protocol) |
| **Technique** | Foundational methods enhancing model capabilities          | 📚 RAG (Retrieval-Augmented Generation), Tool Use, Memory         |

---

## 🧠 2. Actor: AI Agent

### Definition:

A goal-driven autonomous or semi-autonomous software entity capable of reasoning, perceiving, and taking actions.

### Key Characteristics:

* ✅ **Autonomy** (can plan and act without human intervention)
* ✅ **Context awareness** (via memory, RAG, embeddings)
* ✅ **Tool use** (uses protocols like MCP to call APIs, access databases, etc.)

### Examples:

* **AutoGPT / AgentGPT** – self-looping task planners
* **LangGraph Agents** – graph-based stateful agent workflows
* **CrewAI** – multi-agent teamwork with role coordination
* **Meta CICERO** – agents with negotiation and reasoning in games

---

## 🔌 3. Protocol: Connecting Agents with the World

### 📦 A. Model Context Protocol (MCP)

* **By**: Anthropic
* **Purpose**: Enables models like Claude to access external tools, APIs, filesystems
* **Architecture**: Client (inside model) ↔ Server (handles external access)
* **Key Features**:

  * Simplified tool integration for LLMs
  * Built-in sandboxing and access control

### 🔁 B. Agent-to-Agent Protocol (A2A)

* **Purpose**: Allows agents to communicate and collaborate
* **Capabilities**:

  * Task delegation
  * State sharing
  * Multi-agent coordination (e.g., planning, negotiation)

---

## 🧱 4. Framework Comparison: Building & Managing Agents

| Framework     | Architecture                  | Key Features                           | Use Case Scope                            |
| ------------- | ----------------------------- | -------------------------------------- | ----------------------------------------- |
| **LangChain** | Chain-based (object-oriented) | RAG, memory, tools, agents             | Task pipelines with tool calling          |
| **LangGraph** | Graph-based, stateful         | Agents as nodes, state tracking        | Multi-agent workflows, condition handling |
| **CrewAI**    | Team/role-based               | Agents with roles and responsibilities | Complex tasks with collaborative agents   |
| **AutoGPT**   | Self-looping planner          | Plan → Execute → Reflect → Repeat      | Open-ended autonomous task completion     |

---

## 📚 5. Technique Comparison: Enhancing Agent Capabilities

| Technique     | Function                                    | Example Use Case                       |
| ------------- | ------------------------------------------- | -------------------------------------- |
| **RAG**       | Combines retrieval (vector DB) + generation | Domain-specific QA, enterprise search  |
| **Tool Use**  | Calls external APIs or calculators          | Fetching weather, calling backend APIs |
| **Memory**    | Stores short- or long-term interaction data | Multi-turn dialogue, personalization   |
| **Embedding** | Converts text into semantic vectors         | Document similarity, clustering        |

---

## 🔚 6. Summary: System Interaction Map (Text Form)

```
[User]
   ↓
[AI Agent] ←→ [Other Agents via A2A]
   ↓
[MCP → External Tools / DB / APIs / Files]
   ↓
[Techniques: RAG, Tool Use, Memory, Embedding]
```

* **AI Agent** is the main decision-maker and executor.
* **MCP** allows the agent to access real-world tools and data.
* **A2A** enables multi-agent systems to collaborate or delegate tasks.
* **Techniques** like RAG and Tool Use empower the agent to be context-aware and capable.
