package com.taskyproject.tasky.data.network.attendee

import com.taskyproject.tasky.data.network.dto.AttendeeDto

interface AttendeeApi {
    suspend fun fetchAttendee(email: String): AttendeeDto
}