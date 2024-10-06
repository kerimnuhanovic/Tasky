package com.taskyproject.tasky.data.repository

import com.taskyproject.tasky.data.network.logout.LogoutApi
import com.taskyproject.tasky.domain.preferences.Preferences
import com.taskyproject.tasky.domain.repository.LogoutRepository
import com.taskyproject.tasky.domain.util.Result
import com.taskyproject.tasky.domain.util.handleApiError
import javax.inject.Inject

class LogoutRepositoryImpl @Inject constructor(
    private val logoutApi: LogoutApi
): LogoutRepository {
    override suspend fun logout(): Result<Unit> {
        return try {
            logoutApi.logout()
            Result.Success(Unit)
        } catch (ex: Exception) {
            handleApiError(ex)
        }
    }
}