package com.taskyproject.tasky.presentation.reminder

import com.taskyproject.tasky.domain.model.Option
import com.taskyproject.tasky.domain.model.TimeOption
import com.taskyproject.tasky.presentation.task.TaskEvent

sealed interface ReminderEvent {
    data object OnCloseEditClick : ReminderEvent
    data object OnSaveClick : ReminderEvent
    data object OnEditClick : ReminderEvent
    data class OnDateSelect(val millis: Long) : ReminderEvent
    data class OnTimeSelect(val hours: Int, val minutes: Int) : ReminderEvent
    data object OnEditTitleClick : ReminderEvent
    data object OnEditDescriptionClick : ReminderEvent
    data object OnEditTimeClick : ReminderEvent
    data object OnEditDateClick : ReminderEvent
    data object OnReminderDropdownStateChange : ReminderEvent
    data class OnReminderOptionSelect(val option: TimeOption) : ReminderEvent
    data class OnTitleChange(val title: String) : ReminderEvent
    data class OnDescriptionChange(val description: String) : ReminderEvent
}