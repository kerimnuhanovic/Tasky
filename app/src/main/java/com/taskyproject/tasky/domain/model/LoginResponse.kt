package com.taskyproject.tasky.domain.model

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val userId: String,
    val fullName: String,
    val accessTokenExpiration: Long
)
