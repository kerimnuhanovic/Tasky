package com.taskyproject.tasky

import androidx.lifecycle.ViewModel
import com.taskyproject.tasky.domain.usecase.CheckIsUserLoggedInUseCase
import com.taskyproject.tasky.navigation.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val checkIsUserLoggedInUseCase: CheckIsUserLoggedInUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<AppState> = MutableStateFlow(AppState())
    val state = _state.asStateFlow()

    init {
        checkIsUserLoggedIn()
    }

    private fun checkIsUserLoggedIn() {
        if (checkIsUserLoggedInUseCase()) {
            _state.value = state.value.copy(
                isUserLoggedIn = true,
                startDestination = Route.Agenda
            )
        }
    }

}