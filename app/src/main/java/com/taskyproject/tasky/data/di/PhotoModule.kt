package com.taskyproject.tasky.data.di

import com.taskyproject.Database
import com.taskyproject.tasky.data.local.photo.PhotoDao
import com.taskyproject.tasky.data.local.photo.PhotoDaoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PhotoModule {

    @Provides
    @Singleton
    fun providePhotoDao(db: Database): PhotoDao = PhotoDaoImpl(db.photoEntityQueries)
}