package com.taskyproject.tasky.data.mapper

import com.taskyproject.tasky.data.network.dto.EventAttendeeDto
import com.taskyproject.tasky.domain.model.EventAttendee
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun EventAttendeeDto.toEventAttendee(): EventAttendee {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS'Z'")
    return EventAttendee(
        email = email,
        fullName = fullName,
        userId = userId,
        eventId = eventId,
        isGoing = isGoing,
        remindAt = remindAt,
        createdAt = LocalDateTime.parse(createdAt, inputFormatter)
    )
}