package com.taskyproject.tasky.domain.usecase

import com.taskyproject.tasky.domain.repository.LogoutRepository
import com.taskyproject.tasky.domain.util.Result
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val logoutRepository: LogoutRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return logoutRepository.logout()
    }
}