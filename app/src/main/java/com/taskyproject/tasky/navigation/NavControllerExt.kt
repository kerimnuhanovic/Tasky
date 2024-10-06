package com.taskyproject.tasky.navigation

import androidx.navigation.NavController
import com.taskyproject.tasky.presentation.util.UiEvent

fun NavController.navigate(uiEvent: UiEvent.Navigate) {
    this.navigate(uiEvent.route)
}

fun NavController.navigateBack() {
    this.popBackStack()
}

fun NavController.navigateWithPopup(uiEvent: UiEvent.NavigateWithPopup) {
    this.popBackStack(route = uiEvent.popRoute, inclusive = true)
    this.navigate(uiEvent.navigateRoute)
}

fun NavController.navigateBackWithResult(key: String, value: String) {
    this.previousBackStackEntry!!.savedStateHandle[key] = value
    this.popBackStack()
}