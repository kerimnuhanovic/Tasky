package com.taskyproject.tasky.data.local.photo

import com.taskyproject.tasky.domain.model.Photo

interface PhotoDao {
    suspend fun insetPhoto(photo: Photo)
}