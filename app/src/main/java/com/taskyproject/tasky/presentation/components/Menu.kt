package com.taskyproject.tasky.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.taskyproject.tasky.domain.model.MenuItem
import com.taskyproject.tasky.ui.theme.TaskyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Menu(
    isExpanded: Boolean,
    menuItems: List<MenuItem>,
    onExpandChange: () -> Unit,
) {
    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = {
            onExpandChange()
        }
    ) {
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .menuAnchor()
                .rotate(90f)
        )
        DropdownMenu(expanded = isExpanded, onDismissRequest = {
            onExpandChange()
        }) {
            menuItems.map {
                DropdownMenuItem(
                    text = {
                        Text(
                            text = it.label,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    onClick = {
                        it.onItemClick()
                    }
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun MenuPreview() {
    TaskyTheme {
        Menu(
            isExpanded = false,
            menuItems = listOf(),
            onExpandChange = {},
        )
    }
}

