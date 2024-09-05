package com.taskyproject.tasky.data.mapper

import com.taskyproject.tasky.data.network.dto.UpdateEventRequestDto
import com.taskyproject.tasky.domain.model.Event
import com.taskyproject.tasky.domain.model.Photo

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