package com.taskyproject.tasky.data.local.attendee

import taskydatabase.AttendeeEntity

interface AttendeeDao {
    suspend fun getAttendee(email: String): AttendeeEntity?
    suspend fun insertAttendee(email: String, fullName: String, userId: String,)
}