package com.taskyproject.tasky.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class Credentials(val email: String, val password: String)
