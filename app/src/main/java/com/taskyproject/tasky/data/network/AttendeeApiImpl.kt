package com.taskyproject.tasky.data.network

import com.taskyproject.tasky.data.network.constants.AUTHORIZATION
import com.taskyproject.tasky.data.network.constants.HttpRoutes
import com.taskyproject.tasky.data.network.constants.X_API_KEY
import com.taskyproject.tasky.data.network.dto.AttendeeDto
import com.taskyproject.tasky.domain.preferences.Preferences
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import javax.inject.Inject

class AttendeeApiImpl @Inject constructor(
    private val client: HttpClient,
    private val preferences: Preferences
) : AttendeeApi {
    override suspend fun fetchAttendee(email: String): AttendeeDto {
        val response = client.get {
            url(HttpRoutes.ATTENDEE)
            headers {
                append(X_API_KEY, "619417673")
                append(AUTHORIZATION, preferences.readToken()!!)
            }
            parameter("email", email)
        }
        return response.body<AttendeeDto>()
    }
}