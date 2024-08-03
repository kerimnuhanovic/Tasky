package com.taskyproject.tasky.data.repository

import com.taskyproject.tasky.data.mapper.toLoginResponse
import com.taskyproject.tasky.data.network.login.LoginApi
import com.taskyproject.tasky.data.network.dto.Credentials
import com.taskyproject.tasky.domain.model.LoginResponse
import com.taskyproject.tasky.domain.repository.LoginRepository
import com.taskyproject.tasky.domain.util.Result
import com.taskyproject.tasky.domain.util.handleApiError
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginApi: LoginApi
) : LoginRepository {
    override suspend fun login(email: String, password: String): Result<LoginResponse> {
        return try {
            val response = loginApi.login(Credentials(email, password))
            Result.Success(response.toLoginResponse())
        } catch (ex: Exception) {
            handleApiError(ex)
        }
    }
}