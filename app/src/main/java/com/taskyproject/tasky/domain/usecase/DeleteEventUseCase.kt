package com.taskyproject.tasky.domain.usecase

import com.taskyproject.tasky.domain.repository.EventRepository
import com.taskyproject.tasky.domain.util.Result
import javax.inject.Inject

class DeleteEventUseCase @Inject constructor(
    private val eventRepository: EventRepository
) {
    suspend operator fun invoke(eventId: String): Result<Unit> {
        return eventRepository.deleteEvent(eventId)
    }
}