package com.taskyproject.tasky.presentation.task

import com.taskyproject.tasky.domain.model.Option

sealed interface TaskEvent {
    data object OnCloseEditClick : TaskEvent
    data object OnSaveClick : TaskEvent
    data object OnEditClick : TaskEvent
    data class OnDateSelect(val millis: Long) : TaskEvent
    data class OnTimeSelect(val hours: Int, val minutes: Int) : TaskEvent
    data object OnEditTitleClick : TaskEvent
    data object OnEditDescriptionClick : TaskEvent
    data object OnEditTimeClick : TaskEvent
    data object OnEditDateClick : TaskEvent
    data object OnReminderDropdownStateChange : TaskEvent
    data class OnReminderOptionSelect(val option: Option) : TaskEvent
    data class OnTitleChange(val title: String) : TaskEvent
    data class OnDescriptionChange(val description: String) : TaskEvent
}