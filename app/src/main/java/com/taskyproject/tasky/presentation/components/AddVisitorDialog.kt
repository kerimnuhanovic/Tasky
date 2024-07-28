package com.taskyproject.tasky.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.taskyproject.tasky.R
import com.taskyproject.tasky.ui.theme.LightRed
import com.taskyproject.tasky.ui.theme.LocalDimensions
import com.taskyproject.tasky.ui.theme.TaskyTheme

@Composable
fun AddVisitorDialog(
    value: String,
    onValueChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onExitClick: () -> Unit,
    errorMessage: String? = null,
    onDismiss: () -> Unit = {},
    isLoading: Boolean = false
) {
    val dimensions = LocalDimensions.current
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Surface(
            shape = MaterialTheme.shapes.small,
            tonalElevation = dimensions.size4,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensions.size16)
                .background(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = MaterialTheme.colorScheme.surface
                )
        ) {
            Column(
                modifier = Modifier.padding(vertical = dimensions.size24)
            ) {
                Row {
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        painter = painterResource(id = R.drawable.cancel_icon),
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier
                            .clickable {
                                onExitClick()
                            }
                            .padding(horizontal = dimensions.size16)
                    )
                }
                Spacer(modifier = Modifier.height(dimensions.size16))
                Text(
                    text = stringResource(id = R.string.add_visitors),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(dimensions.size30))
                InputField(
                    value = value, onValueChange = onValueChange, placeholder = stringResource(
                        id = R.string.email_address
                    ),
                    modifier = Modifier.padding(horizontal = dimensions.default)
                )
                Spacer(modifier = Modifier.height(dimensions.size8))
                if (errorMessage != null) {
                    Text(
                        text = errorMessage,
                        style = MaterialTheme.typography.bodyMedium,
                        color = LightRed,
                        modifier = Modifier.padding(horizontal = dimensions.size16)
                    )
                }
                Spacer(modifier = Modifier.height(dimensions.size30))
                TaskyButton(
                    content = {
                        if (isLoading) {
                            CircularProgressIndicator(
                                color = Color.White
                            )

                        } else {
                            Text(
                                text = stringResource(id = R.string.add),
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    },
                    backgroundColor = Color.Black,
                    contentColor = Color.White,
                    shape = CircleShape,
                    onButtonClick = { onSubmit() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = dimensions.size16),
                    enabled = !isLoading
                )
            }
        }
    }
}

@Composable
@Preview
private fun AddVisitorDialogPreview() {
    TaskyTheme {
        AddVisitorDialog(
            value = "",
            onValueChange = {},
            onSubmit = {},
            onExitClick = {},
            errorMessage = stringResource(id = R.string.not_existing_user)
        )
    }
}