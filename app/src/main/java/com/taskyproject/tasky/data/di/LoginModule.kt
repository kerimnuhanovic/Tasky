package com.taskyproject.tasky.data.di

import com.taskyproject.tasky.data.network.LoginApi
import com.taskyproject.tasky.data.network.LoginApiImpl
import com.taskyproject.tasky.data.repository.LoginRepositoryImpl
import com.taskyproject.tasky.domain.repository.LoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.headers
import io.ktor.serialization.kotlinx.json.json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoginModule {

    @Provides
    @Singleton
    fun provideLoginApi(): LoginApi = LoginApiImpl(
        client = HttpClient(Android) {
            install(Logging) {
                level = LogLevel.ALL
            }
            install(ContentNegotiation) {
                json()
            }
            headers {

            }
        }
    )

    @Provides
    @Singleton
    fun provideLoginRepository(loginApi: LoginApi): LoginRepository = LoginRepositoryImpl(loginApi)
}