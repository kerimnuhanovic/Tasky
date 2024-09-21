package com.taskyproject.tasky.data.local.reminder

import com.taskyproject.tasky.domain.model.Reminder
import kotlinx.coroutines.flow.Flow
import taskydatabase.ReminderEntity


interface ReminderDao {
    suspend fun insertReminder(
        reminder: Reminder,
        shouldBeDeleted: Boolean = false,
        shouldBeUpdated: Boolean = false,
        isAddedOnRemote: Boolean = false,
    )

    suspend fun getReminder(id: String): ReminderEntity
    fun listReminders(): Flow<List<ReminderEntity>>

    suspend fun updateReminder(
        reminder: Reminder,
        shouldBeDeleted: Boolean = false,
        shouldBeUpdated: Boolean = false,
        isAddedOnRemote: Boolean = false,
    )
    suspend fun markReminderForDelete(reminderId: String)
    suspend fun deleteReminder(reminderId: String)
}