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

package com.raywenderlich.android.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.raywenderlich.android.todo.ui.composable.TodoEditorComposable
import com.raywenderlich.android.todo.ui.composable.TodoListComposable
import com.raywenderlich.android.todo.ui.theme.TODOTheme
import com.raywenderlich.android.todo.viewmodel.TodoEditorViewModel
import com.raywenderlich.android.todo.viewmodel.TodoListViewModel

class MainActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    // Switch to AppTheme for displaying the activity
    setTheme(R.style.AppTheme)

    super.onCreate(savedInstanceState)

    setContent {
      TODOTheme {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = Destinations.LIST_ROUTE) {
          composable(Destinations.LIST_ROUTE) {
            val todoListViewModel: TodoListViewModel by viewModels()
            TodoListComposable(todoListViewModel, navController)
          }
          composable(
            "${Destinations.EDITOR_ROUTE}?${NavigationParameters.EDITOR_ITEM_KEY}={${
              NavigationParameters.EDITOR_ITEM_KEY
            }}",
            arguments = listOf(navArgument(NavigationParameters.EDITOR_ITEM_KEY) {
              type = NavType.IntType
              defaultValue = -1
            })
          ) { backStackEntry ->
            val todoEditorViewModel: TodoEditorViewModel by viewModels()
            TodoEditorComposable(
              todoEditorViewModel,
              navController,
              backStackEntry.arguments?.getInt(NavigationParameters.EDITOR_ITEM_KEY)
            )
          }
        }
      }
    }
  }
}