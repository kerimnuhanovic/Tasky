package com.taskyproject.tasky.presentation.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.taskyproject.tasky.ui.theme.LocalDimensions
import com.taskyproject.tasky.ui.theme.MediumGray
import com.taskyproject.tasky.ui.theme.PrimaryBlue
import com.taskyproject.tasky.ui.theme.TaskyTheme

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onNavigate: (UiEvent.Navigate) -> Unit
) {
    val state = viewModel.state.collectAsState().value

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            if (event is UiEvent.Navigate) {
                onNavigate(event)
            }
        }
    }

    LoginScreenContent(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@Composable
private fun LoginScreenContent(
    state: LoginState,
    onEvent: (LoginEvent) -> Unit
) {
    val dimensions = LocalDimensions.current
    val scrollState = rememberScrollState()

    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(PrimaryBlue)
                .verticalScroll(scrollState)
        ) {
            Text(
                text = stringResource(id = R.string.welcome_back),
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = dimensions.size77)
            )
            Surface(
                color = Color.White,
                modifier = Modifier.weight(1f).fillMaxWidth(),
                shape = RoundedCornerShape(topEnd = dimensions.size30, topStart = dimensions.size30)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Spacer(modifier = Modifier.height(dimensions.size50))
                    InputField(
                        value = state.email,
                        onValueChange = { email ->
                            onEvent(LoginEvent.OnEmailEnter(email))
                        },
                        placeholder = stringResource(id = R.string.email_address),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Next
                        )
                    )
                    Spacer(modifier = Modifier.height(dimensions.size16))
                    InputField(
                        value = state.password,
                        onValueChange = { password ->
                            onEvent(LoginEvent.OnPasswordEnter(password))
                        },
                        placeholder = stringResource(id = R.string.password),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Password
                        ),
                        visualTransformation = if (!state.isPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None,
                        trailingIconId = if (!state.isPasswordVisible) R.drawable.visibility_off_24 else R.drawable.visibility_24,
                        onTrailingIconClick = {
                            onEvent(LoginEvent.OnPasswordIconClick)
                        }
                    )
                    Spacer(modifier = Modifier.height(dimensions.size32))
                    TaskyButton(
                        content = {
                            if (state.isLoading) {
                                CircularProgressIndicator(
                                    color = Color.White
                                )
                            }
                            else {
                                Text(
                                    text = stringResource(id = R.string.login),
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        },
                        backgroundColor = PrimaryBlue,
                        contentColor = Color.White,
                        shape = CircleShape,
                        onButtonClick = {
                            onEvent(LoginEvent.OnLoginClick)
                        },
                        enabled = !state.isLoading,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = dimensions.size16)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Row(modifier = Modifier.padding(bottom = dimensions.size16)) {
                        Text(
                            text = stringResource(id = R.string.dont_have_an_account),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                            color = MediumGray
                        )
                        Spacer(modifier = Modifier.width(dimensions.size4))
                        Text(
                            text = stringResource(id = R.string.sign_up),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                            color = PrimaryBlue,
                            modifier = Modifier.clickable {
                                onEvent(LoginEvent.OnSignUpClick)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview
private fun LoginScreenPreview() {
    TaskyTheme {
        LoginScreenContent(
            state = LoginState(),
            onEvent = {}
        )
    }
}

