package com.taskyproject.tasky.data.local.photo

import com.taskyproject.tasky.domain.model.Photo
import taskydatabase.PhotoEntity

interface PhotoDao {
    suspend fun insetPhoto(photo: Photo)
    suspend fun getEventPhotos(eventId: String): List<PhotoEntity>
    suspend fun markPhotoForDelete(photoKey: String, shouldBeDeleted: Boolean)
    suspend fun deletePhoto(key: String)
}