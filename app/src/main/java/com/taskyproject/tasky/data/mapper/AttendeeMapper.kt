package com.taskyproject.tasky.data.mapper

import com.taskyproject.tasky.data.network.dto.AttendeeDto
import com.taskyproject.tasky.domain.model.Attendee
import taskydatabase.AttendeeEntity

fun AttendeeEntity.toAttendee() : Attendee {
    return Attendee(
        email = email,
        userId = userId,
        fullName = fullName
    )
}

fun AttendeeDto.toAttendee() : Attendee {
    return Attendee(
        email = attendee!!.email,
        userId = attendee.userId,
        fullName = attendee.fullName
    )
}