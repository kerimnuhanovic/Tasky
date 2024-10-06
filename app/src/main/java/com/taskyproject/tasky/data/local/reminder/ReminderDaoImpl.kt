package com.taskyproject.tasky.data.local.reminder

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.taskyproject.tasky.domain.model.Reminder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import taskydatabase.ReminderEntity
import taskydatabase.ReminderEntityQueries
import javax.inject.Inject

class ReminderDaoImpl @Inject constructor(
    private val reminderEntityQueries: ReminderEntityQueries
) : ReminderDao {
    override suspend fun insertReminder(
        reminder: Reminder,
        shouldBeDeleted: Boolean,
        shouldBeUpdated: Boolean,
        isAddedOnRemote: Boolean
    ) {
        reminderEntityQueries.insertReminder(
            id = reminder.id,
            title = reminder.title,
            description = reminder.description,
            time = reminder.time,
            remindAt = reminder.remindAt,
            shouldBeDeleted = if (shouldBeDeleted) 1 else 0,
            shouldBeUpdated = if (shouldBeUpdated) 1 else 0,
            isAddedOnRemote = if (isAddedOnRemote) 1 else 0
        )
    }

    override suspend fun getReminder(id: String): ReminderEntity {
        return reminderEntityQueries.getReminder(id).executeAsOne()
    }

    override fun listReminders(): Flow<List<ReminderEntity>> {
        return reminderEntityQueries.listReminders().asFlow().mapToList(Dispatchers.IO)
    }

    override suspend fun updateReminder(
        reminder: Reminder,
        shouldBeDeleted: Boolean,
        shouldBeUpdated: Boolean,
        isAddedOnRemote: Boolean
    ) {
        return reminderEntityQueries.updateReminder(
            id = reminder.id,
            title = reminder.title,
            description = reminder.description,
            time = reminder.time,
            remindAt = reminder.remindAt,
            shouldBeDeleted = if (shouldBeDeleted) 1 else 0,
            shouldBeUpdated = if (shouldBeUpdated) 1 else 0,
            isAddedOnRemote = if (isAddedOnRemote) 1 else 0
        )
    }

    override suspend fun markReminderForDelete(reminderId: String) {
        reminderEntityQueries.markReminderForDelete(reminderId)
    }

    override suspend fun deleteReminder(reminderId: String) {
        reminderEntityQueries.deleteReminder(reminderId)
    }

    override suspend fun listRemindersForDelete(): List<String> {
        return reminderEntityQueries.listRemindersForDelete().executeAsList()
    }

    override suspend fun listRemindersForCreate(): List<ReminderEntity> {
        return reminderEntityQueries.listRemindersForCreate().executeAsList()
    }

    override suspend fun listRemindersForUpdate(): List<ReminderEntity> {
        return reminderEntityQueries.listRemindersForUpdate().executeAsList()
    }

    override suspend fun markReminderAsAddedOnRemote(id: String) {
        reminderEntityQueries.markReminderAsAddedOnRemote(id)
    }

    override suspend fun markReminderAsUpdated(id: String) {
        reminderEntityQueries.markReminderAsUpdated(id)
    }

    override suspend fun nuke() {
        reminderEntityQueries.nuke()
    }
}