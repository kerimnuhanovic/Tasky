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
    val size4: Dp = 4.dp,
    val size80: Dp = 80.dp,
    val size20: Dp = 20.dp,
    val size110: Dp = 110.dp,
    val size60: Dp = 60.dp,
    val size1: Dp = 1.dp,
    val size2: Dp = 2.dp,
    val size8: Dp = 8.dp,
    val size100: Dp = 100.dp,
    val size46: Dp = 46.dp,
    val size24: Dp = 24.dp,
    val size6: Dp = 6.dp,
    val size40: Dp = 40.dp,
    val size300: Dp = 300.dp,
    val default: Dp = 0.dp
)

val LocalDimensions = compositionLocalOf { Dimensions() }