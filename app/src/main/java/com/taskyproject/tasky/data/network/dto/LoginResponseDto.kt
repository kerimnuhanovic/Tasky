package com.taskyproject.tasky.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDto(
    val accessToken: String,
    val refreshToken: String,
    val userId: String,
    val fullName: String,
    val accessTokenExpirationTimestamp: Long
)
