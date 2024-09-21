package com.taskyproject.tasky.data.local.event

import com.taskyproject.tasky.domain.model.Event
import kotlinx.coroutines.flow.Flow
import taskydatabase.EventEntity

interface EventDao {
    suspend fun insertEvent(
        event: Event,
        shouldBeDeleted: Boolean = false,
        shouldBeUpdated: Boolean = false,
        isAddedOnRemote: Boolean = false
    )
    suspend fun getEvent(id: String): EventEntity
    fun listEvents(): Flow<List<EventEntity>>
    suspend fun updateEvent(
        event: Event,
        shouldBeDeleted: Boolean = false,
        shouldBeUpdated: Boolean = false,
        isAddedOnRemote: Boolean = false,
    )
    suspend fun markEventForDelete(eventId: String)
    suspend fun deleteEvent(eventId: String)
}