package com.taskyproject.tasky.domain.usecase

import com.taskyproject.tasky.domain.repository.RegisterRepository
import com.taskyproject.tasky.domain.util.Result
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val registerRepository: RegisterRepository
) {
    suspend operator fun invoke(fullName: String, email: String, password: String): Result<Unit> =
        registerRepository.register(fullName, email, password)
}