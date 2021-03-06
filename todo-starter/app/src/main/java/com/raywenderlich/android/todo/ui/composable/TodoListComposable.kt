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

package com.raywenderlich.android.todo.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
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
            onClick = {
                // TODO: Add click event
            }) {
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
    // TODO: Change to LazyColumn
    Column(modifier = Modifier.padding(16.dp), content = {
        items.forEach {
            TodoListItem(it, todoListViewModel, navController)
        }
    })
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TodoListItem(
    item: TodoItem,
    todoListViewModel: TodoListViewModel,
    navController: NavController
) {
    // TODO: Add swipe to dismiss state

    // TODO: Wrap with swipe to dismiss
    TodoListRowContent(item, todoListViewModel, navController)
}

@Composable
fun TodoListRowContent(
    item: TodoItem,
    todoListViewModel: TodoListViewModel,
    navController: NavController
) {
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            Modifier
                // TODO: Add clickable modifier
                // TODO: Add pointer input modifier
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
                Text(text = "???", modifier = Modifier.wrapContentWidth())
            }
        }
        Divider()
    }
}