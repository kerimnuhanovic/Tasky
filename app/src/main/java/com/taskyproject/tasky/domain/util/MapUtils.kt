package com.taskyproject.tasky.domain.util

import com.taskyproject.tasky.domain.model.Event
import com.taskyproject.tasky.domain.model.EventAttendee
import com.taskyproject.tasky.domain.model.Photo
import com.taskyproject.tasky.domain.model.Reminder
import com.taskyproject.tasky.domain.model.Task
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId

fun prepareEventRequest(
    id: String,
    title: String,
    description: String,
    fromTime: LocalTime,
    fromDate: LocalDate,
    toTime: LocalTime,
    toDate: LocalDate,
    host: String,
    remindBefore: String,
    attendees: List<EventAttendee>,
    photos: List<Photo>
): Event {
    return Event(
        id = id,
        title = title,
        description = description,
        from = LocalDateTime.of(fromDate, fromTime).atZone(ZoneId.systemDefault()).toInstant()
            .toEpochMilli(),
        to = LocalDateTime.of(toDate, toTime).atZone(ZoneId.systemDefault()).toInstant()
            .toEpochMilli(),
        host = host,
        remindAt = LocalDateTime.of(fromDate, fromTime).minusMinutes(remindBefore.toLong())
            .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
        isUserEventCreator = true,
        attendees = attendees.map { attendee ->
            EventAttendee(
                email = attendee.email,
                fullName = attendee.fullName,
                userId = attendee.userId,
                eventId = id,
                isGoing = true,
                remindAt = LocalDateTime.of(toDate, toTime).minusMinutes(remindBefore.toLong())
                    .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
                createdAt = attendee.createdAt
            )
        },
        photos = photos
    )
}

fun prepareTaskRequest(
    id: String,
    title: String,
    description: String,
    time: LocalTime,
    date: LocalDate,
    remindBefore: String
): Task {
    return Task(
        id = id,
        title = title,
        description = description,
        time = LocalDateTime.of(date, time).atZone(ZoneId.systemDefault()).toInstant()
            .toEpochMilli(),
        remindAt = LocalDateTime.of(date, time).minusMinutes(remindBefore.toLong())
            .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(),
        isDone = false
    )
}

fun prepareReminderRequest(
    id: String,
    title: String,
    description: String,
    time: LocalTime,
    date: LocalDate,
    remindBefore: String
): Reminder {
    return Reminder(
        id = id,
        title = title,
        description = description,
        time = LocalDateTime.of(date, time).atZone(ZoneId.systemDefault()).toInstant()
            .toEpochMilli(),
        remindAt = LocalDateTime.of(date, time).minusMinutes(remindBefore.toLong())
            .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    )
}