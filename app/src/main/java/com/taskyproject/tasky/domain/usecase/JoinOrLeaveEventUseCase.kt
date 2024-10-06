package com.taskyproject.tasky.domain.usecase

import com.taskyproject.tasky.domain.repository.EventRepository
import com.taskyproject.tasky.domain.util.Result
import javax.inject.Inject

class JoinOrLeaveEventUseCase @Inject constructor(
    private val eventRepository: EventRepository
) {
    suspend operator fun invoke(eventId: String, isGoing: Boolean): Result<Unit> {
        return eventRepository.joinOrLeaveEvent(eventId, isGoing)
    }
}