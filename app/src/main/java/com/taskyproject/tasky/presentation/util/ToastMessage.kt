package com.taskyproject.tasky.presentation.util

import com.taskyproject.tasky.R

sealed class ToastMessage(val valueId: Int) {
    data object RegistrationSuccessful : ToastMessage(R.string.registration_successfully)
    data class RegistrationFailed(val messageId: Int) : ToastMessage(messageId)
}