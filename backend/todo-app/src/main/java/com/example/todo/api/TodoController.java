package com.example.todo.api;

import com.example.todo.application.TodoService;
import com.example.todo.application.TodoNotFoundException;
import com.example.todo.domain.Todo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/todos")
public class TodoController {
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping
    public ResponseEntity<TodoResponse> create(@Valid @RequestBody TodoRequest request) {
        Todo todo = todoService.create(request.title(), request.description(), request.completed());
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(todo.id())
                .toUri();
        return ResponseEntity.created(location).body(TodoResponse.from(todo));
    }

    @GetMapping("/{id}")
    public TodoResponse get(@PathVariable UUID id) {
        return TodoResponse.from(todoService.get(id));
    }

    @GetMapping
    public List<TodoResponse> getAll() {
        return todoService.getAll().stream()
                .map(TodoResponse::from)
                .toList();
    }

    @PutMapping("/{id}")
    public TodoResponse update(@PathVariable UUID id, @Valid @RequestBody TodoRequest request) {
        Todo todo = todoService.update(id, request.title(), request.description(), request.completed());
        return TodoResponse.from(todo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        todoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(TodoNotFoundException.class)
    public ProblemDetail handleTodoNotFound(TodoNotFoundException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    public record TodoRequest(
            @NotBlank @Size(max = 255) String title,
            @Size(max = 2000) String description,
            boolean completed) {
    }

    public record TodoResponse(
            UUID id,
            String title,
            String description,
            boolean completed,
            Instant createdAt,
            Instant updatedAt) {
        static TodoResponse from(Todo todo) {
            return new TodoResponse(
                    todo.id(),
                    todo.title(),
                    todo.description(),
                    todo.completed(),
                    todo.createdAt(),
                    todo.updatedAt());
        }
    }
}
