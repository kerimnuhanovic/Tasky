package com.taskyproject.tasky.domain.repository

import com.taskyproject.tasky.domain.model.Event
import com.taskyproject.tasky.domain.model.EventAttendee
import com.taskyproject.tasky.domain.model.Photo
import com.taskyproject.tasky.domain.util.Result
import java.io.File

interface EventRepository {
    suspend fun createEvent(event: Event, photos: List<File>): Result<Event>
    suspend fun getEvent(id: String): Event
    suspend fun updateEvent(
        event: Event,
        photos: List<File>,
        deletedAttendees: List<EventAttendee>,
        deletedPhotos: List<Photo>
    ): Result<Event>
}