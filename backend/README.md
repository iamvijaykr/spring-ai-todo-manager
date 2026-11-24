# Java program guide

The Java program is the part that receives tasks from the website and saves them. It can also ask OpenAI for a summary and suggestions.

## Start it on your computer

Open a terminal in the main project folder—the one containing the top-level README—and prepare the program:

```bash
mvn -pl backend/todo-app -am package
```

To use AI, set your OpenAI key in that same terminal. This step is optional.

**Windows PowerShell:**

```powershell
$env:OPENAI_API_KEY = "your-key"
```

**macOS or Linux:**

```bash
export OPENAI_API_KEY="your-key"
```

Start the program:

```bash
java -jar backend/todo-app/target/todo-app-0.0.1-SNAPSHOT.jar
```

Keep the terminal open. The program uses port `8080`. Open [http://localhost:8080](http://localhost:8080) to check that it started.

## Task addresses

The website uses these addresses automatically. You can also use them to test the Java program directly.

| Action | Address |
| --- | --- |
| See all tasks | `GET /api/todos` |
| See one task | `GET /api/todos/{id}` |
| Add a task | `POST /api/todos` |
| Change a task | `PUT /api/todos/{id}` |
| Remove a task | `DELETE /api/todos/{id}` |
| Get AI suggestions | `POST /api/ai/todos/insights` |

`GET` means read, `POST` means add or ask, `PUT` means change, and `DELETE` means remove. Replace `{id}` with the task's ID.

Example: add a task

```bash
curl -X POST http://localhost:8080/api/todos \
  -H "Content-Type: application/json" \
  -d '{"title":"Plan the week","description":"Review my priorities","completed":false}'
```

Example: ask for AI suggestions

```bash
curl -X POST http://localhost:8080/api/ai/todos/insights
```

On Windows PowerShell, use `curl.exe` instead of `curl`. AI suggestions need a valid OpenAI key. The task title, description, and done status are sent to OpenAI when you ask for suggestions.

## Where server code lives

- `todo-app/` starts the Java program and receives requests.
- `todo-domain/` describes what a task is.
- `todo-application/` handles adding, changing, finding, and removing tasks.
- `todo-infrastructure/` saves tasks and connects to OpenAI.

The saved tasks are temporary. They are cleared when the Java program stops.

For the full path a task takes, see [How it works](../docs/how-it-works.md).
