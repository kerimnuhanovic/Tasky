package com.taskyproject.tasky.domain.repository

import com.taskyproject.tasky.domain.model.Attendee
import com.taskyproject.tasky.domain.util.Result

interface AttendeeRepository {
    suspend fun getAttendee(email: String): Result<Attendee>
}