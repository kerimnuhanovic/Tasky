package com.taskyproject.tasky.data.repository

import com.taskyproject.tasky.data.local.event.EventDao
import com.taskyproject.tasky.data.local.eventattendee.EventAttendeeDao
import com.taskyproject.tasky.data.local.photo.PhotoDao
import com.taskyproject.tasky.data.mapper.toEvent
import com.taskyproject.tasky.data.mapper.toEventAttendee
import com.taskyproject.tasky.data.mapper.toPhoto
import com.taskyproject.tasky.data.mapper.toUpdateEventRequestDto
import com.taskyproject.tasky.data.network.event.EventApi
import com.taskyproject.tasky.domain.model.Event
import com.taskyproject.tasky.domain.model.EventAttendee
import com.taskyproject.tasky.domain.model.Photo
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
            return Result.Success(
                event.copy(
                    photos = listOf()
                )
            )
        } catch (ex: Exception) {
            return handleApiError(ex)
        }
    }

    override suspend fun getEvent(id: String): Event {
        val eventData = eventDao.getEvent(id)
        val eventPhotos = photoDao.getEventPhotos(id)
        val eventAttendees = eventAttendeeDao.getEventAttendees(id)
        return Event(
            id = id,
            isUserEventCreator = eventData.isUserEventCreator == 1L,
            title = eventData.title,
            description = eventData.description,
            from = eventData.fromTime,
            to = eventData.toTime,
            host = eventData.host,
            remindAt = eventData.remindAt,
            photos = eventPhotos.map { photoEntity ->
                photoEntity.toPhoto()
            },
            attendees = eventAttendees.map { eventAttendeeEntity ->
                eventAttendeeEntity.toEventAttendee()
            }
        )
    }

    override suspend fun updateEvent(
        event: Event,
        photos: List<File>,
        deletedAttendees: List<EventAttendee>,
        deletedPhotos: List<Photo>
    ): Result<Event> {
        return try {
            val eventDocument = eventDao.getEvent(event.id)
            eventDao.updateEvent(
                event = event,
                isAddedOnRemote = eventDocument.isAddedOnRemote == 1L,
                shouldBeUpdated = true
            )
            deletedAttendees.forEach {
                eventAttendeeDao.deleteEventAttendee(it.userId)
            }
            event.attendees.forEach {
                eventAttendeeDao.insertAttendee(it)
            }
            deletedPhotos.forEach {
                photoDao.markPhotoForDelete(
                    photoKey = it.key,
                    shouldBeDeleted = true
                )
            }
            if (eventDocument.isAddedOnRemote == 1L) {
                val result =
                    eventApi.updateEvent(event.toUpdateEventRequestDto(true, deletedPhotos), photos)
                eventDao.updateEvent(
                    event = result.toEvent(),
                    isAddedOnRemote = true,
                    shouldBeUpdated = false
                )
                result.attendees.forEach { attendeeDto ->
                    eventAttendeeDao.insertAttendee(attendeeDto.toEventAttendee())
                }
                deletedPhotos.forEach {
                    photoDao.deletePhoto(
                        key = it.key
                    )
                }
                result.photos.forEach { photoDto ->
                    photoDao.insetPhoto(photoDto.toPhoto(event.id))
                }
                Result.Success(result.toEvent())
            } else {
                Result.Success(event)
            }
        } catch (ex: UnknownHostException) {
            Result.Success(event)
        } catch (ex: Exception) {
            handleApiError(ex)
        }
    }

    override suspend fun deleteEvent(eventId: String): Result<Unit> {
        return try {
            eventDao.markEventForDelete(eventId)
            eventApi.deleteEvent(eventId)
            eventDao.deleteEvent(eventId)
            Result.Success(Unit)
        } catch (ex: UnknownHostException) {
            Result.Success(Unit)
        } catch (ex: Exception) {
            handleApiError(ex)
        }
    }
}