# How Todo Flow works

Todo Flow has a website and a Java server. They run separately on your computer, and the website sends requests to the server.

## A normal task change

1. You add, edit, finish, or remove a task on the website.
2. The website code in `frontend/src/app/todo-api.service.ts` sends that change to the server.
3. During local development, `frontend/proxy.conf.json` forwards the request to port `8080`.
4. The server code in `backend/todo-app` checks the request and passes it to the task actions in `backend/todo-application`.
5. The storage code in `backend/todo-infrastructure` saves or reads the task in H2.
6. The server sends the result back to the website, which updates the page.

The task data shape and its basic rules are in `backend/todo-domain`.

## An AI request

1. You select **Give me some ideas**.
2. The website sends `POST /api/ai/todos/insights`.
3. `TodoAiController` gets the current task list and passes it to `TodoAiAssistant`.
4. `SpringAiTodoAssistant` prepares the task data and asks OpenAI for a summary and up to three suggestions.
5. The server sends the answer back, and the website displays it.

The AI helper needs a valid `OPENAI_API_KEY` when the task list has tasks. Without a key, ordinary task changes still work. Task titles, descriptions, and completion states are sent to OpenAI when you request AI help.

## Where to make changes

| If you want to change... | Start here |
| --- | --- |
| Page layout or words | `frontend/src/app/app.html` |
| Page appearance | `frontend/src/app/app.css` and `frontend/src/styles.css` |
| What the page does | `frontend/src/app/app.ts` |
| How the website talks to the server | `frontend/src/app/todo-api.service.ts` |
| Task web addresses | `backend/todo-app/src/main/java/com/example/todo/api/` |
| Task rules | `backend/todo-domain/src/main/java/com/example/todo/domain/Todo.java` |
| Task actions | `backend/todo-application/src/main/java/com/example/todo/application/` |
| Database or OpenAI connection | `backend/todo-infrastructure/src/main/java/com/example/todo/infrastructure/` |

## Data and privacy

Tasks are held in a temporary database and are lost when the server stops. The AI request sends task titles, descriptions, and completion states to OpenAI. Do not use the AI helper with task details you need to keep private.
