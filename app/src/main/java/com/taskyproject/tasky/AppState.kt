package com.taskyproject.tasky

import com.taskyproject.tasky.navigation.Route

data class AppState(
    val isUserLoggedIn: Boolean = false,
    val startDestination: Route = Route.Login
)