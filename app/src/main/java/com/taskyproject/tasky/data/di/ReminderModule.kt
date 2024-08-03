package com.taskyproject.tasky.data.di

import com.taskyproject.Database
import com.taskyproject.tasky.data.local.reminder.ReminderDao
import com.taskyproject.tasky.data.local.reminder.ReminderDaoImpl
import com.taskyproject.tasky.data.network.reminder.ReminderApi
import com.taskyproject.tasky.data.network.reminder.ReminderApiImpl
import com.taskyproject.tasky.data.repository.ReminderRepositoryImpl
import com.taskyproject.tasky.domain.preferences.Preferences
import com.taskyproject.tasky.domain.repository.ReminderRepository
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
object ReminderModule {

    @Provides
    @Singleton
    fun provideReminderDao(db: Database): ReminderDao = ReminderDaoImpl(db.reminderEntityQueries)

    @Provides
    @Singleton
    fun provideReminderApi(preferences: Preferences): ReminderApi = ReminderApiImpl(
        client = HttpClient(Android) {
            install(Logging) {
                level = LogLevel.ALL
            }
            install(ContentNegotiation) {
                json()
            }
        },
        preferences = preferences
    )

    @Provides
    @Singleton
    fun provideReminderRepository(
        reminderDao: ReminderDao,
        reminderApi: ReminderApi
    ): ReminderRepository = ReminderRepositoryImpl(
        reminderDao = reminderDao,
        reminderApi = reminderApi
    )
}