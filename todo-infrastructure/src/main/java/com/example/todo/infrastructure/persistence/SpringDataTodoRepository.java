package com.example.todo.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataTodoRepository extends JpaRepository<TodoEntity, UUID> {
}
