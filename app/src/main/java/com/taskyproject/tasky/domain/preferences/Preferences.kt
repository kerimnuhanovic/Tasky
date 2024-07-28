package com.taskyproject.tasky.domain.preferences

interface Preferences {
    fun saveToken(token: String)
    fun readToken(): String?
    fun deleteToken()
    fun saveUserSessionInfo(accessToken: String, userId: String, fullName: String, accessTokenExpiration: Long)
    fun readUserId(): String?

    companion object {
        const val KEY_TOKEN = "access_token"
        const val USER_ID = "user_id"
        const val FULL_NAME = "full_name"
        const val ACCESS_TOKEN_EXPIRATION = "access_token_expiration"
    }

}