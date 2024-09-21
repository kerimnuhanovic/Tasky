package com.taskyproject.tasky.data.network.reminder

import com.taskyproject.tasky.data.mapper.toReminderDto
import com.taskyproject.tasky.data.mapper.toTaskDto
import com.taskyproject.tasky.data.network.constants.AUTHORIZATION
import com.taskyproject.tasky.data.network.constants.EVENT_ID
import com.taskyproject.tasky.data.network.constants.HttpRoutes
import com.taskyproject.tasky.data.network.constants.REMINDER_ID
import com.taskyproject.tasky.data.network.constants.X_API_KEY
import com.taskyproject.tasky.domain.model.Reminder
import com.taskyproject.tasky.domain.preferences.Preferences
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

class ReminderApiImpl @Inject constructor(
    private val client: HttpClient,
    private val preferences: Preferences
) : ReminderApi {
    override suspend fun createReminder(reminder: Reminder) {
        client.post {
            url(HttpRoutes.REMINDER)
            headers {
                append(X_API_KEY, "619417673")
                append(AUTHORIZATION, preferences.readToken()!!)
            }
            contentType(ContentType.Application.Json)
            setBody(reminder.toReminderDto())
        }
    }

    override suspend fun updateReminder(reminder: Reminder) {
        client.put {
            url(HttpRoutes.REMINDER)
            headers {
                append(X_API_KEY, "619417673")
                append(AUTHORIZATION, preferences.readToken()!!)
            }
            contentType(ContentType.Application.Json)
            setBody(reminder.toReminderDto())
        }
    }

    override suspend fun deleteReminder(reminderId: String) {
        client.delete {
            url(HttpRoutes.REMINDER)
            parameter(REMINDER_ID, reminderId)
            headers {
                append(X_API_KEY, "619417673")
                append(AUTHORIZATION, preferences.readToken()!!)
            }
        }
    }
}