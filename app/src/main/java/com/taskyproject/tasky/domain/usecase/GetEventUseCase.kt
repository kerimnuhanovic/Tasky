package com.taskyproject.tasky.domain.usecase

import com.taskyproject.tasky.domain.model.Event
import com.taskyproject.tasky.domain.repository.EventRepository
import javax.inject.Inject

class GetEventUseCase @Inject constructor(
    private val eventRepository: EventRepository
) {
    suspend operator fun invoke(eventId: String): Event {
        return eventRepository.getEvent(eventId)
    }
}