package com.taskyproject.tasky.domain.usecase

import com.taskyproject.tasky.domain.model.Event
import com.taskyproject.tasky.domain.model.EventAttendee
import com.taskyproject.tasky.domain.model.Photo
import com.taskyproject.tasky.domain.repository.EventRepository
import com.taskyproject.tasky.domain.util.Result
import java.io.File
import javax.inject.Inject

class UpdateEventUseCase @Inject constructor(
    private val eventRepository: EventRepository
) {
    suspend operator fun invoke(
        event: Event,
        photos: List<File>,
        deletedAttendees: List<EventAttendee>,
        deletedPhotos: List<Photo>
    ): Result<Event> {
        return eventRepository.updateEvent(
            event = event,
            photos = photos,
            deletedAttendees = deletedAttendees,
            deletedPhotos = deletedPhotos
        )
    }
}