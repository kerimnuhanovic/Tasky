package com.taskyproject.tasky.presentation.components

import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.taskyproject.tasky.ui.theme.TaskyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerWithDialog(
    state: DatePickerState,
    onConfirmClick: () -> Unit,
    onDismiss: () -> Unit = {},
) {
    DatePickerDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(onClick = { onConfirmClick() }) {
                Text(
                    text = "OK",
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    ) {
        DatePicker(
            state = state
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
private fun DatePickerWithDialogPreview() {
    TaskyTheme {
        DatePickerWithDialog(
            state = rememberDatePickerState(),
            onConfirmClick = {}
        )
    }
}