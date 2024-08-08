package com.taskyproject.tasky.data.network.reminder

import com.taskyproject.tasky.domain.model.Reminder

interface ReminderApi {
    suspend fun createReminder(reminder: Reminder)
    suspend fun updateReminder(reminder: Reminder)
}