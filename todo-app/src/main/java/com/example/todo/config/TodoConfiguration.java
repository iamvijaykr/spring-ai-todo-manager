package com.example.todo.config;

import com.example.todo.application.TodoRepository;
import com.example.todo.application.TodoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TodoConfiguration {
    @Bean
    TodoService todoService(TodoRepository todoRepository) {
        return new TodoService(todoRepository);
    }
}
