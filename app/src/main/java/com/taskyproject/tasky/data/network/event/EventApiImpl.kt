package com.taskyproject.tasky.data.network.event

import com.taskyproject.tasky.data.mapper.toCreateEventRequestDto
import com.taskyproject.tasky.data.mapper.toReminderDto
import com.taskyproject.tasky.data.network.constants.AUTHORIZATION
import com.taskyproject.tasky.data.network.constants.EVENT_ID
import com.taskyproject.tasky.data.network.constants.HttpRoutes
import com.taskyproject.tasky.data.network.constants.X_API_KEY
import com.taskyproject.tasky.data.network.dto.EventDto
import com.taskyproject.tasky.data.network.dto.UpdateEventRequestDto
import com.taskyproject.tasky.domain.model.Event
import com.taskyproject.tasky.domain.preferences.Preferences
import com.taskyproject.tasky.domain.util.generateRandomString
import com.taskyproject.tasky.presentation.util.CREATE_EVENT_REQUEST
import com.taskyproject.tasky.presentation.util.UPDATE_EVENT_REQUEST
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import io.ktor.util.InternalAPI
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import javax.inject.Inject

class EventApiImpl @Inject constructor(
    private val client: HttpClient,
    private val preferences: Preferences
) : EventApi {
    @OptIn(InternalAPI::class)
    override suspend fun createEvent(event: Event, eventPhotos: List<File>): EventDto {
        val response = client.submitFormWithBinaryData(
            url = HttpRoutes.EVENT,
            formData = formData {
                append(CREATE_EVENT_REQUEST, Json.encodeToString(event.toCreateEventRequestDto()))
                eventPhotos.forEachIndexed() { index, photo ->
                    append("photo${index}", photo.readBytes(), Headers.build {
                        append(HttpHeaders.ContentType, "image/png")
                        append(HttpHeaders.ContentDisposition, "filename=\"${generateRandomString()}\"")
                    })
                }
            }
        ) {
            headers {
                append(X_API_KEY, "619417673")
                append(AUTHORIZATION, preferences.readToken()!!)
            }
        }
        return response.body<EventDto>()
    }

    override suspend fun updateEvent(eventRequestDto: UpdateEventRequestDto, eventPhotos: List<File>): EventDto {
        val response = client.submitFormWithBinaryData(
            url = HttpRoutes.EVENT,
            formData = formData {
                append(UPDATE_EVENT_REQUEST, Json.encodeToString(eventRequestDto))
                eventPhotos.forEachIndexed() { index, photo ->
                    append("photo${index}", photo.readBytes(), Headers.build {
                        append(HttpHeaders.ContentType, "image/png")
                        append(HttpHeaders.ContentDisposition, "filename=\"${generateRandomString()}\"")
                    })
                }
            }
        ) {
            method = HttpMethod.Put
            headers {
                append(X_API_KEY, "619417673")
                append(AUTHORIZATION, preferences.readToken()!!)
            }
        }
        return response.body<EventDto>()
    }

    override suspend fun deleteEvent(eventId: String) {
        client.delete {
            url(HttpRoutes.EVENT)
            parameter(EVENT_ID, eventId)
            headers {
                append(X_API_KEY, "619417673")
                append(AUTHORIZATION, preferences.readToken()!!)
            }
        }
    }
}