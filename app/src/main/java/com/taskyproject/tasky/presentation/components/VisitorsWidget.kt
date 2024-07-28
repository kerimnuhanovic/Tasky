package com.taskyproject.tasky.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.taskyproject.tasky.domain.model.VisitorOption
import com.taskyproject.tasky.ui.theme.LightGray
import com.taskyproject.tasky.ui.theme.LocalDimensions
import com.taskyproject.tasky.ui.theme.TaskyTheme

@Composable
fun VisitorsWidget(
    onClick: () -> Unit,
    visitorOption: VisitorOption,
    isSelected: Boolean,
    modifier: Modifier = Modifier
) {
    val dimensions = LocalDimensions.current
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .width(dimensions.size100)
            .height(dimensions.size30)
            .clip(RoundedCornerShape(dimensions.size100))
            .background(if (isSelected) Color.Black else LightGray)
            .clickable {
                onClick()
            }
    ) {
        Text(
            text = visitorOption.label,
            style = MaterialTheme.typography.bodyMedium,
            color = if (isSelected) Color.White else Color.Black,
        )
    }
}

@Composable
@Preview
private fun VisitorsWidgetPreview() {
    TaskyTheme {
        VisitorsWidget(
            onClick = {},
            isSelected = true,
            visitorOption = VisitorOption.NotGoing
        )
    }
}