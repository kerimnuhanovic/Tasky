package com.taskyproject.tasky.domain.usecase

import com.taskyproject.tasky.domain.model.Reminder
import com.taskyproject.tasky.domain.repository.ReminderRepository
import javax.inject.Inject

class GetReminderUseCase @Inject constructor(
    private val reminderRepository: ReminderRepository
) {
    suspend operator fun invoke(id: String): Reminder {
        return reminderRepository.getReminder(id)
    }
}