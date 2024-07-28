package com.taskyproject.tasky.data.mapper

import com.taskyproject.tasky.data.network.dto.PhotoDto
import com.taskyproject.tasky.domain.model.Photo

fun PhotoDto.toPhoto(eventId: String): Photo {
    return Photo(
        key = key,
        url = url,
        eventId = eventId
    )
}