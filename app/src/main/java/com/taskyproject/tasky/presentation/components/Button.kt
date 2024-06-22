package com.taskyproject.tasky.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.taskyproject.tasky.ui.theme.LocalDimensions

@Composable
fun TaskyButton(
    content: @Composable () -> Unit,
    backgroundColor: Color,
    contentColor: Color,
    shape: Shape,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    val dimensions = LocalDimensions.current
    Button(
        onClick = onButtonClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor
        ),
        modifier = modifier.height(dimensions.size56),
        shape = shape,
        enabled = enabled
    ) {
        Box(contentAlignment = Alignment.Center) {
            content()
        }
    }

}

@Composable
@Preview
private fun TaskyButtonPreview() {
    TaskyButton(
        content = {
            Text(
                text = "LOGIN",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold
            )
        },
        backgroundColor = Color.Black,
        contentColor = Color.White,
        shape = CircleShape,
        onButtonClick = {},
        modifier = Modifier.fillMaxWidth()
    )
}