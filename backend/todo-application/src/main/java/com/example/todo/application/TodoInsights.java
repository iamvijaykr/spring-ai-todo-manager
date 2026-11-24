package com.example.todo.application;

import java.util.List;

public record TodoInsights(String summary, List<String> suggestions) {
    public TodoInsights {
        if (summary == null || summary.isBlank()) {
            throw new IllegalArgumentException("summary must not be blank");
        }
        suggestions = List.copyOf(suggestions);
    }
}
