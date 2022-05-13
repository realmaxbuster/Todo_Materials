package com.raywenderlich.android.todo.data.database

import androidx.room.*
import com.raywenderlich.android.todo.data.model.TodoItem
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

  @Insert
  fun insert(item: TodoItem)

  @Query("SELECT * FROM todo")
  fun observeAll(): Flow<List<TodoItem>>

  @Query("SELECT * FROM todo WHERE id LIKE :id")
  fun get(id: Int): TodoItem?

  @Query("UPDATE todo SET starred = NOT starred WHERE id = :itemId")
  fun toggleStar(itemId: Int)

  @Update
  fun update(item: TodoItem)

  @Delete
  fun delete(item: TodoItem)
}