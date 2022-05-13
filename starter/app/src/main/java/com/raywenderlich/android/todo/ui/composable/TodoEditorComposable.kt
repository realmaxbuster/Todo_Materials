package com.raywenderlich.android.todo.ui.composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.raywenderlich.android.todo.R
import com.raywenderlich.android.todo.viewmodel.TodoEditorViewModel

@Composable
fun TodoEditorComposable(
    todoEditorViewModel: TodoEditorViewModel,
    navController: NavController,
    itemId: Int?
) {
  val todo = itemId?.let { todoEditorViewModel.getTodo(it) }

  val title = remember { mutableStateOf(todo?.title ?: "") }
  val content = remember { mutableStateOf(todo?.content ?: "") }

  Column(
      Modifier
          .fillMaxHeight()
          .padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = title.value,
        onValueChange = { newTitle -> title.value = newTitle },
        label = { Text(text = stringResource(id = R.string.title)) })
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = content.value,
        onValueChange = { newContent -> content.value = newContent },
        label = { Text(text = stringResource(id = R.string.content)) })
    Button(modifier = Modifier
        .fillMaxWidth()
        .height(50.dp), onClick = { }) {
      Text(text = stringResource(id = R.string.save))
    }
  }
}