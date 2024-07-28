package com.taskyproject.tasky.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class PhotoDto(
    val key: String,
    val url: String
)
