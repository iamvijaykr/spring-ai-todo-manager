import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface Todo {
  id: string;
  title: string;
  description: string;
  completed: boolean;
  createdAt: string;
  updatedAt: string;
}

export interface TodoRequest {
  title: string;
  description: string;
  completed: boolean;
}

export interface TodoInsights {
  summary: string;
  suggestions: string[];
}

@Injectable({ providedIn: 'root' })
export class TodoApiService {
  private readonly todosUrl = '/api/todos';

  constructor(private readonly http: HttpClient) {}

  getTodos(): Observable<Todo[]> {
    return this.http.get<Todo[]>(this.todosUrl);
  }

  createTodo(request: TodoRequest): Observable<Todo> {
    return this.http.post<Todo>(this.todosUrl, request);
  }

  updateTodo(id: string, request: TodoRequest): Observable<Todo> {
    return this.http.put<Todo>(`${this.todosUrl}/${id}`, request);
  }

  deleteTodo(id: string): Observable<void> {
    return this.http.delete<void>(`${this.todosUrl}/${id}`);
  }

  getInsights(): Observable<TodoInsights> {
    return this.http.post<TodoInsights>('/api/ai/todos/insights', null);
  }
}
