package com.taskyproject.tasky.data.mapper

import com.taskyproject.tasky.data.network.dto.CreateEventRequestDto
import com.taskyproject.tasky.domain.model.Event
import java.util.UUID

fun Event.toCreateEventRequestDto(): CreateEventRequestDto {
    return CreateEventRequestDto(
        id = id,
        title = title,
        description = description,
        from = from,
        to = to,
        remindAt = remindAt,
        attendeeIds = attendees.map { attendee -> attendee.userId }
    )
}