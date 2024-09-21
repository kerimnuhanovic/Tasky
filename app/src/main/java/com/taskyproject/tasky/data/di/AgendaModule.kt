package com.taskyproject.tasky.data.di

import com.taskyproject.tasky.data.local.event.EventDao
import com.taskyproject.tasky.data.local.reminder.ReminderDao
import com.taskyproject.tasky.data.local.task.TaskDao
import com.taskyproject.tasky.data.repository.AgendaRepositoryImpl
import com.taskyproject.tasky.domain.repository.AgendaRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AgendaModule {

    @Provides
    @Singleton
    fun provideAgendaRepository(
        taskDao: TaskDao,
        reminderDao: ReminderDao,
        eventDao: EventDao
    ): AgendaRepository = AgendaRepositoryImpl(taskDao, reminderDao, eventDao)
}