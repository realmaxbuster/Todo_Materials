package com.raywenderlich.android.todo.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.raywenderlich.android.todo.R
import com.raywenderlich.android.todo.data.model.TodoItem
import com.raywenderlich.android.todo.ui.theme.Typography
import com.raywenderlich.android.todo.viewmodel.TodoListViewModel
import kotlin.math.roundToInt

@Composable
fun TodoListComposable(todoListViewModel: TodoListViewModel, navController: NavController) {
  val itemList by todoListViewModel.observeTodos().collectAsState(initial = emptyList())

  Box(modifier = Modifier.fillMaxSize()) {
    TodoList(itemList, todoListViewModel, navController)
    FloatingActionButton(
        modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(16.dp),
        onClick = { navController.navigate("editor") }) {
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
  LazyColumn(modifier = Modifier.padding(16.dp), content = {
    items(items, key = { it.id }) {
      TodoListItem(it, todoListViewModel, navController)
    }
  })
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TodoListItem(item: TodoItem, todoListViewModel: TodoListViewModel,
    navController: NavController) {
  val swipeableState = rememberSwipeableState(0)
  val sizePx = with(LocalDensity.current) { 30.dp.toPx() }
  val anchors = mapOf(0f to 0, sizePx to 1) // Maps anchor points (in px) to states

  Box(
      modifier = Modifier
          .fillMaxWidth()
          .swipeable(
              state = swipeableState,
              anchors = anchors,
              thresholds = { _, _ -> FractionalThreshold(0.5f) },
              orientation = Orientation.Horizontal
          )
  ) {
    IconButton(onClick = {
      todoListViewModel.removeTodo(item)
    },
        modifier = Modifier
            .size(30.dp)
            .align(Alignment.CenterStart)) {
      Icon(
          painterResource(id = R.drawable.ic_baseline_delete_outline_24),
          modifier = Modifier
              .size(30.dp)
              .align(Alignment.CenterStart),
          contentDescription = null,
          tint = Color.Red
      )
    }
    Row(
        Modifier
            .fillMaxWidth()
            .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
            .pointerInput(Unit) {
              detectTapGestures(
                  onTap = { navController.navigate("editor?item=${item.id}") },
                  onDoubleTap = { todoListViewModel.toggleStarred(item) })
            }
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