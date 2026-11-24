package com.example.todo.domain;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public record Todo(
        UUID id,
        String title,
        String description,
        boolean completed,
        Instant createdAt,
        Instant updatedAt) {

    public Todo {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(createdAt, "createdAt must not be null");
        Objects.requireNonNull(updatedAt, "updatedAt must not be null");
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("title must not be blank");
        }
        description = description == null ? "" : description;
    }

    public static Todo create(String title, String description, boolean completed) {
        Instant now = Instant.now();
        return new Todo(UUID.randomUUID(), title, description, completed, now, now);
    }

    public Todo update(String title, String description, boolean completed) {
        return new Todo(id, title, description, completed, createdAt, Instant.now());
    }
}
