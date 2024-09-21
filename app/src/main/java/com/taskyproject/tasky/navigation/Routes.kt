package com.taskyproject.tasky.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object Login : Route

    @Serializable
    data object Agenda : Route

    @Serializable
    data object Register : Route

    @Serializable
    data class EventDetails(val eventId: String? = null, val shouldOpenInEditMode: Boolean) : Route

    @Serializable
    data class Task(val taskId: String? = null, val shouldOpenInEditMode: Boolean) : Route

    @Serializable
    data class Reminder(val reminderId: String? = null, val shouldOpenInEditMode: Boolean) : Route

    @Serializable
    data class TitleEdit(val title: String) : Route

    @Serializable
    data class DescriptionEdit(val description: String) : Route
}