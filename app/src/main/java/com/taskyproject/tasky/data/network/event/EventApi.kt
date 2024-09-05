package com.taskyproject.tasky.data.network.event

import com.taskyproject.tasky.data.network.dto.EventDto
import com.taskyproject.tasky.data.network.dto.UpdateEventRequestDto
import com.taskyproject.tasky.domain.model.Event
import java.io.File

interface EventApi {
    suspend fun createEvent(event: Event, eventPhotos: List<File>): EventDto
    suspend fun updateEvent(eventRequestDto: UpdateEventRequestDto, eventPhotos: List<File>): EventDto
}