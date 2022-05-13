package com.raywenderlich.android.todo.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val LightColorPalette = lightColors(
    primary = PrimaryColor,
    primaryVariant = PrimaryDark,
    secondary = Accent
)

@Composable
fun TodoTheme(content: @Composable () -> Unit) {

  MaterialTheme(
      colors = LightColorPalette,
      typography = Typography,
      shapes = Shapes,
      content = content
  )
}