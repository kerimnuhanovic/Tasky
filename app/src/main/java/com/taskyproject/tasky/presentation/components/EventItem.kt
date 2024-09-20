package com.taskyproject.tasky.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import com.taskyproject.tasky.R
import com.taskyproject.tasky.domain.model.Event
import com.taskyproject.tasky.domain.model.MenuItem
import com.taskyproject.tasky.presentation.util.millisToLocalDateTime
import com.taskyproject.tasky.presentation.util.millisToLocalTime
import com.taskyproject.tasky.ui.theme.LightRed
import com.taskyproject.tasky.ui.theme.LocalDimensions
import com.taskyproject.tasky.ui.theme.TaskyTheme

@Composable
fun EventItem(
    event: Event,
    isMenuOpen: Boolean,
    menuItems: List<MenuItem>,
    onExpandChange: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dimensions = LocalDimensions.current
    val currentDateTime = millisToLocalDateTime(System.currentTimeMillis())
    val eventDateTime = millisToLocalDateTime(event.to)
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(dimensions.size110),
        colors = CardDefaults.cardColors(containerColor = LightRed)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensions.size8)
        ) {
            Column {
                Row {
                    if (currentDateTime.isAfter(eventDateTime)) {
                        Icon(
                            imageVector = Icons.Outlined.CheckCircle,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                    Text(
                        text = event.title,
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textDecoration = if (currentDateTime.isAfter(eventDateTime)) TextDecoration.LineThrough else TextDecoration.None
                    )
                }
                Spacer(modifier = Modifier.height(dimensions.size8))
                Text(
                    text = event.description,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Normal,
                    color = Color.White
                )
            }
            Menu(
                isExpanded = isMenuOpen,
                menuItems = menuItems,
                onExpandChange = onExpandChange
            )

        }
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensions.size8), horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = millisToLocalTime(event.from).toString(),
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Normal,
                color = Color.White
            )
            Text(
                text = stringResource(id = R.string.dash),
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Normal,
                color = Color.White
            )
            Text(
                text = millisToLocalTime(event.to).toString(),
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Normal,
                color = Color.White
            )
        }
    }
}

@Preview
@Composable
private fun EventItemPreview() {
    TaskyTheme {
        EventItem(
            event = Event(
                id = "",
                isUserEventCreator = true,
                title = "Daily Meeting",
                description = "Manage Daily",
                from = 1725292800000,
                to = 1725295500000,
                host = "",
                remindAt = 1725209100000,
                photos = emptyList(),
                attendees = emptyList()
            ),
            isMenuOpen = false,
            menuItems = listOf(MenuItem(label = "Delete", onItemClick = {})),
            onExpandChange = {}
        )
    }
}