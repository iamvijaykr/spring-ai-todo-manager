# Website guide

The Angular website is in this folder. It lets you add, edit, finish, filter, and remove tasks, and request a summary and suggestions from the server.

## Start the website on your computer

Start the Java server first by following the [server guide](../backend/README.md). Then open a second terminal in this folder and run:

```bash
npm ci
npm start
```

Open [http://localhost:4200](http://localhost:4200). Keep both the website and server terminals open while you use the app.

The website sends requests to `http://localhost:8080` through [`proxy.conf.json`](./proxy.conf.json). You do not need to set up a separate connection.

## Main files

- `src/app/app.ts`, `app.html`, and `app.css` control the page, its content, and its appearance.
- `src/app/todo-api.service.ts` sends task and AI requests to the Java server.
- `src/app/app.spec.ts` checks that the page loads.
- `src/styles.css` contains styles used across the page.

## Check the website

Build it:

```bash
npm run build
```

Run its tests:

```bash
npm test
```

The tests open Chrome, so install Chrome before running them.
