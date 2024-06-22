package com.taskyproject.tasky.domain.usecase

import com.taskyproject.tasky.domain.model.LoginResponse
import com.taskyproject.tasky.domain.repository.LoginRepository
import com.taskyproject.tasky.domain.util.Result
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<LoginResponse> =
        loginRepository.login(email, password)
}