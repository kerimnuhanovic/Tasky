package com.taskyproject.tasky.data.mapper

import com.taskyproject.tasky.data.network.dto.EventDto
import com.taskyproject.tasky.domain.model.Event
import com.taskyproject.tasky.domain.model.EventAttendee
import com.taskyproject.tasky.domain.model.Photo
import taskydatabase.EventAttendeeEntity
import taskydatabase.EventEntity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun EventDto.toEvent(): Event {
    return Event(
        id = id,
        isUserEventCreator = isUserEventCreator,
        title = title,
        description = description,
        from = from,
        to = to,
        host = host,
        remindAt = remindAt,
        photos = photos.map { photoDto ->
            Photo(
                key = photoDto.key,
                url = photoDto.url,
                eventId = id
            )
        },
        attendees = attendees.map { attendeeDto ->
            attendeeDto.toEventAttendee()
        }
    )
}

fun EventEntity.toEvent(photos: List<Photo>, eventAttendeesEntity: List<EventAttendeeEntity>): Event {
    return Event(
        id = id,
        title = title,
        description = description,
        isUserEventCreator = isUserEventCreator == 1L,
        from = fromTime,
        to = toTime,
        host = host,
        remindAt = remindAt,
        photos = photos,
        attendees = eventAttendeesEntity.map { eventAttendeeEntity -> eventAttendeeEntity.toEventAttendee() }
    )
}