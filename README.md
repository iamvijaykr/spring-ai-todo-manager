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

## What happens behind the scenes

When you add or change a task, the website sends it to the Java program, which saves it and sends the result back. When you ask for AI suggestions, the program sends task titles, descriptions, and completion states to OpenAI, then shows the reply on the website.

Do not use the AI helper with task details you do not want to share with OpenAI. Tasks are stored temporarily and are cleared when the Java program stops.

See [How it works](./docs/how-it-works.md) for a step-by-step explanation.

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
