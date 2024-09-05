package com.taskyproject.tasky.data.local.eventattendee

import com.taskyproject.tasky.domain.model.EventAttendee
import taskydatabase.EventAttendeeEntity

interface EventAttendeeDao {
    suspend fun insertAttendee(eventAttendee: EventAttendee)
    suspend fun getEventAttendees(eventId: String): List<EventAttendeeEntity>
    suspend fun deleteEventAttendee(userId: String)
}