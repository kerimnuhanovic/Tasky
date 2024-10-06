package com.taskyproject.tasky.data.mapper

import com.taskyproject.tasky.data.network.dto.UpdateEventRequestDto
import com.taskyproject.tasky.domain.model.Event
import com.taskyproject.tasky.domain.model.Photo
import taskydatabase.EventAttendeeEntity
import taskydatabase.EventEntity
import taskydatabase.PhotoEntity

fun Event.toUpdateEventRequestDto(isGoing: Boolean, deletedPhotos: List<Photo>): UpdateEventRequestDto {
    return UpdateEventRequestDto(
        id = id,
        title = title,
        description = description,
        from = from,
        to = to,
        remindAt = remindAt,
        attendeeIds = attendees.map { attendee -> attendee.userId },
        deletedPhotoKeys = deletedPhotos.map {
            it.key
        },
        isGoing = isGoing
    )
}

fun EventEntity.toUpdateEventRequestDto(isGoing: Boolean, deletedPhotos: List<PhotoEntity>, eventAttendees: List<EventAttendeeEntity>): UpdateEventRequestDto {
    return UpdateEventRequestDto(
        id = id,
        title = title,
        description = description,
        from = fromTime,
        to = toTime,
        remindAt = remindAt,
        attendeeIds = eventAttendees.map { attendee -> attendee.userId },
        deletedPhotoKeys = deletedPhotos.map {
            it.key
        },
        isGoing = isGoing
    )
}