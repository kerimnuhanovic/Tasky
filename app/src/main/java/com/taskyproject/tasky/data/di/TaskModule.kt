package com.taskyproject.tasky.data.di

import com.taskyproject.Database
import com.taskyproject.tasky.data.local.task.TaskDao
import com.taskyproject.tasky.data.local.task.TaskDaoImpl
import com.taskyproject.tasky.data.network.task.TaskApi
import com.taskyproject.tasky.data.network.task.TaskApiImpl
import com.taskyproject.tasky.data.repository.TaskRepositoryImpl
import com.taskyproject.tasky.domain.preferences.Preferences
import com.taskyproject.tasky.domain.repository.TaskRepository
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
object TaskModule {

    @Provides
    @Singleton
    fun provideTaskDao(db: Database): TaskDao = TaskDaoImpl(db.taskEntityQueries)

    @Provides
    @Singleton
    fun provideTaskApi(preferences: Preferences): TaskApi =
        TaskApiImpl(client = HttpClient(Android) {
            install(Logging) {
                level = LogLevel.ALL
            }
            install(ContentNegotiation) {
                json()
            }
        }, preferences = preferences)

    @Provides
    @Singleton
    fun provideTaskRepository(taskDao: TaskDao, taskApi: TaskApi): TaskRepository =
        TaskRepositoryImpl(taskDao, taskApi)
}