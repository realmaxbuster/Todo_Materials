package com.raywenderlich.android.todo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.raywenderlich.android.todo.data.model.TodoItem
import com.raywenderlich.android.todo.data.repository.Repository
import com.raywenderlich.android.todo.provider.DependencyProvider

class TodoEditorViewModel(application: Application) : AndroidViewModel(application) {

  private val repository: Repository = DependencyProvider(getApplication()).repository

  fun getTodo(id: Int): TodoItem? {
    return repository.getTodo(id)
  }

  fun saveTodo(title: String, content: String) {
    val todo = TodoItem(title = title, content = content)
    repository.addTodo(todo)
  }

  fun updateTodo(todo: TodoItem, title: String, content: String) {
    val todoUpdate = todo.copy(title = title, content = content)
    repository.update(todoUpdate)
  }
}