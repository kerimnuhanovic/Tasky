package com.taskyproject.tasky.domain.repository

import com.taskyproject.tasky.domain.model.Reminder
import com.taskyproject.tasky.domain.util.Result

interface ReminderRepository {
    suspend fun createReminder(reminder: Reminder): Result<Reminder>
    suspend fun getReminder(id: String): Reminder
    suspend fun updateReminder(reminder: Reminder): Result<Reminder>
    suspend fun deleteReminder(reminderId: String): Result<Unit>
}