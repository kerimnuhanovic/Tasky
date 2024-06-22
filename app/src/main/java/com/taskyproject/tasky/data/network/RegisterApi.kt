package com.taskyproject.tasky.data.network

import com.taskyproject.tasky.data.network.dto.RegisterData

interface RegisterApi {
    suspend fun register(registerData: RegisterData)
}