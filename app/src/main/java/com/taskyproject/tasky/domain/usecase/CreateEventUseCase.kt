package com.taskyproject.tasky.domain.usecase

import com.taskyproject.tasky.domain.model.Event
import com.taskyproject.tasky.domain.repository.EventRepository
import com.taskyproject.tasky.domain.util.Result
import java.io.File
import javax.inject.Inject

class CreateEventUseCase @Inject constructor(
    private val eventRepository: EventRepository
) {
    suspend operator fun invoke(event: Event, photos: List<File>): Result<Event> {
        return eventRepository.createEvent(event, photos)
    }
}