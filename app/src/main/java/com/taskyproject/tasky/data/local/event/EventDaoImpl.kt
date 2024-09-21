package com.taskyproject.tasky.data.local.event

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.taskyproject.tasky.domain.model.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import taskydatabase.EventEntity
import taskydatabase.EventEntityQueries
import javax.inject.Inject

class EventDaoImpl @Inject constructor(
    private val eventQueries: EventEntityQueries,
) : EventDao {
    override suspend fun insertEvent(
        event: Event,
        shouldBeDeleted: Boolean,
        shouldBeUpdated: Boolean,
        isAddedOnRemote: Boolean,
    ) {
        eventQueries.insertEvent(
            id = event.id,
            isUserEventCreator = if (event.isUserEventCreator) 1 else 0,
            title = event.title,
            description = event.description,
            fromTime = event.from,
            toTime = event.to,
            host = event.host,
            remindAt = event.remindAt,
            shouldBeDeleted = if (shouldBeDeleted) 1 else 0,
            shouldBeUpdated = if (shouldBeUpdated) 1 else 0,
            isAddedOnRemote = if (isAddedOnRemote) 1 else 0
        )
    }

    override suspend fun getEvent(id: String): EventEntity {
        return eventQueries.getEvent(id).executeAsOne()
    }

    override fun listEvents(): Flow<List<EventEntity>> {
        return eventQueries.listEvents().asFlow().mapToList(Dispatchers.IO)
    }

    override suspend fun updateEvent(
        event: Event,
        shouldBeDeleted: Boolean,
        shouldBeUpdated: Boolean,
        isAddedOnRemote: Boolean
    ) {
        eventQueries.updateEvent(
            id = event.id,
            title = event.title,
            description = event.description,
            fromTime = event.from,
            toTime = event.to,
            remindAt = event.remindAt,
            host = event.host,
            isUserEventCreator = if (event.isUserEventCreator) 1 else 0,
            shouldBeDeleted = if (shouldBeDeleted) 1 else 0,
            shouldBeUpdated = if (shouldBeUpdated) 1 else 0,
            isAddedOnRemote = if (isAddedOnRemote) 1 else 0,
        )
    }

    override suspend fun markEventForDelete(eventId: String) {
        eventQueries.markEventForDelete(eventId)
    }

    override suspend fun deleteEvent(eventId: String) {
        eventQueries.deleteEvent(eventId)
    }

}