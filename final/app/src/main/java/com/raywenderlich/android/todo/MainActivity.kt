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
import com.raywenderlich.android.todo.ui.theme.TodoTheme
import com.raywenderlich.android.todo.viewmodel.TodoEditorViewModel
import com.raywenderlich.android.todo.viewmodel.TodoListViewModel

class MainActivity : ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      TodoTheme {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = "list") {
          composable("list") {
            val todoListViewModel: TodoListViewModel by viewModels()
            TodoListComposable(todoListViewModel, navController)
          }
          composable(
              "editor?item={item}",
              arguments = listOf(navArgument("item") {
                type = NavType.IntType
                defaultValue = -1
              })
          ) { backStackEntry ->
            val todoEditorViewModel: TodoEditorViewModel by viewModels()
            TodoEditorComposable(
                todoEditorViewModel,
                navController,
                backStackEntry.arguments?.getInt("item")
            )
          }
        }
      }
    }
  }
}