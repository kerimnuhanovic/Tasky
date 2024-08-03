package com.taskyproject.tasky.data.repository

import com.taskyproject.tasky.data.network.register.RegisterApi
import com.taskyproject.tasky.data.network.dto.RegisterData
import com.taskyproject.tasky.domain.repository.RegisterRepository
import com.taskyproject.tasky.domain.util.Result
import com.taskyproject.tasky.domain.util.handleApiError
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val registerApi: RegisterApi
) : RegisterRepository {
    override suspend fun register(fullName: String, email: String, password: String): Result<Unit> {
        return try {
            registerApi.register(RegisterData(fullName, email, password))
            Result.Success(Unit)
        } catch (ex: Exception) {
            handleApiError(ex)
        }
    }
}