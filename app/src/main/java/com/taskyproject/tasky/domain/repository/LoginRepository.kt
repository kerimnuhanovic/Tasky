package com.taskyproject.tasky.domain.repository

import com.taskyproject.tasky.domain.model.LoginResponse
import com.taskyproject.tasky.domain.util.Result

interface LoginRepository {
    suspend fun login(email: String, password: String) : Result<LoginResponse>
}