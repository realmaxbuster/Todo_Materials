package com.raywenderlich.android.todo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.raywenderlich.android.todo.data.model.TodoItem
import com.raywenderlich.android.todo.data.repository.Repository
import com.raywenderlich.android.todo.provider.DependencyProvider
import kotlinx.coroutines.flow.Flow

class TodoListViewModel(application: Application) : AndroidViewModel(application) {

  private val repository: Repository = DependencyProvider(getApplication()).repository

  fun observeTodos(): Flow<List<TodoItem>> {
    return repository.observeAllTodos()
  }

  fun removeTodo(todo: TodoItem) {
    repository.removeTodo(todo)
  }

  fun toggleStarred(todo: TodoItem) {
    repository.toggleStarred(todo)
  }
}