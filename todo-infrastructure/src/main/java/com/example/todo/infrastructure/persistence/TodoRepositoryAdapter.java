package com.example.todo.infrastructure.persistence;

import com.example.todo.application.TodoRepository;
import com.example.todo.domain.Todo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class TodoRepositoryAdapter implements TodoRepository {
    private final SpringDataTodoRepository repository;

    public TodoRepositoryAdapter(SpringDataTodoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Todo save(Todo todo) {
        TodoEntity entity = new TodoEntity(
                todo.id(),
                todo.title(),
                todo.description(),
                todo.completed(),
                todo.createdAt(),
                todo.updatedAt());
        return toDomain(repository.save(entity));
    }

    @Override
    public Optional<Todo> findById(UUID id) {
        return repository.findById(id).map(TodoRepositoryAdapter::toDomain);
    }

    @Override
    public List<Todo> findAll() {
        return repository.findAll().stream()
                .map(TodoRepositoryAdapter::toDomain)
                .toList();
    }

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    private static Todo toDomain(TodoEntity entity) {
        return new Todo(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.isCompleted(),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }
}
