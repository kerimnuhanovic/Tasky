package com.taskyproject.tasky.data.local.reminder

import com.taskyproject.tasky.domain.model.Reminder
import taskydatabase.ReminderEntity


interface ReminderDao {
    suspend fun insertReminder(
        reminder: Reminder,
        shouldBeDeleted: Boolean = false,
        shouldBeUpdated: Boolean = false,
        isAddedOnRemote: Boolean = false,
    )

    suspend fun getReminder(id: String): ReminderEntity

    suspend fun updateReminder(
        reminder: Reminder,
        shouldBeDeleted: Boolean = false,
        shouldBeUpdated: Boolean = false,
        isAddedOnRemote: Boolean = false,
    )
}