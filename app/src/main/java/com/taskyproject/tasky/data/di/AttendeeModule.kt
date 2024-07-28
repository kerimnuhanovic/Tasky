package com.taskyproject.tasky.data.di

import com.taskyproject.Database
import com.taskyproject.tasky.data.local.attendee.AttendeeDao
import com.taskyproject.tasky.data.local.attendee.AttendeeDaoImpl
import com.taskyproject.tasky.data.network.AttendeeApi
import com.taskyproject.tasky.data.network.AttendeeApiImpl
import com.taskyproject.tasky.data.repository.AttendeeRepositoryImpl
import com.taskyproject.tasky.domain.preferences.Preferences
import com.taskyproject.tasky.domain.repository.AttendeeRepository
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
object AttendeeModule {

    @Provides
    @Singleton
    fun provideAttendeeDao(db: Database): AttendeeDao = AttendeeDaoImpl(db.attendeeEntityQueries)

    @Provides
    @Singleton
    fun provideAttendeeApi(preferences: Preferences): AttendeeApi = AttendeeApiImpl(
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

    @Provides
    @Singleton
    fun provideAttendeeRepository(
        attendeeApi: AttendeeApi,
        attendeeDao: AttendeeDao
    ): AttendeeRepository = AttendeeRepositoryImpl(attendeeApi, attendeeDao)


}