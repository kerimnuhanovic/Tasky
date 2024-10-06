package com.taskyproject.tasky.data.network.agenda

import com.taskyproject.tasky.data.network.constants.AUTHORIZATION
import com.taskyproject.tasky.data.network.constants.HttpRoutes
import com.taskyproject.tasky.data.network.constants.X_API_KEY
import com.taskyproject.tasky.data.network.dto.AgendaDto
import com.taskyproject.tasky.data.network.dto.AttendeeDto
import com.taskyproject.tasky.data.network.dto.SyncAgendaRequest
import com.taskyproject.tasky.domain.preferences.Preferences
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

class AgendaApiImpl @Inject constructor(
    private val client: HttpClient,
    private val preferences: Preferences
): AgendaApi {
    override suspend fun deleteAgendaItems(
        eventsIds: List<String>,
        tasksIds: List<String>,
        remindersIds: List<String>
    ) {
        client.post {
            url(HttpRoutes.SYNC_AGENDA)
            contentType(ContentType.Application.Json)
            headers {
                append(X_API_KEY, "619417673")
                append(AUTHORIZATION, preferences.readToken()!!)
            }
            setBody(SyncAgendaRequest(eventsIds, tasksIds, remindersIds))
        }
    }

    override suspend fun getFullAgenda(): AgendaDto {
        val response = client.get {
            url(HttpRoutes.FULL_AGENDA)
            headers {
                append(X_API_KEY, "619417673")
                append(AUTHORIZATION, preferences.readToken()!!)
            }
        }
        return response.body<AgendaDto>()
    }
}