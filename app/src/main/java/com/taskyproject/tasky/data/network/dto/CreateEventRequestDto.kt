package com.taskyproject.tasky.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class CreateEventRequestDto(
    val id: String,
    val title: String,
    val description: String,
    val from: Long,
    val to: Long,
    val remindAt: Long,
    val attendeeIds: List<String>
)
