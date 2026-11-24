package com.example.todo.api;

import com.example.todo.application.TodoAiAssistant;
import com.example.todo.application.TodoInsights;
import com.example.todo.application.TodoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ai/todos")
public class TodoAiController {
    private final TodoAiAssistant todoAiAssistant;
    private final TodoService todoService;

    public TodoAiController(TodoAiAssistant todoAiAssistant, TodoService todoService) {
        this.todoAiAssistant = todoAiAssistant;
        this.todoService = todoService;
    }

    @PostMapping("/insights")
    public TodoInsights generateInsights() {
        return todoAiAssistant.generateInsights(todoService.getAll());
    }
}
