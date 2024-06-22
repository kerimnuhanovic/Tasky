package com.taskyproject.tasky.presentation.register


sealed interface RegisterEvent {
    data class OnNameEnter(val name: String) : RegisterEvent
    data class OnEmailEnter(val email: String) : RegisterEvent
    data class OnPasswordEnter(val password: String) : RegisterEvent
    data object OnPasswordIconClick : RegisterEvent
    data object OnRegisterClick : RegisterEvent
    data object OnBackClick : RegisterEvent
}