package com.taskyproject.tasky.data.network

import com.taskyproject.tasky.data.network.dto.Credentials
import com.taskyproject.tasky.data.network.dto.LoginResponseDto

interface LoginApi {
    suspend fun login(credentials: Credentials) : LoginResponseDto
}