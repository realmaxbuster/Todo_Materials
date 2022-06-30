/*
 * Copyright (c) 2022 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

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