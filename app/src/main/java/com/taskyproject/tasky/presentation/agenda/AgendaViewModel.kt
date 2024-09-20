package com.taskyproject.tasky.presentation.agenda

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taskyproject.tasky.domain.model.Event
import com.taskyproject.tasky.domain.model.Reminder
import com.taskyproject.tasky.domain.model.Task
import com.taskyproject.tasky.domain.usecase.GetAgendaUseCase
import com.taskyproject.tasky.navigation.Route
import com.taskyproject.tasky.presentation.util.AgendaItemType
import com.taskyproject.tasky.presentation.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AgendaViewModel @Inject constructor(
    private val getAgendaUseCase: GetAgendaUseCase
) : ViewModel() {

    private val selectedDate = MutableStateFlow<LocalDate>(LocalDate.now())
    @OptIn(ExperimentalCoroutinesApi::class)
    private val agendaFlow = selectedDate
        .flatMapLatest { selectedDate ->
            getAgendaUseCase(selectedDate)
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val _state = MutableStateFlow(AgendaState())
    val state = combine(_state, agendaFlow) { state, agenda ->
        state.copy(
            agendaItems = agenda
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), AgendaState())

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


    fun onEvent(event: AgendaEvent) {
        when (event) {
            AgendaEvent.OnAddClick -> {
                _state.value = state.value.copy(
                    isBottomSheetOpened = true
                )
            }
            AgendaEvent.OnBottomSheetDismiss -> {
                _state.value = state.value.copy(
                    isBottomSheetOpened = false
                )
            }
            is AgendaEvent.OnDateChange -> {
                selectedDate.value = event.date
            }

            is AgendaEvent.OnAddNewAgendaItemClick -> {
                _state.value = state.value.copy(
                    isBottomSheetOpened = false
                )
                viewModelScope.launch {
                    when (event.agendaItemType) {
                        AgendaItemType.EVENT -> {
                            _uiEvent.send(UiEvent.Navigate(Route.EventDetails(null, true)))
                        }
                        AgendaItemType.REMINDER -> {
                            _uiEvent.send(UiEvent.Navigate(Route.Reminder(null, true)))
                        }
                        AgendaItemType.TASK -> {
                            _uiEvent.send(UiEvent.Navigate(Route.Task(null, true)))
                        }
                    }
                }
            }

            is AgendaEvent.OnDeleteAgendaItemClick -> {

            }
            is AgendaEvent.OnEditAgendaItemClick -> {
                viewModelScope.launch {
                    when (event.agenda) {
                        is Event -> {
                            _uiEvent.send(UiEvent.Navigate(Route.EventDetails(event.agenda.id, true)))
                        }
                        is Task -> {
                            _uiEvent.send(UiEvent.Navigate(Route.Task(event.agenda.id, true)))
                        }
                        is Reminder -> {
                            _uiEvent.send(UiEvent.Navigate(Route.Reminder(event.agenda.id, true)))
                        }
                    }
                }
            }
            is AgendaEvent.OnMoreClick -> {
                _state.value = state.value.copy(
                    indexOfOpenedMenu = if (state.value.indexOfOpenedMenu == event.index) null else event.index
                )
            }
            is AgendaEvent.OnOpenAgendaItemClick -> {
                viewModelScope.launch {
                    when (event.agenda) {
                        is Event -> {
                            _uiEvent.send(UiEvent.Navigate(Route.EventDetails(event.agenda.id, false)))
                        }
                        is Task -> {
                            _uiEvent.send(UiEvent.Navigate(Route.Task(event.agenda.id, false)))
                        }
                        is Reminder -> {
                            _uiEvent.send(UiEvent.Navigate(Route.Reminder(event.agenda.id, false)))
                        }
                    }
                }
            }
        }
    }

    private fun getAgenda() {
        viewModelScope.launch {
            selectedDate.collect { selectedDate ->
                getAgendaUseCase(selectedDate).collect { agendaItems ->
                    _state.value = state.value.copy(agendaItems = agendaItems)
                }
            }
        }
    }


}