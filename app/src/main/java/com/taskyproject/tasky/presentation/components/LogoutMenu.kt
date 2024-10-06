package com.taskyproject.tasky.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import com.taskyproject.tasky.domain.model.MenuItem
import com.taskyproject.tasky.ui.theme.CalendarGray
import com.taskyproject.tasky.ui.theme.LocalDimensions
import com.taskyproject.tasky.ui.theme.MediumGray
import com.taskyproject.tasky.ui.theme.Typography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogoutMenu(
    isExpanded: Boolean,
    menuItems: List<MenuItem>,
    onExpandChange: () -> Unit,
    userInitials: String
) {
    val dimensions = LocalDimensions.current
    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = {
            onExpandChange()
        }
    ) {
        Box(
            modifier = Modifier
                .menuAnchor()
                .clip(CircleShape)
                .background(MediumGray)
                .size(dimensions.size34),
            contentAlignment = Alignment.Center
        ) {
            Text(text = userInitials, color = CalendarGray, style = Typography.bodyMedium, fontWeight = FontWeight.SemiBold)
        }
        DropdownMenu(expanded = isExpanded, onDismissRequest = {
            onExpandChange()
        }, offset = DpOffset(x = dimensions.default, y = dimensions.size8)) {
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