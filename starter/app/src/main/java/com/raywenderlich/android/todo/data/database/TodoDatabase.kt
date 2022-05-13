package com.raywenderlich.android.todo.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.raywenderlich.android.todo.data.model.TodoItem

@Database(entities = [TodoItem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

  companion object {

    const val DATABASE_NAME = "todo-database"
  }

  abstract fun todoDao(): TodoDao
}