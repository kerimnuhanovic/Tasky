package com.taskyproject.tasky.presentation.util

import com.taskyproject.tasky.R

sealed class ToastMessage(val valueId: Int) {
    data object RegistrationSuccessful : ToastMessage(R.string.registration_successfully)
    data class RegistrationFailed(val messageId: Int) : ToastMessage(messageId)
    data class EventCreated(val messageId: Int) : ToastMessage(messageId)
    data class EventCreationFailed(val messageId: Int) : ToastMessage(messageId)
    data object TaskCreated : ToastMessage(R.string.task_created)
    data class TaskCreationFailed(val messageId: Int) : ToastMessage(messageId)
    data object TaskUpdated : ToastMessage(R.string.reminder_updated)
    data class TaskUpdateFailed(val messageId: Int) : ToastMessage(messageId)
    data object ReminderCreated : ToastMessage(R.string.reminder_created)
    data class ReminderCreationFailed(val messageId: Int) : ToastMessage(messageId)
    data object ReminderUpdated : ToastMessage(R.string.reminder_updated)
    data class ReminderUpdateFailed(val messageId: Int) : ToastMessage(messageId)
    data class EventUpdated(val messageId: Int) : ToastMessage(messageId)
    data class EventUpdateFailed(val messageId: Int) : ToastMessage(messageId)
    data class AgendaItemDeleted(val messageId: Int) : ToastMessage(messageId)
    data class AgendaItemDeleteFailed(val messageId: Int) : ToastMessage(messageId)
}