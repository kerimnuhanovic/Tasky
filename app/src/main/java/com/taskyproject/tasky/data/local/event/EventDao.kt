package com.taskyproject.tasky.data.local.event

import com.taskyproject.tasky.domain.model.Event
import taskydatabase.EventEntity

interface EventDao {
    suspend fun insertEvent(
        event: Event,
        shouldBeDeleted: Boolean = false,
        shouldBeUpdated: Boolean = false,
        isAddedOnRemote: Boolean = false
    )
    suspend fun getEvent(id: String): EventEntity
    suspend fun updateEvent(
        event: Event,
        shouldBeDeleted: Boolean = false,
        shouldBeUpdated: Boolean = false,
        isAddedOnRemote: Boolean = false,
    )
}