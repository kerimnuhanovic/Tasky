package com.taskyproject.tasky.data.di

import com.taskyproject.tasky.data.local.event.EventDao
import com.taskyproject.tasky.data.local.eventattendee.EventAttendeeDao
import com.taskyproject.tasky.data.local.photo.PhotoDao
import com.taskyproject.tasky.data.local.reminder.ReminderDao
import com.taskyproject.tasky.data.local.task.TaskDao
import com.taskyproject.tasky.data.network.agenda.AgendaApi
import com.taskyproject.tasky.data.network.agenda.AgendaApiImpl
import com.taskyproject.tasky.data.repository.AgendaRepositoryImpl
import com.taskyproject.tasky.domain.preferences.Preferences
import com.taskyproject.tasky.domain.repository.AgendaRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AgendaModule {

    @Provides
    @Singleton
    fun provideAgendaRepository(
        taskDao: TaskDao,
        reminderDao: ReminderDao,
        eventDao: EventDao,
        photoDao: PhotoDao,
        eventAttendeeDao: EventAttendeeDao,
        agendaApi: AgendaApi
    ): AgendaRepository = AgendaRepositoryImpl(taskDao, reminderDao, eventDao, photoDao, eventAttendeeDao, agendaApi)

    @Provides
    @Singleton
    fun provideAgendaApi(preferences: Preferences): AgendaApi = AgendaApiImpl(
        client = HttpClient(Android) {
            install(Logging) {
                level = LogLevel.ALL
            }
            install(ContentNegotiation) {
                json()
            }
        },
        preferences
    )
}