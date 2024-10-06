package com.taskyproject.tasky.domain.usecase

import com.taskyproject.tasky.domain.repository.AgendaRepository
import com.taskyproject.tasky.domain.util.Result
import javax.inject.Inject

class GetFullAgendaUseCase @Inject constructor(
    private val agendaRepository: AgendaRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return agendaRepository.fetchFullAgendaAndStoreToLocalDB()
    }
}