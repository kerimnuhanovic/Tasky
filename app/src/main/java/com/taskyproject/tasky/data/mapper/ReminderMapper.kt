package com.taskyproject.tasky.data.mapper

import com.taskyproject.tasky.data.network.dto.ReminderDto
import com.taskyproject.tasky.domain.model.Reminder
import taskydatabase.ReminderEntity

fun ReminderDto.toReminder(): Reminder {
    return Reminder(
        id = id,
        title = title,
        description = description,
        time = time,
        remindAt = remindAt
    )
}

fun Reminder.toReminderDto(): ReminderDto {
    return ReminderDto(
        id = id,
        title = title,
        description = description,
        time = time,
        remindAt = remindAt
    )
}

fun ReminderEntity.toReminder(): Reminder {
    return Reminder(
        id = id,
        title = title,
        description = description,
        time = time,
        remindAt = remindAt
    )
}