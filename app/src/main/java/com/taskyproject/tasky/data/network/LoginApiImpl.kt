package com.taskyproject.tasky.data.network

import com.taskyproject.tasky.data.network.constants.HttpRoutes
import com.taskyproject.tasky.data.network.constants.X_API_KEY
import com.taskyproject.tasky.data.network.dto.Credentials
import com.taskyproject.tasky.data.network.dto.LoginResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

class LoginApiImpl @Inject constructor(
    private val client: HttpClient
): LoginApi {
    override suspend fun login(credentials: Credentials): LoginResponseDto {
        val response = client.post {
            url(HttpRoutes.LOGIN)
            contentType(ContentType.Application.Json)
            headers {
                append(X_API_KEY, "619417673")
            }
            setBody(credentials)

        }
        return response.body<LoginResponseDto>()
    }
}