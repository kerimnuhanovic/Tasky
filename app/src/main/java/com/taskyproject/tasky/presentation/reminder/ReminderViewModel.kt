package com.taskyproject.tasky.presentation.reminder

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.taskyproject.tasky.domain.model.TimeOption
import com.taskyproject.tasky.domain.usecase.CreateReminderUseCase
import com.taskyproject.tasky.domain.usecase.GetReminderUseCase
import com.taskyproject.tasky.domain.usecase.UpdateReminderUseCase
import com.taskyproject.tasky.domain.util.Result
import com.taskyproject.tasky.domain.util.prepareReminderRequest
import com.taskyproject.tasky.navigation.Route
import com.taskyproject.tasky.presentation.util.ToastMessage
import com.taskyproject.tasky.presentation.util.UiEvent
import com.taskyproject.tasky.presentation.util.differenceInMinutes
import com.taskyproject.tasky.presentation.util.millisToLocalDate
import com.taskyproject.tasky.presentation.util.millisToLocalTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalTime
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ReminderViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val createReminderUseCase: CreateReminderUseCase,
    private val getReminderUseCase: GetReminderUseCase,
    private val updateReminderUseCase: UpdateReminderUseCase
) : ViewModel() {
    private val reminderId = savedStateHandle.toRoute<Route.Reminder>().reminderId
    private val shouldOpenInEditMode = savedStateHandle.toRoute<Route.Reminder>().shouldOpenInEditMode

    private val _state = MutableStateFlow(ReminderState(
        isEditable = shouldOpenInEditMode
    ))
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        getReminder()
    }

    fun onEvent(event: ReminderEvent) {
        when (event) {
            ReminderEvent.OnCloseEditClick -> {
                _state.value = state.value.copy(
                    isEditable = false
                )
            }
            is ReminderEvent.OnDateSelect -> {
                _state.value = state.value.copy(
                    date = millisToLocalDate(event.millis),
                    isDatePickerOpen = false
                )
            }
            ReminderEvent.OnEditClick -> {
                _state.value = state.value.copy(
                    isEditable = true
                )
            }
            ReminderEvent.OnEditDateClick -> {
                _state.value = state.value.copy(
                    isDatePickerOpen = true
                )
            }
            ReminderEvent.OnEditDescriptionClick -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.Navigate(Route.DescriptionEdit(state.value.description)))
                }
            }
            ReminderEvent.OnEditTimeClick -> {
                _state.value = state.value.copy(
                    isTimePickerOpen = true
                )
            }
            ReminderEvent.OnEditTitleClick -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.Navigate(Route.TitleEdit(state.value.title)))
                }
            }
            ReminderEvent.OnReminderDropdownStateChange -> {
                _state.value = state.value.copy(
                    isReminderDropdownExpanded = !state.value.isReminderDropdownExpanded
                )
            }
            is ReminderEvent.OnReminderOptionSelect -> {
                _state.value = state.value.copy(
                    selectedReminderOption = event.option,
                    isReminderDropdownExpanded = false
                )
            }
            ReminderEvent.OnSaveClick -> {
                createOrUpdateReminder()
            }
            is ReminderEvent.OnTimeSelect -> {
                _state.value = state.value.copy(
                    time = LocalTime.of(event.hours, event.minutes),
                    isTimePickerOpen = false
                )
            }

            is ReminderEvent.OnDescriptionChange -> {
                _state.value = state.value.copy(
                    description = event.description
                )
            }
            is ReminderEvent.OnTitleChange -> {
                _state.value = state.value.copy(
                    title = event.title
                )
            }
        }
    }

    private fun getReminder() {
        viewModelScope.launch {
            reminderId?.let {
                val reminder = getReminderUseCase(it)
                _state.value = state.value.copy(
                    isCreateOperation = false,
                    title = reminder.title,
                    description = reminder.description,
                    time = millisToLocalTime(reminder.time),
                    date = millisToLocalDate(reminder.time),
                    selectedReminderOption = TimeOption.fromTime(differenceInMinutes(reminder.time, reminder.remindAt).toString())
                )
            }
        }
    }

    private fun createOrUpdateReminder() {
        if (state.value.isCreateOperation) {
            val reminderId = UUID.randomUUID().toString()
            val newReminder = prepareReminderRequest(
                id = reminderId,
                title = state.value.title,
                description = state.value.description,
                time = state.value.time,
                date = state.value.date,
                remindBefore = state.value.selectedReminderOption.value
            )
            viewModelScope.launch(Dispatchers.IO) {
                when(val result = createReminderUseCase(newReminder)) {
                    is Result.Success -> {
                        _uiEvent.send(UiEvent.ShowToast(ToastMessage.ReminderCreated))
                        _state.value = state.value.copy(
                            isEditable = false
                        )
                        _uiEvent.send(UiEvent.Navigate(Route.Agenda))
                    }
                    is Result.Failure -> {
                        _uiEvent.send(UiEvent.ShowToast(ToastMessage.ReminderCreationFailed(result.errorMessageId)))
                    }
                }
            }
        } else {
            val reminder = prepareReminderRequest(
                id = reminderId!!,
                title = state.value.title,
                description = state.value.description,
                time = state.value.time,
                date = state.value.date,
                remindBefore = state.value.selectedReminderOption.value
            )
            viewModelScope.launch {
                when (val result = updateReminderUseCase(reminder)) {
                    is Result.Success -> {
                        _uiEvent.send(UiEvent.ShowToast(ToastMessage.ReminderUpdated))
                        _state.value = state.value.copy(
                            isEditable = false
                        )
                    }
                    is Result.Failure -> {
                        _uiEvent.send(UiEvent.ShowToast(ToastMessage.ReminderUpdateFailed(result.errorMessageId)))
                    }
                }
            }
        }
    }
}