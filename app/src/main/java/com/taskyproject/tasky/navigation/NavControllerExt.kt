package com.taskyproject.tasky.navigation

import androidx.navigation.NavController
import com.taskyproject.tasky.presentation.util.UiEvent

fun NavController.navigate(uiEvent: UiEvent.Navigate) {
    this.navigate(uiEvent.route)
}

fun NavController.navigateBack() {
    this.popBackStack()
}