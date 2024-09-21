package com.taskyproject.tasky.domain.usecase

import com.taskyproject.tasky.domain.repository.ReminderRepository
import com.taskyproject.tasky.domain.util.Result
import javax.inject.Inject

class DeleteReminderUseCase @Inject constructor(
    private val reminderRepository: ReminderRepository
) {
    suspend operator fun invoke(reminderId: String): Result<Unit> {
        return reminderRepository.deleteReminder(reminderId)
    }
}