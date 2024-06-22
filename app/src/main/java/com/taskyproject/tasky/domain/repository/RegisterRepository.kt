package com.taskyproject.tasky.domain.repository

import com.taskyproject.tasky.domain.util.Result

interface RegisterRepository {
    suspend fun register(fullName: String, email: String, password: String) : Result<Unit>
}