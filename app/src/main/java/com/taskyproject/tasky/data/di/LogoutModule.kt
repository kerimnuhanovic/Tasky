package com.taskyproject.tasky.data.di

import com.taskyproject.tasky.data.network.logout.LogoutApi
import com.taskyproject.tasky.data.network.logout.LogoutApiImpl
import com.taskyproject.tasky.data.repository.LogoutRepositoryImpl
import com.taskyproject.tasky.domain.preferences.Preferences
import com.taskyproject.tasky.domain.repository.LogoutRepository
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
object LogoutModule {

    @Provides
    @Singleton
    fun provideLogoutApi(preferences: Preferences): LogoutApi = LogoutApiImpl(
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
    fun provideLogoutRepository(logoutApi: LogoutApi): LogoutRepository = LogoutRepositoryImpl(logoutApi)
}