package com.taskyproject.tasky.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.taskyproject.tasky.data.local.event.EventDao
import com.taskyproject.tasky.data.local.eventattendee.EventAttendeeDao
import com.taskyproject.tasky.data.local.photo.PhotoDao
import com.taskyproject.tasky.data.local.reminder.ReminderDao
import com.taskyproject.tasky.data.local.task.TaskDao
import com.taskyproject.tasky.data.mapper.toEvent
import com.taskyproject.tasky.data.mapper.toPhoto
import com.taskyproject.tasky.data.mapper.toReminder
import com.taskyproject.tasky.data.mapper.toTask
import com.taskyproject.tasky.data.mapper.toUpdateEventRequestDto
import com.taskyproject.tasky.data.network.agenda.AgendaApi
import com.taskyproject.tasky.data.network.dto.Credentials
import com.taskyproject.tasky.data.network.event.EventApi
import com.taskyproject.tasky.data.network.login.LoginApi
import com.taskyproject.tasky.data.network.reminder.ReminderApi
import com.taskyproject.tasky.data.network.task.TaskApi
import com.taskyproject.tasky.domain.preferences.Preferences
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class SyncDataWorker @AssistedInject constructor(
    @Assisted private val loginApi: LoginApi,
    @Assisted private val agendaApi: AgendaApi,
    @Assisted private val eventApi: EventApi,
    @Assisted private val reminderApi: ReminderApi,
    @Assisted private val taskApi: TaskApi,
    @Assisted private val preferences: Preferences,
    @Assisted private val eventDao: EventDao,
    @Assisted private val taskDao: TaskDao,
    @Assisted private val reminderDao: ReminderDao,
    @Assisted private val eventAttendeeDao: EventAttendeeDao,
    @Assisted private val photoDao: PhotoDao,
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
        return try {

            //refresh token
            val email = preferences.readUserEmail()
            val password = preferences.readUserPassword()
            if (email == null || password == null) {
                Result.failure()
            }
            val result = loginApi.login(Credentials(email!!, password!!))
            preferences.saveToken(result.accessToken)

            //sync events
            val eventsForDelete = eventDao.listEventsForDelete()
            val tasksForDelete = taskDao.listTasksForDelete()
            val remindersForDelete = reminderDao.listRemindersForDelete()
            agendaApi.deleteAgendaItems(
                eventsIds = eventsForDelete,
                tasksIds = tasksForDelete,
                remindersIds = remindersForDelete
            )

            val eventsToCreate = eventDao.listEventsForCreate()
            val eventsToCreateAttendees = eventsToCreate.map {
                eventAttendeeDao.getEventAttendees(it.id)
            }
            eventsToCreate.forEachIndexed { index, it ->
                eventApi.createEvent(
                    event = it.toEvent(photos = emptyList(), eventAttendeesEntity = eventsToCreateAttendees[index]),
                    eventPhotos = emptyList()
                )
                eventDao.markEventAsAddedOnRemote(it.id)
            }

            val eventsToUpdate = eventDao.listEventsForUpdate()
            val eventsToUpdateAttendees = eventsToUpdate.map {
                eventAttendeeDao.getEventAttendees(it.id)
            }
            val eventToUpdatePhotosForDelete = eventsToUpdate.map {
                photoDao.getEventPhotosForDelete(it.id)
            }
            eventsToUpdate.forEachIndexed { index, it ->
                val attendees = eventsToUpdateAttendees[index]
                val isGoing = attendees.find {
                    preferences.readUserId() == it.userId
                }?.isGoing
                eventApi.updateEvent(
                    eventRequestDto = it.toUpdateEventRequestDto(
                        isGoing = isGoing != 0L,
                        deletedPhotos = eventToUpdatePhotosForDelete[index],
                        eventAttendees = attendees
                    ),
                    eventPhotos = emptyList()
                )
                eventDao.markEventAsUpdated(it.id)
                photoDao.deleteEventPhotos(it.id)
            }


            //sync reminders
            val remindersToCreate = reminderDao.listRemindersForCreate()
            remindersToCreate.forEach {
                reminderApi.createReminder(
                    reminder = it.toReminder()
                )
                reminderDao.markReminderAsAddedOnRemote(it.id)
            }

            val remindersForUpdate = reminderDao.listRemindersForUpdate()
            remindersForUpdate.forEach {
                reminderApi.updateReminder(
                    reminder = it.toReminder()
                )
                reminderDao.markReminderAsUpdated(it.id)
            }


            //sync tasks
            val tasksForCreate = taskDao.listTasksForCreate()
            tasksForCreate.forEach {
                taskApi.createTask(
                    task = it.toTask()
                )
                taskDao.markTaskAsAddedOnRemote(it.id)
            }

            val tasksForUpdate = taskDao.listTasksForUpdate()
            tasksForUpdate.forEach {
                taskApi.updateTask(
                    task = it.toTask()
                )
                taskDao.markTaskAsUpdated(it.id)
            }


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

            Result.success()
        } catch (ex: Exception) {
            println("blabla-")
            println(ex)
            Result.failure()
        }
    }

}