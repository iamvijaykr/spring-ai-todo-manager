package com.example.todo.application;

import com.example.todo.domain.Todo;

import java.util.List;
import java.util.UUID;

public class TodoService {
    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public Todo create(String title, String description, boolean completed) {
        return todoRepository.save(Todo.create(title, description, completed));
    }

    public Todo update(UUID id, String title, String description, boolean completed) {
        Todo existing = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException(id));
        return todoRepository.save(existing.update(title, description, completed));
    }

    public Todo get(UUID id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException(id));
    }

    public List<Todo> getAll() {
        return todoRepository.findAll();
    }

    public void delete(UUID id) {
        if (todoRepository.findById(id).isEmpty()) {
            throw new TodoNotFoundException(id);
        }
        todoRepository.deleteById(id);
    }
}
