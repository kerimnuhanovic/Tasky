package com.taskyproject.tasky.data.di

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.taskyproject.Database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideSqlDriver(@ApplicationContext context: Context): SqlDriver {
        return AndroidSqliteDriver(Database.Schema, context, "Database")
    }

    @Provides
    @Singleton
    fun provideDatabase(driver: SqlDriver): Database {
        return Database(driver)
    }
}