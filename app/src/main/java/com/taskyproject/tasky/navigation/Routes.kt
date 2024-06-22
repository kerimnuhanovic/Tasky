package com.taskyproject.tasky.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object Login : Route

    @Serializable
    data object EventList : Route

    @Serializable
    data object Register : Route
}