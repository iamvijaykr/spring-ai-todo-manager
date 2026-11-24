# Todo Flow

Todo Flow is a task list with a website and an optional AI helper. Add tasks, mark them done, and ask AI for a short summary and suggestions for what to do next.

The website and the Java server are kept in separate folders. Start them in two terminals on your computer.

## What you need

- Java 21 or newer.
- Maven 3.6.3 or newer.
- Node.js 20.19+, 22.12+, or 24+, with npm.
- An OpenAI key if you want to use the AI helper.

Check that Java, Maven, and Node.js are available:

```bash
java -version
mvn -version
node --version
npm --version
```

## Run it on your computer

### 1. Start the server

Open a terminal in the project folder (the folder containing this `README.md`). Build the server:

```bash
mvn -pl backend/todo-app -am package
```

AI is optional. If you have an OpenAI key, set it in this same terminal before starting the server.

**Windows PowerShell:**

```powershell
$env:OPENAI_API_KEY = "your-key"
```

**macOS or Linux:**

```bash
export OPENAI_API_KEY="your-key"
```

Now start the server:

```bash
java -jar backend/todo-app/target/todo-app-0.0.1-SNAPSHOT.jar
```

Leave this terminal open. Without an OpenAI key, the task list still works; the AI helper does not.

### 2. Start the website

Open a second terminal in the project folder and run:

```bash
cd frontend
npm ci
npm start
```

Leave this terminal open too, then visit [http://localhost:4200](http://localhost:4200).

The website sends its requests to the server automatically. If you change the server address or port, update [`frontend/proxy.conf.json`](./frontend/proxy.conf.json).

## How to use it

- Add a task with a title and optional description.
- Edit, finish, filter, or remove tasks from the list.
- Select **Give me some ideas** for a short summary and up to three suggestions.
- To use the server without the website, open [http://localhost:8080](http://localhost:8080).

When you ask for AI suggestions, the task titles, descriptions, and completion states are sent to OpenAI. Do not enter information you do not want to share with that service.

Tasks are stored in a temporary database. They are cleared when the server stops.

## Try the server directly

Show all tasks:

```bash
curl http://localhost:8080/api/todos
```

Add a task:

```bash
curl -X POST http://localhost:8080/api/todos \
  -H "Content-Type: application/json" \
  -d '{"title":"Plan the week","description":"Review my priorities","completed":false}'
```

Ask for AI suggestions (requires an OpenAI key):

```bash
curl -X POST http://localhost:8080/api/ai/todos/insights
```

On Windows PowerShell, use `curl.exe` instead of `curl`.

| Method and address | What it does |
| --- | --- |
| `GET /api/todos` | Show every task. |
| `GET /api/todos/{id}` | Show one task. |
| `POST /api/todos` | Add a task. |
| `PUT /api/todos/{id}` | Change a task. |
| `DELETE /api/todos/{id}` | Remove a task. |
| `POST /api/ai/todos/insights` | Get a summary and suggestions. |

## Run the checks

Build the server and run its tests from the project folder:

```bash
mvn -pl backend/todo-app -am verify
```

Build the website from the `frontend` folder:

```bash
npm run build
```

Run the website tests from the `frontend` folder:

```bash
npm test
```

The website tests need Chrome installed.

## Where things live

| Folder or file | What it is for |
| --- | --- |
| [`frontend/`](./frontend/) | The website and its setup steps. |
| [`backend/`](./backend/) | The Java server and its setup steps. |
| [`backend/todo-app/`](./backend/todo-app/) | Starts the server and receives website requests. |
| [`backend/todo-domain/`](./backend/todo-domain/) | Describes a task and its basic rules. |
| [`backend/todo-application/`](./backend/todo-application/) | Handles the actions people can take on tasks. |
| [`backend/todo-infrastructure/`](./backend/todo-infrastructure/) | Saves tasks and connects to OpenAI. |
| [`docs/how-it-works.md`](./docs/how-it-works.md) | Explains how the folders and request steps fit together. |

For more detail, see the [server guide](./backend/README.md), [website guide](./frontend/README.md), and [how-it-works guide](./docs/how-it-works.md).

## If something does not work

- **The website cannot load tasks:** Make sure the server terminal is still running on port 8080.
- **Port 8080 or 4200 is already in use:** Stop the other program using that port, then start Todo Flow again.
- **The AI helper reports an error:** Check that the key is valid, set it in the server terminal, and restart the server.
- **Tasks disappear after restart:** This version uses temporary storage. It does not keep tasks after the server stops.
- **`npm ci` fails:** Check your Node.js and npm versions, then run it again from the `frontend` folder.
