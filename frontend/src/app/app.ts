import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Todo, TodoApiService, TodoInsights, TodoRequest } from './todo-api.service';

type TodoFilter = 'all' | 'active' | 'completed';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [DatePipe, FormsModule],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App implements OnInit {
  readonly today = new Date();
  todos: Todo[] = [];
  filter: TodoFilter = 'all';
  loading = true;
  saving = false;
  aiLoading = false;
  error = '';
  aiError = '';
  insights: TodoInsights | null = null;
  newTitle = '';
  newDescription = '';
  editingId: string | null = null;
  editTitle = '';
  editDescription = '';

  constructor(private readonly todoApi: TodoApiService) {}

  ngOnInit(): void {
    this.loadTodos();
  }

  get visibleTodos(): Todo[] {
    if (this.filter === 'active') {
      return this.todos.filter((todo) => !todo.completed);
    }
    if (this.filter === 'completed') {
      return this.todos.filter((todo) => todo.completed);
    }
    return this.todos;
  }

  get completedCount(): number {
    return this.todos.filter((todo) => todo.completed).length;
  }

  get activeCount(): number {
    return this.todos.length - this.completedCount;
  }

  loadTodos(): void {
    this.loading = true;
    this.error = '';
    this.todoApi.getTodos().subscribe({
      next: (todos) => {
        this.todos = todos;
        this.loading = false;
      },
      error: () => {
        this.error = 'Could not load todos. Check that the Spring Boot API is running.';
        this.loading = false;
      }
    });
  }

  createTodo(): void {
    const request: TodoRequest = {
      title: this.newTitle.trim(),
      description: this.newDescription.trim(),
      completed: false
    };
    if (!request.title || this.saving) {
      return;
    }

    this.saving = true;
    this.error = '';
    this.todoApi.createTodo(request).subscribe({
      next: (todo) => {
        this.todos = [todo, ...this.todos];
        this.newTitle = '';
        this.newDescription = '';
        this.saving = false;
      },
      error: () => {
        this.error = 'Could not save this todo. Please try again.';
        this.saving = false;
      }
    });
  }

  setCompleted(todo: Todo, event: Event): void {
    const completed = (event.target as HTMLInputElement).checked;
    this.saveTodo({ ...todo, completed });
  }

  startEditing(todo: Todo): void {
    this.editingId = todo.id;
    this.editTitle = todo.title;
    this.editDescription = todo.description;
  }

  saveEdit(todo: Todo): void {
    const title = this.editTitle.trim();
    if (!title) {
      this.error = 'A todo title is required.';
      return;
    }
    this.saveTodo({ ...todo, title, description: this.editDescription.trim() });
    this.editingId = null;
  }

  cancelEditing(): void {
    this.editingId = null;
    this.error = '';
  }

  deleteTodo(todo: Todo): void {
    this.error = '';
    this.todoApi.deleteTodo(todo.id).subscribe({
      next: () => {
        this.todos = this.todos.filter((item) => item.id !== todo.id);
      },
      error: () => {
        this.error = 'Could not delete this todo. Please try again.';
      }
    });
  }

  generateInsights(): void {
    this.aiLoading = true;
    this.aiError = '';
    this.insights = null;
    this.todoApi.getInsights().subscribe({
      next: (insights) => {
        this.insights = insights;
        this.aiLoading = false;
      },
      error: () => {
        this.aiError = 'AI insights are unavailable. Set OPENAI_API_KEY for the Spring Boot app and try again.';
        this.aiLoading = false;
      }
    });
  }

  private saveTodo(updatedTodo: Todo): void {
    this.error = '';
    this.todoApi.updateTodo(updatedTodo.id, {
      title: updatedTodo.title,
      description: updatedTodo.description,
      completed: updatedTodo.completed
    }).subscribe({
      next: (savedTodo) => {
        this.todos = this.todos.map((todo) => todo.id === savedTodo.id ? savedTodo : todo);
      },
      error: () => {
        this.error = 'Could not update this todo. Please try again.';
      }
    });
  }
}
