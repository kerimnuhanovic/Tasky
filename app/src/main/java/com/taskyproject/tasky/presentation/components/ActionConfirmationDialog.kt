package com.taskyproject.tasky.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.taskyproject.tasky.R
import com.taskyproject.tasky.ui.theme.InterFontFamily
import com.taskyproject.tasky.ui.theme.TaskyTheme

@Composable
fun ActionConfirmationDialog(
    titleId: Int,
    descriptionId: Int,
    icon: ImageVector? = null,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        icon = {
               if (icon != null) {
                   Icon(imageVector = icon, contentDescription = null, tint = MaterialTheme.colorScheme.onBackground)
               }
        },
        title = {
            Text(text = stringResource(id = titleId), fontFamily = InterFontFamily)
        },
        text = {
            Text(text = stringResource(id = descriptionId), fontFamily = InterFontFamily)
        },
        onDismissRequest = {
            onDismiss()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm()
                }
            ) {
                Text(stringResource(id = R.string.confirm))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismiss()
                }
            ) {
                Text(stringResource(id = R.string.dismiss))
            }
        }
    )
}

@Composable
@Preview
private fun ActionConfirmationDialogPreview() {
    TaskyTheme {
        ActionConfirmationDialog(
            titleId = R.string.delete,
            descriptionId = R.string.delete_description,
            icon = Icons.Outlined.Delete,
            onConfirm = {},
            onDismiss = {}
        )
    }
}