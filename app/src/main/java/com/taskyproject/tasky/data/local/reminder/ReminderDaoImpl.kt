package com.taskyproject.tasky.data.local.reminder

import com.taskyproject.tasky.domain.model.Reminder
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
}