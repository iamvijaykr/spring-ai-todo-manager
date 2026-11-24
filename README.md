# Spring AI Todo Manager

A Java 21 / Spring Boot 3 todo application scaffolded as a Maven multi-module project. It includes a REST CRUD API, Spring Data JPA with an in-memory H2 database, and the Spring AI OpenAI starter for future AI features.

## Modules

- `todo-domain` — core todo model and business rules.
- `todo-application` — use cases and repository port.
- `todo-infrastructure` — JPA persistence, H2, and Spring AI integration dependency.
- `todo-app` — executable Spring Boot application and REST API.

## Run

Start the application from the repository root:

```bash
mvn -pl todo-app -am package
java -jar todo-app/target/todo-app-0.0.1-SNAPSHOT.jar
```

The API is available at `http://localhost:8080/api/todos`.
Set `OPENAI_API_KEY` to a valid key before adding or calling OpenAI-backed features. A placeholder is used otherwise so the CRUD API can run without an AI key.

| Method | Path | Action |
| --- | --- | --- |
| `GET` | `/api/todos` | List todos |
| `GET` | `/api/todos/{id}` | Get a todo |
| `POST` | `/api/todos` | Create a todo |
| `PUT` | `/api/todos/{id}` | Update a todo |
| `DELETE` | `/api/todos/{id}` | Delete a todo |

Example request:

```json
{
  "title": "Plan the week",
  "description": "Review priorities and schedule tasks",
  "completed": false
}
```

The H2 console is available at `http://localhost:8080/h2-console` with JDBC URL `jdbc:h2:mem:tododb`, username `sa`, and an empty password.
