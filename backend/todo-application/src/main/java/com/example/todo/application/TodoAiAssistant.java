package com.example.todo.application;

import com.example.todo.domain.Todo;

import java.util.List;

public interface TodoAiAssistant {
    TodoInsights generateInsights(List<Todo> todos);
}
