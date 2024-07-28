package com.taskyproject.tasky.presentation.descriptionedit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.taskyproject.tasky.navigation.Route
import com.taskyproject.tasky.presentation.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DescriptionEditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val title: String = checkNotNull(savedStateHandle.toRoute<Route.DescriptionEdit>().description)

    private val _state = MutableStateFlow(
        DescriptionEditState(
        initialDescription = title,
        description = title
    )
    )
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: DescriptionEditEvent) {
        when (event) {
            is DescriptionEditEvent.OnTitleEnter -> {
                _state.value = state.value.copy(
                    description = event.title
                )
            }

            DescriptionEditEvent.OnSaveClick -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.NavigateBackWithResult)
                }
            }

            DescriptionEditEvent.OnBackClick -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.NavigateBack)
                }
            }
        }
    }
}