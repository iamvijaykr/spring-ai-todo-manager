# Java server guide

The server accepts requests from the website, checks them, and saves tasks. It can also send the task list to OpenAI when someone asks for AI suggestions.

## Start the server on your computer

From the project folder, build and test the server:

```bash
mvn -pl backend/todo-app -am package
```

AI is optional. Before starting the server, set `OPENAI_API_KEY` in the same terminal if you want AI suggestions.

**Windows PowerShell:**

```powershell
$env:OPENAI_API_KEY = "your-key"
```

**macOS or Linux:**

```bash
export OPENAI_API_KEY="your-key"
```

Start the server:

```bash
java -jar backend/todo-app/target/todo-app-0.0.1-SNAPSHOT.jar
```

Keep the terminal open. The server uses port `8080`. Its first page is [http://localhost:8080](http://localhost:8080).

## Request examples

Add a task:

```bash
curl -X POST http://localhost:8080/api/todos \
  -H "Content-Type: application/json" \
  -d '{"title":"Plan the week","description":"Review my priorities","completed":false}'
```

Ask for a summary and suggestions:

```bash
curl -X POST http://localhost:8080/api/ai/todos/insights
```

The AI request needs a valid OpenAI key. It sends task titles, descriptions, and completion states to OpenAI. Avoid putting private information in tasks if you use this feature.

On Windows PowerShell, use `curl.exe` instead of `curl`.

## Available addresses

| Method | Address | What it does |
| --- | --- | --- |
| `GET` | `/api/todos` | Show every task. |
| `GET` | `/api/todos/{id}` | Show one task. |
| `POST` | `/api/todos` | Add a task. |
| `PUT` | `/api/todos/{id}` | Change a task. |
| `DELETE` | `/api/todos/{id}` | Remove a task. |
| `POST` | `/api/ai/todos/insights` | Get a summary and up to three suggestions. |

## Server folders

- `todo-app` starts the server and defines the addresses it accepts.
- `todo-domain` defines what a task contains.
- `todo-application` handles task actions and describes what the server needs from storage and AI.
- `todo-infrastructure` saves tasks and connects to OpenAI.

See [how the parts work together](../docs/how-it-works.md) for the full request path.
