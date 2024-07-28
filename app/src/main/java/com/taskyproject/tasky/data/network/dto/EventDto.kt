package com.taskyproject.tasky.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class EventDto(
    val id: String,
    val isUserEventCreator: Boolean,
    val title: String,
    val description: String,
    val from: Long,
    val to: Long,
    val host: String,
    val remindAt: Long,
    val photos: List<PhotoDto>,
    val attendees: List<EventAttendeeDto>
)
