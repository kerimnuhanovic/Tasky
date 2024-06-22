package com.taskyproject.tasky.domain.model

data class RegistrationErrors(
    val shouldDisplayNameError: Boolean = false,
    val shouldDisplayEmailError: Boolean = false,
    val shouldDisplayPasswordError: Boolean = false
)
