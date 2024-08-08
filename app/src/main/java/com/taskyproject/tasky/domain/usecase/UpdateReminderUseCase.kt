package com.taskyproject.tasky.domain.usecase

import com.taskyproject.tasky.domain.model.Reminder
import com.taskyproject.tasky.domain.repository.ReminderRepository
import com.taskyproject.tasky.domain.util.Result
import javax.inject.Inject

class UpdateReminderUseCase @Inject constructor(
    private val reminderRepository: ReminderRepository
) {
    suspend operator fun invoke(reminder: Reminder): Result<Reminder> {
        return reminderRepository.updateReminder(reminder)
    }
}