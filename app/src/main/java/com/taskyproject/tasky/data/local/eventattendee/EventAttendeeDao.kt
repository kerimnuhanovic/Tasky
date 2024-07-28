package com.taskyproject.tasky.data.local.eventattendee

import com.taskyproject.tasky.domain.model.EventAttendee

interface EventAttendeeDao {
    suspend fun insertAttendee(eventAttendee: EventAttendee)
}