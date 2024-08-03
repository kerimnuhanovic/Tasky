package com.taskyproject.tasky.presentation.task

import com.taskyproject.tasky.domain.model.Option
import java.time.LocalDate
import java.time.LocalTime

data class TaskState(
    val title: String = "Task",
    val description: String = "Description",
    val reminderOptions: List<Option> = listOf(
        Option("10", "10 minutes before"),
        Option("30", "30 minutes before"),
        Option("1", "1 hour before"),
        Option("1", "1 day before")
    ),
    val selectedReminderOption: Option = Option("10", "10 minutes before"),
    val time: LocalTime = LocalTime.of(8,0),
    val date: LocalDate = LocalDate.now(),
    val isEditable: Boolean = false,
    val isDatePickerOpen: Boolean = false,
    val isTimePickerOpen: Boolean = false,
    val isReminderDropdownExpanded: Boolean = false
)
