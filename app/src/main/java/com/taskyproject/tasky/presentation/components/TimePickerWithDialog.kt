package com.taskyproject.tasky.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.taskyproject.tasky.ui.theme.LocalDimensions
import com.taskyproject.tasky.ui.theme.TaskyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerWithDialog(
    title: String,
    state: TimePickerState,
    onConfirmClick: () -> Unit,
    onDismiss: () -> Unit = {}
) {
    val dimensions = LocalDimensions.current
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = dimensions.size4,
            modifier = Modifier
                .width(IntrinsicSize.Min)
                .height(IntrinsicSize.Min)
                .background(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = MaterialTheme.colorScheme.surface
                ),
        ) {
            Column(
                modifier = Modifier.padding(dimensions.size24),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = dimensions.size20),
                    text = title,
                    style = MaterialTheme.typography.labelMedium
                )
                TimePicker(state = state)
                Row(
                    modifier = Modifier
                        .height(dimensions.size40)
                        .fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    Button(
                        onClick = {
                            onConfirmClick()
                        }
                    ) {
                        Text(
                            text = "OK",
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
private fun TimePickerWithDialogPreview() {
    TaskyTheme {
        TimePickerWithDialog(
            title = "Select time",
            state = rememberTimePickerState(),
            onConfirmClick = { /*TODO*/ })
    }
}