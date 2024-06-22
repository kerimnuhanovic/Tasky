package com.taskyproject.tasky.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class RegisterData(val fullName: String, val email: String, val password: String)
