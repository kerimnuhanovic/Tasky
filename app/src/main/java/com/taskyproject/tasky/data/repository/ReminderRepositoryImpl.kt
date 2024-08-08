package com.taskyproject.tasky.data.repository

import com.taskyproject.tasky.R
import com.taskyproject.tasky.data.local.reminder.ReminderDao
import com.taskyproject.tasky.data.mapper.toReminder
import com.taskyproject.tasky.data.network.reminder.ReminderApi
import com.taskyproject.tasky.domain.model.Reminder
import com.taskyproject.tasky.domain.repository.ReminderRepository
import com.taskyproject.tasky.domain.util.Result
import com.taskyproject.tasky.domain.util.handleApiError
import java.net.UnknownHostException
import javax.inject.Inject

class ReminderRepositoryImpl @Inject constructor(
    private val reminderDao: ReminderDao,
    private val reminderApi: ReminderApi
) : ReminderRepository {
    override suspend fun createReminder(reminder: Reminder): Result<Reminder> {
        return try {
            reminderDao.insertReminder(reminder)
            reminderApi.createReminder(reminder)
            reminderDao.insertReminder(reminder, isAddedOnRemote = true)
            Result.Success(reminder)
        } catch (ex: UnknownHostException) {
            Result.Success(reminder)
        } catch (ex: Exception) {
            handleApiError(ex)
        }
    }

    override suspend fun getReminder(id: String): Reminder {
        val reminderDocument = reminderDao.getReminder(id)
        return reminderDocument.toReminder()
    }

    override suspend fun updateReminder(reminder: Reminder): Result<Reminder> {
        return try {
            val reminderDocument = reminderDao.getReminder(reminder.id)
            reminderDao.updateReminder(
                reminder = reminder,
                isAddedOnRemote = reminderDocument.isAddedOnRemote == 1L,
                shouldBeUpdated = true
            )
            if (reminderDocument.isAddedOnRemote == 1L) {
                reminderApi.updateReminder(reminder)
                reminderDao.updateReminder(reminder, isAddedOnRemote = true, shouldBeUpdated = false)
            }
            Result.Success(reminder)
        } catch (ex: UnknownHostException) {
            Result.Success(reminder)
        } catch (ex: Exception) {
            handleApiError(ex)
        }
    }
}