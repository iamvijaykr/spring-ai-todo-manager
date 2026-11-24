# How Todo Flow works

Todo Flow has a website and a Java program. The website is what you see; the Java program saves the tasks and handles requests from the website.

You can run these parts directly on your computer or start them together with Docker. With Docker, the website and Java program run in separate containers and Docker connects them.

## When you add or change a task

1. You enter a task or click a button on the website.
2. The website sends the change to the Java program.
3. The Java program checks the task and saves it.
4. The Java program sends the saved task back to the website.
5. The website updates the list on your screen.

The task is saved in a temporary store. It is lost when the Java program stops.

## When you ask for AI suggestions

1. You click **Give me some ideas** on the website.
2. The website asks the Java program for suggestions.
3. The Java program sends the task titles, descriptions, and done status to OpenAI.
4. OpenAI returns a short summary and up to three suggestions.
5. The website shows the answer.

You need an OpenAI key for this feature. Do not send private task details to the AI helper.

## Where to look when changing something

| Change you want to make | Start in this file or folder |
| --- | --- |
| Change the words or layout | `frontend/src/app/app.html` |
| Change the page appearance | `frontend/src/app/app.css` or `frontend/src/styles.css` |
| Change how the page behaves | `frontend/src/app/app.ts` |
| Change how the website sends tasks | `frontend/src/app/todo-api.service.ts` |
| Change the web addresses | `backend/todo-app/src/main/java/com/example/todo/api/` |
| Change the rules for a task | `backend/todo-domain/src/main/java/com/example/todo/domain/Todo.java` |
| Change task actions | `backend/todo-application/src/main/java/com/example/todo/application/` |
| Change task storage or the AI connection | `backend/todo-infrastructure/src/main/java/com/example/todo/infrastructure/` |
