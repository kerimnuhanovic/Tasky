package com.taskyproject.tasky.domain.usecase

import com.taskyproject.tasky.domain.repository.AgendaRepository
import javax.inject.Inject

class NukeDataUseCase @Inject constructor(
    private val agendaRepository: AgendaRepository
) {
    suspend operator fun invoke() {
        agendaRepository.nuke()
    }
}