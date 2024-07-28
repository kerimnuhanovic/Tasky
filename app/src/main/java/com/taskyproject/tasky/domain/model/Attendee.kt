package com.taskyproject.tasky.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Attendee(
    val email: String,
    val fullName: String,
    val userId: String
)
