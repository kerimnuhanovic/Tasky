package com.taskyproject.tasky.presentation.task

import com.taskyproject.tasky.domain.model.Option
import com.taskyproject.tasky.domain.model.TimeOption
import java.time.LocalDate
import java.time.LocalTime

data class TaskState(
    val title: String = "Task",
    val description: String = "Description",
    val reminderOptions: List<TimeOption> = TimeOption.listTimeOptions(),
    val selectedReminderOption: TimeOption = TimeOption.TenMinutesBefore,
    val time: LocalTime = LocalTime.of(8,0),
    val date: LocalDate = LocalDate.now(),
    val isEditable: Boolean = false,
    val isDatePickerOpen: Boolean = false,
    val isTimePickerOpen: Boolean = false,
    val isReminderDropdownExpanded: Boolean = false,
    val isCreateOperation: Boolean = true
)
