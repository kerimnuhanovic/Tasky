package com.taskyproject.tasky.data.network.dto

import com.taskyproject.tasky.domain.model.Attendee
import kotlinx.serialization.Serializable

@Serializable
data class AttendeeDto(
    val doesUserExist: Boolean,
    val attendee: Attendee?
)
