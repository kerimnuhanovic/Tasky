package com.taskyproject.tasky.ui.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimensions(
    val loginHeaderBoxHeight: Dp = 121.dp,
    val size77: Dp = 77.dp,
    val size30: Dp = 30.dp,
    val size50: Dp = 50.dp,
    val size16: Dp = 16.dp,
    val size10: Dp = 10.dp,
    val size62: Dp = 62.dp,
    val size56: Dp = 56.dp,
    val size32: Dp = 32.dp,
    val size4: Dp = 4.dp
)

val LocalDimensions = compositionLocalOf { Dimensions() }