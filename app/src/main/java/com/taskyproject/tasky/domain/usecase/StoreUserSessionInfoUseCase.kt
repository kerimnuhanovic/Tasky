package com.taskyproject.tasky.domain.usecase

import com.taskyproject.tasky.domain.preferences.Preferences
import javax.inject.Inject

class StoreUserSessionInfoUseCase @Inject constructor(
    private val preferences: Preferences
) {
    operator fun invoke(
        accessToken: String,
        userId: String,
        fullName: String,
        accessTokenExpiration: Long,
        email: String,
        password: String
    ) {
        preferences.saveUserSessionInfo(accessToken, userId, fullName, accessTokenExpiration, email, password)
    }
}