package com.taskyproject.tasky.data.di

import com.taskyproject.tasky.data.network.register.RegisterApi
import com.taskyproject.tasky.data.network.register.RegisterApiImpl
import com.taskyproject.tasky.data.repository.RegisterRepositoryImpl
import com.taskyproject.tasky.domain.repository.RegisterRepository
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
object RegisterModule {

    @Provides
    @Singleton
    fun provideRegisterApi(): RegisterApi = RegisterApiImpl(
        client = HttpClient(Android) {
            install(Logging) {
                level = LogLevel.ALL
            }
            install(ContentNegotiation) {
                json()
            }
        }
    )

    @Singleton
    @Provides
    fun provideRegisterRepository(registerApi: RegisterApi): RegisterRepository = RegisterRepositoryImpl(registerApi)
}