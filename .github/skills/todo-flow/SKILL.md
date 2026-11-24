---
name: todo-flow
description: Work on the Todo Flow Angular website, Java server, AI helper, tests, and project guides.
---

# Todo Flow project skill

Use this guide when changing Todo Flow. The project is split into a website and a Java server.

## Find the right place

- `frontend/` contains the Angular website.
- `backend/todo-app/` starts the Java server and handles website requests.
- `backend/todo-domain/` describes a task and its basic rules.
- `backend/todo-application/` handles task actions.
- `backend/todo-infrastructure/` saves tasks and connects to OpenAI.
- `docs/how-it-works.md` explains how the parts work together.

For website changes, start with `frontend/src/app/app.ts`, `app.html`, and `app.css`. Requests to the server are sent by `frontend/src/app/todo-api.service.ts`.

For server changes, start in `backend/todo-app/src/main/java/com/example/todo/api/`. Keep task rules and storage in their existing backend folders rather than putting everything in the web request handlers.

## Keep in mind

- Keep the website and server as separate parts. The website reaches the server through `frontend/proxy.conf.json` during local development.
- Check task input in the server, even if the website also checks it.
- The task store is temporary. Tasks are lost when the server stops.
- AI suggestions send task titles, descriptions, and completion states to OpenAI. Keep the OpenAI key in the `OPENAI_API_KEY` environment variable; never add a real key to code or documentation.
- Update the relevant README when run steps or user-visible behavior changes. Use plain language and include commands people can copy.

## Run and check changes

From the project folder, build and test the Java server:

```bash
mvn -pl backend/todo-app -am verify
```

From the `frontend/` folder, build the website:

```bash
npm ci
npm run build
```

Run website tests with `npm test`. Chrome must be installed for these tests.

To try the whole app, start the server as described in the main `README.md`, then run `npm start` from `frontend/`. Open `http://localhost:4200`; the server uses `http://localhost:8080`.
