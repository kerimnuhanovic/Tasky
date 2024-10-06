package com.taskyproject.tasky.data.preferences

import android.content.SharedPreferences
import com.taskyproject.tasky.domain.preferences.Preferences
import javax.inject.Inject

class PreferencesImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
): Preferences {
    override fun saveToken(token: String) {
        sharedPreferences.edit().putString(Preferences.KEY_TOKEN, "Bearer $token").apply()
    }

    override fun readToken(): String? {
        return sharedPreferences.getString(Preferences.KEY_TOKEN, null)
    }

    override fun deleteToken() {
        sharedPreferences.edit().putString(Preferences.KEY_TOKEN, null).apply()
    }

    override fun saveUserSessionInfo(
        accessToken: String,
        userId: String,
        fullName: String,
        accessTokenExpiration: Long,
        email: String,
        password: String
    ) {
        saveToken(accessToken)
        sharedPreferences.edit().putString(Preferences.USER_ID, userId).apply()
        sharedPreferences.edit().putString(Preferences.FULL_NAME, fullName).apply()
        sharedPreferences.edit().putLong(Preferences.ACCESS_TOKEN_EXPIRATION, accessTokenExpiration).apply()
        sharedPreferences.edit().putString(Preferences.EMAIL, email).apply()
        sharedPreferences.edit().putString(Preferences.PASSWORD, password).apply()
    }

    override fun deleteSessionInfo() {
        sharedPreferences.edit().putString(Preferences.KEY_TOKEN, null).apply()
        sharedPreferences.edit().putString(Preferences.USER_ID, null).apply()
        sharedPreferences.edit().putString(Preferences.FULL_NAME, null).apply()
        sharedPreferences.edit().putLong(Preferences.ACCESS_TOKEN_EXPIRATION, 0L).apply()
        sharedPreferences.edit().putString(Preferences.EMAIL, null).apply()
        sharedPreferences.edit().putString(Preferences.PASSWORD, null).apply()
    }

    override fun readUserId(): String? {
        return sharedPreferences.getString(Preferences.USER_ID, null)
    }

    override fun readUserEmail(): String? {
        return sharedPreferences.getString(Preferences.EMAIL, null)
    }

    override fun readUserPassword(): String? {
        return sharedPreferences.getString(Preferences.PASSWORD, null)
    }

    override fun readUserFullName(): String? {
        return sharedPreferences.getString(Preferences.FULL_NAME, null)
    }
}