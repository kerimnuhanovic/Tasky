package com.taskyproject.tasky.data.local.eventattendee

import com.taskyproject.tasky.domain.model.EventAttendee
import taskydatabase.EventAttendeeEntityQueries
import javax.inject.Inject

class EventAttendeeDaoImpl @Inject constructor(
    private val eventAttendeeQueries: EventAttendeeEntityQueries
) : EventAttendeeDao {
    override suspend fun insertAttendee(eventAttendee: EventAttendee) {
        eventAttendeeQueries.insertEventAttendee(
            email = eventAttendee.email,
            fullName = eventAttendee.fullName,
            userId = eventAttendee.userId,
            eventId = eventAttendee.eventId,
            isGoing = if (eventAttendee.isGoing) 1 else 0,
            remindAt = eventAttendee.remindAt!!,
            createdAt = eventAttendee.createdAt.toString()
        )
    }
}