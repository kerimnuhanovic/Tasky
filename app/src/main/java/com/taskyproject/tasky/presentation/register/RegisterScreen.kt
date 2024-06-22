package com.taskyproject.tasky.presentation.register

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.taskyproject.tasky.R
import com.taskyproject.tasky.presentation.components.InputField
import com.taskyproject.tasky.presentation.components.TaskyButton
import com.taskyproject.tasky.presentation.util.UiEvent
import com.taskyproject.tasky.ui.theme.Green
import com.taskyproject.tasky.ui.theme.LocalDimensions
import com.taskyproject.tasky.ui.theme.PrimaryBlue
import com.taskyproject.tasky.ui.theme.TaskyTheme

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    onNavigate: (UiEvent.Navigate) -> Unit,
    onNavigateBack: () -> Unit
) {
    val state = viewModel.state.collectAsState().value
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            if (event is UiEvent.Navigate) {
                onNavigate(event)
            }
            else if (event is UiEvent.NavigateBack) {
                onNavigateBack()
            }
            else if (event is UiEvent.ShowToast) {
                Toast.makeText(context, context.getString(event.message.valueId), Toast.LENGTH_SHORT).show()
            }
        }
    }
    RegisterScreenContent(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@Composable
private fun RegisterScreenContent(
    state: RegisterState,
    onEvent: (RegisterEvent) -> Unit
) {
    val dimensions = LocalDimensions.current
    val scrollState = rememberScrollState()

    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(PrimaryBlue)
                .padding(it)
                .verticalScroll(scrollState)
        ) {
            Text(
                text = stringResource(id = R.string.create_your_account),
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = dimensions.size77)
            )
            Surface(
                color = Color.White,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(topEnd = dimensions.size30, topStart = dimensions.size30)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Spacer(modifier = Modifier.height(dimensions.size50))
                    InputField(
                        value = state.name,
                        onValueChange = { name ->
                            onEvent(RegisterEvent.OnNameEnter(name))
                        },
                        placeholder = stringResource(id = R.string.name),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Next
                        ),
                        trailingIconId = if (state.isNameValid) R.drawable.checkmark else null,
                        iconTint = Green
                    )
                    if (state.registrationErrors.shouldDisplayNameError) {
                        Text(
                            text = stringResource(id = R.string.full_name_error),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = dimensions.size16,
                                    end = dimensions.size16,
                                    top = dimensions.size4
                                )
                        )
                    }
                    Spacer(modifier = Modifier.height(dimensions.size16))
                    InputField(
                        value = state.email,
                        onValueChange = { email ->
                            onEvent(RegisterEvent.OnEmailEnter(email))
                        },
                        placeholder = stringResource(id = R.string.email_address),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Next
                        ),
                        trailingIconId = if (state.isEmailValid) R.drawable.checkmark else null,
                        iconTint = Green
                    )
                    if (state.registrationErrors.shouldDisplayEmailError) {
                        Text(
                            text = stringResource(id = R.string.invalid_email),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = dimensions.size16,
                                    end = dimensions.size16,
                                    top = dimensions.size4
                                )
                        )
                    }
                    Spacer(modifier = Modifier.height(dimensions.size16))
                    InputField(
                        value = state.password,
                        onValueChange = { password ->
                            onEvent(RegisterEvent.OnPasswordEnter(password))
                        },
                        placeholder = stringResource(id = R.string.password),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Password
                        ),
                        visualTransformation = if (!state.isPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None,
                        trailingIconId = if (!state.isPasswordVisible) R.drawable.visibility_off_24 else R.drawable.visibility_24,
                        onTrailingIconClick = {
                            onEvent(RegisterEvent.OnPasswordIconClick)
                        }
                    )
                    if (state.registrationErrors.shouldDisplayPasswordError) {
                        Text(
                            text = stringResource(id = R.string.invalid_password),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = dimensions.size16,
                                    end = dimensions.size16,
                                    top = dimensions.size4
                                )
                        )
                    }
                    Spacer(modifier = Modifier.height(dimensions.size32))
                    TaskyButton(
                        content = {
                            if (state.isLoading) {
                                CircularProgressIndicator(
                                    color = Color.White
                                )
                            } else {
                                Text(
                                    text = stringResource(id = R.string.get_started),
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        },
                        backgroundColor = PrimaryBlue,
                        contentColor = Color.White,
                        shape = CircleShape,
                        onButtonClick = {
                            onEvent(RegisterEvent.OnRegisterClick)
                        },
                        enabled = !state.isLoading,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = dimensions.size16)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = dimensions.size16, bottom = dimensions.size50)
                    ) {
                        IconButton(
                            onClick = {
                                      onEvent(RegisterEvent.OnBackClick)
                            },
                            modifier = Modifier
                                .clip(
                                    RoundedCornerShape(dimensions.size16)
                                )
                                .background(PrimaryBlue)
                        ) {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowLeft,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview
private fun RegisterScreenPreview() {
    TaskyTheme {
        RegisterScreenContent(
            state = RegisterState(),
            onEvent = {}
        )
    }
}