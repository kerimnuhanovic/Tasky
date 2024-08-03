package com.taskyproject.tasky.data.network.register

import com.taskyproject.tasky.data.network.constants.HttpRoutes
import com.taskyproject.tasky.data.network.constants.X_API_KEY
import com.taskyproject.tasky.data.network.dto.RegisterData
import io.ktor.client.HttpClient
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

class RegisterApiImpl @Inject constructor(
    private val client: HttpClient
) : RegisterApi {
    override suspend fun register(registerData: RegisterData) {
        client.post {
            url(HttpRoutes.REGISTER)
            contentType(ContentType.Application.Json)
            headers {
                append(X_API_KEY, "619417673")
            }
            setBody(registerData)

        }
    }
}