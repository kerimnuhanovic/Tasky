package com.taskyproject.tasky.data.local.attendee

import taskydatabase.AttendeeEntity
import taskydatabase.AttendeeEntityQueries
import javax.inject.Inject

class AttendeeDaoImpl @Inject constructor(
    private val queries: AttendeeEntityQueries
) : AttendeeDao {
    override suspend fun getAttendee(email: String): AttendeeEntity? {
        return queries.getAttendeeByEmail(email).executeAsOneOrNull()
    }

    override suspend fun insertAttendee(email: String, fullName: String, userId: String) {
        queries.insertAttendee(userId, fullName, email)
    }

}