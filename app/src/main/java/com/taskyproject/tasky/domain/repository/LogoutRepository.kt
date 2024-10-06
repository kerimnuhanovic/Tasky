package com.taskyproject.tasky.domain.repository

import com.taskyproject.tasky.domain.util.Result

interface LogoutRepository {
    suspend fun logout(): Result<Unit>
}