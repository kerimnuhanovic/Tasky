package com.taskyproject.tasky.presentation.util

import com.taskyproject.tasky.R

sealed class ToastMessage(val valueId: Int) {
    data object RegistrationSuccessful : ToastMessage(R.string.registration_successfully)
    data class RegistrationFailed(val messageId: Int) : ToastMessage(messageId)
    data object EventCreated : ToastMessage(R.string.event_created)
    data class EventCreationFailed(val messageId: Int) : ToastMessage(messageId)
    data object TaskCreated : ToastMessage(R.string.task_created)
    data class TaskCreationFailed(val messageId: Int) : ToastMessage(messageId)
    data object ReminderCreated : ToastMessage(R.string.reminder_created)
    data class ReminderCreationFailed(val messageId: Int) : ToastMessage(messageId)
}