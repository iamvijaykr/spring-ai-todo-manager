# Website guide

The website is the part you open in your browser. It lets you add, change, finish, filter, and remove tasks, and request AI suggestions.

## Start the website

Start the Java program first by following the [Java program guide](../backend/README.md). Then open a second terminal, move into this folder, and run:

```bash
npm ci
npm start
```

Open [http://localhost:4200](http://localhost:4200). Keep both terminals open while you use the app.

The website sends requests to the Java program on port `8080` automatically.

## Run both parts with Docker

From the main project folder, run:

```bash
docker compose up --build
```

Open [http://localhost:4200](http://localhost:4200). Docker sends website requests to the Java program automatically. To use AI, set `OPENAI_API_KEY` before starting Docker. See the [main guide](../README.md#run-both-parts-with-docker) for examples.

## Change the website

- `src/app/app.html` contains the words and layout.
- `src/app/app.css` and `src/styles.css` control how it looks.
- `src/app/app.ts` controls what the page does.
- `src/app/todo-api.service.ts` sends task and AI requests to the Java program.
- `src/app/app.spec.ts` contains the website check.

## Check your changes

Build the website:

```bash
npm run build
```

Run the website check:

```bash
npm test
```

The check opens Chrome, so Chrome must be installed.
