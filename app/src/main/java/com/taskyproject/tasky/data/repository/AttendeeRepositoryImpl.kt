package com.taskyproject.tasky.data.repository

import com.taskyproject.tasky.R
import com.taskyproject.tasky.data.local.attendee.AttendeeDao
import com.taskyproject.tasky.data.mapper.toAttendee
import com.taskyproject.tasky.data.network.attendee.AttendeeApi
import com.taskyproject.tasky.domain.model.Attendee
import com.taskyproject.tasky.domain.repository.AttendeeRepository
import com.taskyproject.tasky.domain.util.Result
import com.taskyproject.tasky.domain.util.handleApiError
import javax.inject.Inject

class AttendeeRepositoryImpl @Inject constructor(
    private val attendeeApi: AttendeeApi,
    private val attendeeDao: AttendeeDao
) : AttendeeRepository {
    override suspend fun getAttendee(email: String): Result<Attendee> {
        try {
            val localAttendee = attendeeDao.getAttendee(email)
            if (localAttendee != null) {
                return Result.Success(localAttendee.toAttendee())
            }
            val result = attendeeApi.fetchAttendee(email)
            if (!result.doesUserExist) {
                return Result.Failure(errorMessageId = R.string.not_existing_user)
            } else {
                val attendee = result.toAttendee()
                attendeeDao.insertAttendee(attendee.email, attendee.fullName, attendee.userId)
                return Result.Success(attendee)
            }
        } catch (ex: Exception) {
            return handleApiError(ex)
        }
    }
}