package com.taskyproject.tasky.domain.repository

import com.taskyproject.tasky.domain.model.Event
import com.taskyproject.tasky.domain.util.Result
import java.io.File

interface EventRepository {
    suspend fun createEvent(event: Event, photos: List<File>): Result<Event>
}