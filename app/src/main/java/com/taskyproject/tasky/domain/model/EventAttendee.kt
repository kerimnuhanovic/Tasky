package com.taskyproject.tasky.domain.model

import java.time.LocalDateTime

data class EventAttendee(
    val email: String,
    val fullName: String,
    val userId: String,
    val eventId: String,
    val isGoing: Boolean,
    val remindAt: Long?,
    val createdAt: LocalDateTime
)
