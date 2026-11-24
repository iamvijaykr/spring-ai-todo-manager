# Todo Flow

Todo Flow is a simple task list with a website and an optional AI helper. Add tasks, mark them done, and ask AI for a short summary and ideas for what to do next.

The project has two parts:

- `backend/` is the Java program that saves tasks.
- `frontend/` is the website you use in your browser.

## What you need

- Java 21 or newer.
- Maven 3.6.3 or newer.
- Node.js 20.19+, 22.12+, or 24+, with npm.
- An OpenAI key only if you want to use AI suggestions.

To check that the tools are installed, run:

```bash
java -version
mvn -version
node --version
npm --version
```

## Start the app on your computer

Keep both parts running in separate terminals.

### 1. Start the Java program

Open a terminal in the main project folder—the one containing this README—and prepare the server:

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

Leave this terminal open. The task list works without an OpenAI key, but AI suggestions need one.

### 2. Start the website

Open a second terminal in the main project folder and run:

```bash
cd frontend
npm ci
npm start
```

Leave this terminal open too. Open [http://localhost:4200](http://localhost:4200) in your browser.

### 3. Use Todo Flow

- Add a task with a title and optional description.
- Edit it, mark it done, filter the list, or remove it.
- Select **Give me some ideas** to get an AI summary and suggestions.

The website contacts the Java program automatically. You do not need to configure a connection.

## Run both parts with Docker

Install Docker Desktop (or Docker Engine with Docker Compose). From the main project folder, run:

```bash
docker compose up --build
```

Docker builds a website image and a Java server image, then starts both. Open [http://localhost:4200](http://localhost:4200). The Java server is also available at [http://localhost:8080](http://localhost:8080).

To use AI, set `OPENAI_API_KEY` before running the command.

**Windows PowerShell:**

```powershell
$env:OPENAI_API_KEY = "your-key"
docker compose up --build
```

**macOS or Linux:**

```bash
export OPENAI_API_KEY="your-key"
docker compose up --build
```

Stop the containers with `Ctrl+C`, then run `docker compose down`. Tasks are temporary and are lost when the Java server stops.

## How the code is organized

| Location | What it does |
| --- | --- |
| `frontend/src/app/app.html` | Shows the task form, task list, and AI suggestions. |
| `frontend/src/app/app.ts` | Handles button clicks, loading, saving, and errors on the page. |
| `frontend/src/app/app.css` | Controls the appearance of the page. |
| `frontend/src/app/todo-api.service.ts` | Sends task and AI requests from the website to the Java program. |
| `backend/todo-app/` | Starts the Java program and receives requests from the website. |
| `backend/todo-domain/` | Defines a task and its basic rules. |
| `backend/todo-application/` | Handles task actions and describes the information AI should return. |
| `backend/todo-infrastructure/` | Saves and reads tasks, and connects to OpenAI. |

The backend has three main request handlers:

- `HomeController` answers when you open the Java program's home address.
- `TodoController` handles adding, listing, changing, and removing tasks.
- `TodoAiController` handles requests for AI suggestions.

## How a task is saved

When you add, change, finish, or remove a task:

1. The page in `app.html` collects your input.
2. `app.ts` handles your action.
3. `TodoApiService` sends it to the Java program.
4. `TodoController` receives it, and `TodoService` carries out the requested task action.
5. The storage code saves or reads the task.
6. The Java program returns the result, and the website updates the list.

The task is stored in memory, not in a permanent file or database. It is lost when the Java program stops.

When running locally, `frontend/proxy.conf.json` forwards website requests to the Java program. With Docker, `frontend/nginx.conf` sends those requests between the two containers.

## How AI suggestions are made

1. You select **Give me some ideas**.
2. `TodoApiService` asks `TodoAiController` for suggestions.
3. `TodoAiController` gets the current task list and passes it to `SpringAiTodoAssistant`.
4. `SpringAiTodoAssistant` sends the task titles, descriptions, and done status to OpenAI.
5. The answer comes back to the website as a summary and up to three suggestions.

If there are no tasks, the Java program suggests creating one without contacting OpenAI. For other AI requests, a valid OpenAI key is required. Do not use the AI helper with task details you do not want to share with OpenAI.

For a fuller explanation, see [How it works](./docs/how-it-works.md).

## If something does not work

- **The website cannot load tasks:** Make sure the Java program is still running in its terminal.
- **Port 8080 or 4200 is already in use:** Stop the other program using that port, then try again.
- **AI suggestions show an error:** Check the OpenAI key in the Java program's terminal, then restart the Java program.
- **Tasks disappear after restarting:** This version does not keep tasks after the Java program stops.
- **`npm ci` fails:** Run it from inside the `frontend` folder and check your Node.js and npm versions.

## More help

- [Website guide](./frontend/README.md)
- [Java program guide](./backend/README.md)
- [How the parts work together](./docs/how-it-works.md)
- [Project skill for AI coding assistants](./.github/skills/todo-flow/SKILL.md)
