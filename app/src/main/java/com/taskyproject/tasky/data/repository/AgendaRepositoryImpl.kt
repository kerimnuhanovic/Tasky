package com.taskyproject.tasky.data.repository

import com.taskyproject.tasky.data.local.event.EventDao
import com.taskyproject.tasky.data.local.eventattendee.EventAttendeeDao
import com.taskyproject.tasky.data.local.photo.PhotoDao
import com.taskyproject.tasky.data.local.reminder.ReminderDao
import com.taskyproject.tasky.data.local.task.TaskDao
import com.taskyproject.tasky.data.mapper.toEvent
import com.taskyproject.tasky.data.mapper.toReminder
import com.taskyproject.tasky.data.mapper.toTask
import com.taskyproject.tasky.data.network.agenda.AgendaApi
import com.taskyproject.tasky.domain.model.AgendaItem
import com.taskyproject.tasky.domain.model.Event
import com.taskyproject.tasky.domain.repository.AgendaRepository
import com.taskyproject.tasky.domain.util.Result
import com.taskyproject.tasky.domain.util.handleApiError
import com.taskyproject.tasky.presentation.util.millisToLocalDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.time.LocalDate
import javax.inject.Inject

class AgendaRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao,
    private val reminderDao: ReminderDao,
    private val eventDao: EventDao,
    private val photoDao: PhotoDao,
    private val eventAttendeeDao: EventAttendeeDao,
    private val agendaApi: AgendaApi
) : AgendaRepository {
    override suspend fun listAgenda(date: LocalDate): Flow<List<AgendaItem>> {
        val tasksFLow = taskDao.listTasks()
        val remindersFlow = reminderDao.listReminders()
        val eventsFlow = eventDao.listEvents()
        return combine(
            tasksFLow,
            remindersFlow,
            eventsFlow
        ) { taskEntities, reminderEntities, eventEntities ->
            val tasks = taskEntities.map {
                it.toTask()
            }
            val reminders = reminderEntities.map {
                it.toReminder()
            }
            val events = eventEntities.map { eventEntity ->
                Event(
                    id = eventEntity.id,
                    isUserEventCreator = eventEntity.isUserEventCreator == 1L,
                    title = eventEntity.title,
                    description = eventEntity.description,
                    from = eventEntity.fromTime,
                    to = eventEntity.toTime,
                    host = eventEntity.host,
                    remindAt = eventEntity.remindAt,
                    photos = emptyList(),
                    attendees = emptyList()
                )
            }
            val agendaItems = tasks + reminders + events
            agendaItems.filter {
                millisToLocalDate(it.getStartTime()).isEqual(date)
            }.sortedBy { it.getStartTime() }
        }
    }

    override suspend fun nuke() {
        eventDao.nuke()
        reminderDao.nuke()
        taskDao.nuke()
    }

    override suspend fun fetchFullAgendaAndStoreToLocalDB(): Result<Unit> {
        return try {
            val agenda = agendaApi.getFullAgenda()
            agenda.events.forEach {
                val event = it.toEvent()
                eventDao.insertEvent(event, isAddedOnRemote = true)
                event.attendees.forEach { attendee ->
                    eventAttendeeDao.insertAttendee(attendee)
                }
                event.photos.forEach { photo ->
                    photoDao.insetPhoto(photo)
                }
            }
            agenda.reminders.forEach {
                reminderDao.insertReminder(it.toReminder(), isAddedOnRemote = true)
            }
            agenda.tasks.forEach {
                taskDao.insertTask(it.toTask(), isAddedOnRemote = true)
            }
            Result.Success(Unit)
        } catch (ex: Exception) {
            handleApiError(ex)
        }
    }
}