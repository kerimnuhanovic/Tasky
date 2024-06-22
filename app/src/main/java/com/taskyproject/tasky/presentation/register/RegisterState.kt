package com.taskyproject.tasky.presentation.register

import com.taskyproject.tasky.domain.model.RegistrationErrors

data class RegisterState(
    val name: String = "",
    val isNameValid: Boolean = false,
    val email: String = "",
    val isEmailValid: Boolean = false,
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val registrationErrors: RegistrationErrors = RegistrationErrors()
)
