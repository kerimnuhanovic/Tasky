package com.taskyproject.tasky

import android.app.Application
import android.content.Context
import androidx.work.Configuration
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.taskyproject.tasky.data.local.event.EventDao
import com.taskyproject.tasky.data.local.eventattendee.EventAttendeeDao
import com.taskyproject.tasky.data.local.photo.PhotoDao
import com.taskyproject.tasky.data.local.reminder.ReminderDao
import com.taskyproject.tasky.data.local.task.TaskDao
import com.taskyproject.tasky.data.network.agenda.AgendaApi
import com.taskyproject.tasky.data.network.event.EventApi
import com.taskyproject.tasky.data.network.login.LoginApi
import com.taskyproject.tasky.data.network.reminder.ReminderApi
import com.taskyproject.tasky.data.network.task.TaskApi
import com.taskyproject.tasky.data.worker.SyncDataWorker
import com.taskyproject.tasky.domain.preferences.Preferences
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class TaskyApp : Application(), Configuration.Provider {

    @Inject
    lateinit var refreshTokenWorkerFactory: RefreshTokenWorkerFactory
    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(refreshTokenWorkerFactory)
            .build()

}

class RefreshTokenWorkerFactory @Inject constructor(
    private val preferences: Preferences,
    private val agendaApi: AgendaApi,
    private val eventApi: EventApi,
    private val reminderApi: ReminderApi,
    private val taskApi: TaskApi,
    private val loginApi: LoginApi,
    private val eventDao: EventDao,
    private val taskDao: TaskDao,
    private val reminderDao: ReminderDao,
    private val eventAttendeeDao: EventAttendeeDao,
    private val photoDao: PhotoDao,
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker = SyncDataWorker(
        preferences = preferences,
        loginApi = loginApi,
        agendaApi = agendaApi,
        eventApi = eventApi,
        reminderApi = reminderApi,
        taskApi = taskApi,
        eventDao = eventDao,
        taskDao = taskDao,
        reminderDao = reminderDao,
        eventAttendeeDao = eventAttendeeDao,
        photoDao = photoDao,
        context = appContext,
        workerParameters = workerParameters
    )

}