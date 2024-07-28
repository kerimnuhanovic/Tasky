package com.taskyproject.tasky.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.taskyproject.tasky.domain.model.Option
import com.taskyproject.tasky.ui.theme.TaskyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dropdown(
    isExpanded: Boolean,
    selectedItem: Option,
    options: List<Option>,
    onExpandChange: () -> Unit,
    onItemSelect: (Option) -> Unit,
    modifier: Modifier = Modifier
) {
    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = {
            onExpandChange()
        }
    ) {
        TextField(
            modifier = modifier
                .menuAnchor(),
            value = selectedItem.label,
            onValueChange = {},
            readOnly = true,
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            textStyle = MaterialTheme.typography.bodyMedium,
        )
        DropdownMenu(expanded = isExpanded, onDismissRequest = { onExpandChange() }) {
            options.map {
                DropdownMenuItem(
                    text = {
                        Text(
                            text = it.label,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                    onClick = {
                        onItemSelect(it)
                    }
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun DropdownPreview() {
    TaskyTheme {
        Dropdown(
            isExpanded = true,
            selectedItem = Option("30", "30 minutes before"),
            options = listOf(
                Option("Option 1", "Option 1"),
                Option("Option 2", "Option 2"),
                Option("Option 3", "Option 3")
            ),
            onExpandChange = {},
            onItemSelect = {}
        )
    }
}

