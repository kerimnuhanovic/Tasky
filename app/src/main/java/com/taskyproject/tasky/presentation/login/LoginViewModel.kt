package com.taskyproject.tasky.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taskyproject.tasky.domain.usecase.LoginUseCase
import com.taskyproject.tasky.domain.usecase.StoreUserSessionInfoUseCase
import com.taskyproject.tasky.domain.util.Result
import com.taskyproject.tasky.navigation.Route
import com.taskyproject.tasky.presentation.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val storeUserSessionInfoUseCase: StoreUserSessionInfoUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<LoginState> = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnEmailEnter -> {
                _state.value = state.value.copy(
                    email = event.email
                )
            }
            is LoginEvent.OnPasswordEnter -> {
                _state.value = state.value.copy(
                    password = event.password
                )
            }

            LoginEvent.OnPasswordIconClick -> {
                _state.value = state.value.copy(
                    isPasswordVisible = !state.value.isPasswordVisible
                )
            }

            LoginEvent.OnLoginClick -> {
                _state.value = state.value.copy(
                    isLoading = true
                )
                viewModelScope.launch(Dispatchers.IO) {
                    when (val result = loginUseCase(state.value.email, state.value.password)) {
                        is Result.Success -> {
                            _state.value = _state.value.copy(
                                isLoading = false
                            )
                            storeUserSessionInfoUseCase(
                                accessToken = result.data.accessToken,
                                userId = result.data.userId,
                                fullName = result.data.fullName,
                                accessTokenExpiration = result.data.accessTokenExpiration
                            )
                            _uiEvent.send(UiEvent.Navigate(Route.EventList))
                        }
                        is Result.Failure -> {
                        }
                    }
                }
            }

            LoginEvent.OnSignUpClick -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.Navigate(Route.Register))
                }
            }
        }
    }
}