package com.taskyproject.tasky.data.local.event

import com.taskyproject.tasky.domain.model.Event

interface EventDao {
    suspend fun insertEvent(
        event: Event,
        shouldBeDeleted: Boolean = false,
        shouldBeUpdated: Boolean = false,
        isAddedOnRemote: Boolean = false
    )
}