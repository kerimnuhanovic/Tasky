package com.taskyproject.tasky.data.local.event

import com.taskyproject.tasky.domain.model.Event
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

}