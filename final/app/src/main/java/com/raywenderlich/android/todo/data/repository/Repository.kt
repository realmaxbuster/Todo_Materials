package com.raywenderlich.android.todo.data.repository

import com.raywenderlich.android.todo.data.database.TodoDao
import com.raywenderlich.android.todo.data.model.TodoItem
import kotlinx.coroutines.flow.Flow

class Repository(private val todoDao: TodoDao) {

  fun addTodo(item: TodoItem) {
    todoDao.insert(item)
  }

  fun toggleStarred(todo: TodoItem) {
    todoDao.toggleStar(todo.id)
  }

  fun update(todo: TodoItem) {
    todoDao.update(todo)
  }

  fun removeTodo(item: TodoItem) {
    todoDao.delete(item)
  }

  fun observeAllTodos(): Flow<List<TodoItem>> {
    return todoDao.observeAll()
  }

  fun getTodo(id: Int): TodoItem? {
    return todoDao.get(id)
  }
}