package com.taskyproject.tasky.domain.usecase

import com.taskyproject.tasky.domain.model.AgendaItem
import com.taskyproject.tasky.domain.repository.AgendaRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class GetAgendaUseCase @Inject constructor(
    private val agendaRepository: AgendaRepository
) {
    suspend operator fun invoke(date: LocalDate): Flow<List<AgendaItem>> {
        return agendaRepository.listAgenda(date)
    }
}