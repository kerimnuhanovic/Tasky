package com.taskyproject.tasky.data.network.logout

import com.taskyproject.tasky.data.network.constants.AUTHORIZATION
import com.taskyproject.tasky.data.network.constants.HttpRoutes
import com.taskyproject.tasky.data.network.constants.X_API_KEY
import com.taskyproject.tasky.domain.preferences.Preferences
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.url
import javax.inject.Inject

class LogoutApiImpl @Inject constructor(
    private val client: HttpClient,
    private val preferences: Preferences
): LogoutApi {
    override suspend fun logout() {
        client.get {
            url(HttpRoutes.LOGOUT)
            headers {
                append(X_API_KEY, "619417673")
                append(AUTHORIZATION, preferences.readToken()!!)
            }
        }
    }
}