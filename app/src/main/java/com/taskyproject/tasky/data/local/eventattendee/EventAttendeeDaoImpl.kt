package com.taskyproject.tasky.data.local.eventattendee

import com.taskyproject.tasky.domain.model.EventAttendee
import taskydatabase.EventAttendeeEntity
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

    override suspend fun getEventAttendees(eventId: String): List<EventAttendeeEntity> {
        return eventAttendeeQueries.getEventAttendees(eventId).executeAsList()
    }

    override suspend fun deleteEventAttendee(userId: String) {
        eventAttendeeQueries.deleteEventAttendee(userId)
    }

    override suspend fun updateIsUserGoingToEvent(
        userId: String,
        eventId: String,
        isGoing: Boolean
    ) {
        eventAttendeeQueries.updateIsUserGoingToEvent(
            isGoing = if (isGoing) 1L else 0L,
            userId = userId,
            eventId = eventId
        )
    }
}