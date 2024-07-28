package com.taskyproject.tasky.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.taskyproject.tasky.ui.theme.Gray
import com.taskyproject.tasky.ui.theme.LightGray
import com.taskyproject.tasky.ui.theme.LocalDimensions
import com.taskyproject.tasky.ui.theme.MediumGray
import com.taskyproject.tasky.ui.theme.TaskyTheme

@Composable
fun TextArea(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    isSingleLine: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    val dimensions = LocalDimensions.current
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholder,
                style = MaterialTheme.typography.labelMedium,
                color = MediumGray
            )
        },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
            disabledTextColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        singleLine = isSingleLine,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        visualTransformation = visualTransformation,
        modifier = modifier
            .padding(
                start = dimensions.size16,
                end = dimensions.size16
            )
            .fillMaxWidth()
    )
}

@Composable
@Preview(showBackground = true)
private fun TextAreaPreview() {
    TaskyTheme {
        TextArea(
            value = "kerimnuhanovic18@gmail.com",
            onValueChange = {},
            placeholder = "Email Address"
        )
    }
}