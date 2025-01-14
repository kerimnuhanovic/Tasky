package com.taskyproject.tasky.data.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.taskyproject.tasky.data.preferences.PreferencesImpl
import com.taskyproject.tasky.domain.preferences.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {
    @Provides
    @Singleton
    fun provideSharedPreferences(application: Application): SharedPreferences =
        application.getSharedPreferences("shared_pref", Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun providePreferences(sharedPreferences: SharedPreferences): Preferences =
        PreferencesImpl(sharedPreferences)
}