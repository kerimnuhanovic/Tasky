package com.taskyproject.tasky.data.mapper

import com.taskyproject.tasky.data.network.dto.LoginResponseDto
import com.taskyproject.tasky.domain.model.LoginResponse

fun LoginResponseDto.toLoginResponse(): LoginResponse = LoginResponse(
    accessToken = accessToken,
    refreshToken = refreshToken,
    userId = userId,
    fullName = fullName,
    accessTokenExpiration = accessTokenExpirationTimestamp
)