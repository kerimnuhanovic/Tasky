package com.taskyproject.tasky.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.taskyproject.tasky.R
import com.taskyproject.tasky.ui.theme.DarkGray
import com.taskyproject.tasky.ui.theme.Gray
import com.taskyproject.tasky.ui.theme.LightBlue
import com.taskyproject.tasky.ui.theme.LightGray
import com.taskyproject.tasky.ui.theme.LocalDimensions
import com.taskyproject.tasky.ui.theme.TaskyTheme

@Composable
fun VisitorCard(
    fullName: String,
    isCreator: Boolean,
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit = {}
) {
    val dimensions = LocalDimensions.current
    val firstName = fullName.split(" ")[0]
    val lastName = fullName.split(" ")[1]
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .height(dimensions.size46)
            .background(LightGray)
            .padding(horizontal = dimensions.size8)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(dimensions.size32)
                .clip(CircleShape)
                .background(Gray)
        ) {
            Text(
                text = "${firstName[0]}${lastName[0]}",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.width(dimensions.size16))
        Text(
            text = fullName,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = DarkGray
        )
        Spacer(modifier = Modifier.weight(1f))
        if (!isCreator) {
            Icon(painter = painterResource(
                id = R.drawable.trash),
                contentDescription = null,
                tint = DarkGray,
                modifier = Modifier.clickable {
                    onDeleteClick()
                }
            )
        }
        else {
            Text(
                text = stringResource(id = R.string.creator),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = LightBlue
            )
        }
    }
}

@Composable
@Preview
private fun VisitorCardPreview() {
    TaskyTheme {
        VisitorCard(
            fullName = "Kerim Nuhanovic",
            isCreator = false
        )
    }
}