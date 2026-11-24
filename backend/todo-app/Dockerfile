FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /workspace
COPY pom.xml .
COPY backend ./backend

RUN mvn -B -pl backend/todo-app -am package

FROM eclipse-temurin:21-jre-alpine

RUN addgroup -S app && adduser -S -u 10001 -G app app
WORKDIR /app
COPY --from=build --chown=app:app \
    /workspace/backend/todo-app/target/todo-app-0.0.1-SNAPSHOT.jar \
    /app/todo-app.jar

USER app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/todo-app.jar"]
