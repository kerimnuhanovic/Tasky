package com.taskyproject.tasky.presentation.login

sealed interface LoginEvent {
    data class OnEmailEnter(val email: String) : LoginEvent
    data class OnPasswordEnter(val password: String) : LoginEvent
    data object OnPasswordIconClick : LoginEvent
    data object OnLoginClick : LoginEvent
    object OnSignUpClick : LoginEvent
}