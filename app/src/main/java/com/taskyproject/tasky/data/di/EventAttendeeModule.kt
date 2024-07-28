package com.taskyproject.tasky.data.di

import com.taskyproject.Database
import com.taskyproject.tasky.data.local.eventattendee.EventAttendeeDao
import com.taskyproject.tasky.data.local.eventattendee.EventAttendeeDaoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EventAttendeeModule {

    @Singleton
    @Provides
    fun provideEventAttendeeDao(db: Database): EventAttendeeDao =
        EventAttendeeDaoImpl(db.eventAttendeeEntityQueries)
}