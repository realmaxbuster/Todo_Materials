package com.raywenderlich.android.todo.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.raywenderlich.android.todo.R
import com.raywenderlich.android.todo.data.model.TodoItem
import com.raywenderlich.android.todo.ui.theme.Typography
import com.raywenderlich.android.todo.viewmodel.TodoListViewModel

@Composable
fun TodoListComposable(todoListViewModel: TodoListViewModel, navController: NavController) {
  val itemList by todoListViewModel.observeTodos().collectAsState(initial = emptyList())

  Box(modifier = Modifier.fillMaxSize()) {
    TodoList(itemList, todoListViewModel, navController)
    FloatingActionButton(
        modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(16.dp),
        onClick = { }) {
      Icon(
          painterResource(id = R.drawable.ic_baseline_add_24),
          contentDescription = null,
      )
    }
  }
}

@Composable
fun TodoList(
    items: List<TodoItem>,
    todoListViewModel: TodoListViewModel,
    navController: NavController
) {
  Column(modifier = Modifier.padding(16.dp), content = {
    items.forEach {
      TodoListItem(it, todoListViewModel, navController)
    }
  })
}

@Composable
fun TodoListItem(
    item: TodoItem,
    todoListViewModel: TodoListViewModel,
    navController: NavController
) {
  Box(
      modifier = Modifier.fillMaxWidth()
  ) {
    Row(
        Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
      Column(Modifier.fillMaxWidth(.8f)) {
        Text(
            text = item.title,
            style = Typography.h6,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = item.content,
            style = Typography.body1,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
      }
      if (item.starred) {
        Text(text = "⭐", modifier = Modifier.wrapContentWidth())
      }
    }
    Divider()
  }
}