package com.raywenderlich.android.todo.provider

import android.content.Context
import androidx.room.Room
import com.raywenderlich.android.todo.data.database.AppDatabase
import com.raywenderlich.android.todo.data.repository.Repository
import com.raywenderlich.android.todo.data.sampledata.SampleData
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking

class DependencyProvider(applicationContext: Context) {

  companion object {
    private var database: AppDatabase? = null
    private var repository: Repository? = null
  }

  val repository: Repository by lazy { provideRepository(provideDatabase(applicationContext)) }

  private fun provideDatabase(applicationContext: Context): AppDatabase {
    if (database == null) {
      database = Room.databaseBuilder(
          applicationContext,
          AppDatabase::class.java,
          AppDatabase.DATABASE_NAME
      ).allowMainThreadQueries().build()

      runBlocking {
        if (database?.todoDao()?.observeAll()?.firstOrNull().isNullOrEmpty()) {
          SampleData.TODO_LIST.forEach {
            database?.todoDao()?.insert(it)
          }
        }
      }
    }

    return database!!
  }

  private fun provideRepository(database: AppDatabase): Repository {
    if (Companion.repository == null) {
      val todoDao = database.todoDao()
      val repository = Repository(todoDao)
      Companion.repository = repository
    }

    return Companion.repository!!
  }
}