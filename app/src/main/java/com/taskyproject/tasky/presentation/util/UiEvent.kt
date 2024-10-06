package com.taskyproject.tasky.presentation.util

import com.taskyproject.tasky.navigation.Route

sealed interface UiEvent {
    data class Navigate(val route: Route) : UiEvent
    data class NavigateWithPopup(val navigateRoute: Route, val popRoute: Route) : UiEvent
    data object NavigateBack : UiEvent
    data object NavigateBackWithResult : UiEvent
    data class ShowToast(val message: ToastMessage) : UiEvent
}