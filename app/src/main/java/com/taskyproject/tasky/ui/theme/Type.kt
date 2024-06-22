package com.taskyproject.tasky.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = InterFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp,
        lineHeight = 30.sp
    ),
    labelMedium = TextStyle(
        fontFamily = InterFontFamily,
        fontSize = 16.sp,
        lineHeight = 30.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = InterFontFamily,
        fontSize = 14.sp,
        lineHeight = 30.sp
    ),
    labelSmall = TextStyle(
        fontFamily = InterFontFamily,
        fontSize = 12.sp,
        lineHeight = 20.sp
    )
    /* Other default text styles to override
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)