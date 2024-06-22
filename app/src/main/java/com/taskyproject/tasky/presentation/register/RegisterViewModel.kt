package com.taskyproject.tasky.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taskyproject.tasky.domain.model.RegistrationErrors
import com.taskyproject.tasky.domain.usecase.CheckIsEmailValid
import com.taskyproject.tasky.domain.usecase.CheckIsNameValid
import com.taskyproject.tasky.domain.usecase.CheckIsPasswordValid
import com.taskyproject.tasky.domain.usecase.RegisterUseCase
import com.taskyproject.tasky.domain.util.Result
import com.taskyproject.tasky.navigation.Route
import com.taskyproject.tasky.presentation.util.DELAY_1000
import com.taskyproject.tasky.presentation.util.ToastMessage
import com.taskyproject.tasky.presentation.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val checkIsNameValid: CheckIsNameValid,
    private val checkIsEmailValid: CheckIsEmailValid,
    private val checkIsPasswordValid: CheckIsPasswordValid
) : ViewModel() {
    private val _state = MutableStateFlow(RegisterState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: RegisterEvent) {
        when (event) {
            RegisterEvent.OnBackClick -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.NavigateBack)
                }
            }

            is RegisterEvent.OnEmailEnter -> {
                _state.value = _state.value.copy(
                    email = event.email,
                    isEmailValid = checkIsEmailValid(event.email)
                )
            }

            is RegisterEvent.OnNameEnter -> {
                _state.value = _state.value.copy(
                    name = event.name,
                    isNameValid = checkIsNameValid(event.name)
                )
            }

            is RegisterEvent.OnPasswordEnter -> {
                _state.value = _state.value.copy(
                    password = event.password
                )
            }

            RegisterEvent.OnPasswordIconClick -> {
                _state.value = _state.value.copy(
                    isPasswordVisible = !state.value.isPasswordVisible
                )
            }

            RegisterEvent.OnRegisterClick -> {
                registerUser()
            }
        }
    }

    private fun registerUser() {
        val isPasswordValid = checkIsPasswordValid(state.value.password)
        val isEmailValid = checkIsEmailValid(state.value.email)
        val isNameValid = checkIsNameValid(state.value.name)
        if (!isPasswordValid || !isEmailValid || !isNameValid) {
            _state.value = state.value.copy(
                registrationErrors = RegistrationErrors(
                    shouldDisplayNameError = !isNameValid,
                    shouldDisplayEmailError = !isEmailValid,
                    shouldDisplayPasswordError = !isPasswordValid
                )
            )
            return
        }
        _state.value = state.value.copy(
            isLoading = true,
            registrationErrors = RegistrationErrors()
        )
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = registerUseCase(
                fullName = state.value.name,
                email = state.value.email,
                password = state.value.password
            )) {
                is Result.Success -> {
                    _state.value = state.value.copy(
                        isLoading = false
                    )
                    _uiEvent.send(UiEvent.ShowToast(ToastMessage.RegistrationSuccessful))
                    delay(DELAY_1000)
                    _uiEvent.send(UiEvent.Navigate(Route.Login))
                }
                is Result.Failure -> {
                    _state.value = state.value.copy(
                        isLoading = false
                    )
                    _uiEvent.send(UiEvent.ShowToast(ToastMessage.RegistrationFailed(result.errorMessageId)))
                }
            }
        }
    }
}