package com.taskyproject.tasky.data.repository

import com.taskyproject.tasky.data.local.event.EventDao
import com.taskyproject.tasky.data.local.eventattendee.EventAttendeeDao
import com.taskyproject.tasky.data.local.photo.PhotoDao
import com.taskyproject.tasky.data.mapper.toEvent
import com.taskyproject.tasky.data.mapper.toEventAttendee
import com.taskyproject.tasky.data.mapper.toPhoto
import com.taskyproject.tasky.data.network.EventApi
import com.taskyproject.tasky.domain.model.Event
import com.taskyproject.tasky.domain.repository.EventRepository
import java.io.File
import java.net.UnknownHostException
import javax.inject.Inject
import com.taskyproject.tasky.domain.util.Result
import com.taskyproject.tasky.domain.util.handleApiError

class EventRepositoryImpl @Inject constructor(
    private val eventApi: EventApi,
    private val eventDao: EventDao,
    private val photoDao: PhotoDao,
    private val eventAttendeeDao: EventAttendeeDao,
) : EventRepository {
    override suspend fun createEvent(event: Event, photos: List<File>): Result<Event> {
        try {
            eventDao.insertEvent(event, isAddedOnRemote = false)
            event.attendees.forEach { attendee ->
                eventAttendeeDao.insertAttendee(attendee)
            }
            event.photos.forEach { photo ->
                photoDao.insetPhoto(photo)
            }
            val result = eventApi.createEvent(event, photos)
            val freshEvent = result.toEvent()
            eventDao.insertEvent(freshEvent, isAddedOnRemote = true)
            result.attendees.forEach { attendeeDto ->
                eventAttendeeDao.insertAttendee(attendeeDto.toEventAttendee())
            }
            result.photos.forEach { photoDto ->
                photoDao.insetPhoto(photoDto.toPhoto(event.id))
            }
            return Result.Success(freshEvent)
        } catch (ex: UnknownHostException) {
            return Result.Success(event)
        } catch (ex: Exception) {
            return handleApiError(ex)
        }
    }
}