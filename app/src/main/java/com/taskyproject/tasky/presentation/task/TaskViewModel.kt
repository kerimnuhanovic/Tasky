package com.taskyproject.tasky.presentation.task

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.taskyproject.tasky.domain.model.Task
import com.taskyproject.tasky.domain.usecase.CreateTaskUseCase
import com.taskyproject.tasky.domain.util.Result
import com.taskyproject.tasky.domain.util.prepareTaskRequest
import com.taskyproject.tasky.navigation.Route
import com.taskyproject.tasky.presentation.util.ToastMessage
import com.taskyproject.tasky.presentation.util.UiEvent
import com.taskyproject.tasky.presentation.util.millisToLocalDate
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
class TaskViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val createTaskUseCase: CreateTaskUseCase
) : ViewModel() {
    private val taskId = savedStateHandle.toRoute<Route.Task>().taskId

    private val _state = MutableStateFlow(TaskState())
    val state = _state.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: TaskEvent) {
        when (event) {
            TaskEvent.OnCloseEditClick -> {
                _state.value = state.value.copy(
                    isEditable = false
                )
            }
            is TaskEvent.OnDateSelect -> {
                _state.value = state.value.copy(
                    date = millisToLocalDate(event.millis),
                    isDatePickerOpen = false
                )
            }
            TaskEvent.OnEditClick -> {
                _state.value = state.value.copy(
                    isEditable = true
                )
            }
            TaskEvent.OnEditDateClick -> {
                _state.value = state.value.copy(
                    isDatePickerOpen = true
                )
            }
            TaskEvent.OnEditDescriptionClick -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.Navigate(Route.DescriptionEdit(state.value.description)))
                }
            }
            TaskEvent.OnEditTimeClick -> {
                _state.value = state.value.copy(
                    isTimePickerOpen = true
                )
            }
            TaskEvent.OnEditTitleClick -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.Navigate(Route.TitleEdit(state.value.title)))
                }
            }
            TaskEvent.OnReminderDropdownStateChange -> {
                _state.value = state.value.copy(
                    isReminderDropdownExpanded = !state.value.isReminderDropdownExpanded
                )
            }
            is TaskEvent.OnReminderOptionSelect -> {
                _state.value = state.value.copy(
                    selectedReminderOption = event.option,
                    isReminderDropdownExpanded = false
                )
            }
            TaskEvent.OnSaveClick -> {
                createTask()
            }
            is TaskEvent.OnTimeSelect -> {
                _state.value = state.value.copy(
                    time = LocalTime.of(event.hours, event.minutes),
                    isTimePickerOpen = false
                )
            }

            is TaskEvent.OnDescriptionChange -> {
                _state.value = state.value.copy(
                    description = event.description
                )
            }
            is TaskEvent.OnTitleChange -> {
                _state.value = state.value.copy(
                    title = event.title
                )
            }
        }
    }

    private fun createTask() {
        val taskId = UUID.randomUUID().toString()
        val newTask = prepareTaskRequest(
            id = taskId,
            title = state.value.title,
            description = state.value.description,
            time = state.value.time,
            date = state.value.date,
            remindBefore = state.value.selectedReminderOption.value
        )
        viewModelScope.launch(Dispatchers.IO) {
            when(val result = createTaskUseCase(newTask)) {
                is Result.Success -> {
                    _uiEvent.send(UiEvent.ShowToast(ToastMessage.TaskCreated))
                }
                is Result.Failure -> {
                    _uiEvent.send(UiEvent.ShowToast(ToastMessage.TaskCreationFailed(result.errorMessageId)))
                }
            }
        }
    }
}