package com.taskyproject.tasky.data.di

import com.taskyproject.Database
import com.taskyproject.tasky.data.local.event.EventDao
import com.taskyproject.tasky.data.local.event.EventDaoImpl
import com.taskyproject.tasky.data.local.eventattendee.EventAttendeeDao
import com.taskyproject.tasky.data.local.photo.PhotoDao
import com.taskyproject.tasky.data.network.event.EventApi
import com.taskyproject.tasky.data.network.event.EventApiImpl
import com.taskyproject.tasky.data.repository.EventRepositoryImpl
import com.taskyproject.tasky.domain.preferences.Preferences
import com.taskyproject.tasky.domain.repository.EventRepository
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
object EventModule {

    @Singleton
    @Provides
    fun provideEventDao(db: Database): EventDao = EventDaoImpl(db.eventEntityQueries)

    @Singleton
    @Provides
    fun provideEventApi(preferences: Preferences): EventApi =
        EventApiImpl(client = HttpClient(Android) {
            install(Logging) {
                level = LogLevel.ALL
            }
            install(ContentNegotiation) {
                json()
            }
        }, preferences = preferences)

    @Singleton
    @Provides
    fun provideEventRepository(
        eventApi: EventApi,
        eventDao: EventDao,
        photoDao: PhotoDao,
        eventAttendeeDao: EventAttendeeDao,
        preferences: Preferences
    ): EventRepository = EventRepositoryImpl(
        eventApi = eventApi,
        eventDao = eventDao,
        photoDao = photoDao,
        eventAttendeeDao = eventAttendeeDao,
        preferences = preferences
    )
}