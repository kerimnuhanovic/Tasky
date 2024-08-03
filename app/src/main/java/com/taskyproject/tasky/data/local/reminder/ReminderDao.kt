package com.taskyproject.tasky.data.local.reminder

import com.taskyproject.tasky.domain.model.Reminder


interface ReminderDao {
    suspend fun insertReminder(
        reminder: Reminder,
        shouldBeDeleted: Boolean = false,
        shouldBeUpdated: Boolean = false,
        isAddedOnRemote: Boolean = false,
    )
}