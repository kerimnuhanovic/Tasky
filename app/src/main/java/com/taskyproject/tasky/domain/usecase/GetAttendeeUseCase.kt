package com.taskyproject.tasky.domain.usecase

import com.taskyproject.tasky.domain.model.Attendee
import com.taskyproject.tasky.domain.repository.AttendeeRepository
import com.taskyproject.tasky.domain.util.Result
import javax.inject.Inject

class GetAttendeeUseCase @Inject constructor(
    private val attendeeRepository: AttendeeRepository
) {
    suspend operator fun invoke(email: String): Result<Attendee> {
        return attendeeRepository.getAttendee(email)
    }
}