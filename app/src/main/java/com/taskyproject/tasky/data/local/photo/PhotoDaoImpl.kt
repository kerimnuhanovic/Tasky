package com.taskyproject.tasky.data.local.photo

import com.taskyproject.tasky.domain.model.Photo
import taskydatabase.PhotoEntity
import taskydatabase.PhotoEntityQueries
import javax.inject.Inject

class PhotoDaoImpl @Inject constructor(
    private val photoEntityQueries: PhotoEntityQueries
) : PhotoDao {
    override suspend fun insetPhoto(photo: Photo) {
        photoEntityQueries.insertPhoto(key = photo.key, url = photo.url, eventId = photo.eventId, shouldBeDeleted = 0)
    }

    override suspend fun getEventPhotos(eventId: String): List<PhotoEntity> {
        return photoEntityQueries.getEventPhotos(eventId).executeAsList()
    }

    override suspend fun markPhotoForDelete(photoKey: String, shouldBeDeleted: Boolean) {
        photoEntityQueries.markPhotoForDelete(
            key = photoKey,
            shouldBeDeleted = if (shouldBeDeleted) 1 else 0
        )
    }

    override suspend fun deletePhoto(key: String) {
        photoEntityQueries.deletePhoto(key)
    }

    override suspend fun getEventPhotosForDelete(eventId: String): List<PhotoEntity> {
        return photoEntityQueries.getEventPhotosForDelete(eventId).executeAsList()
    }

    override suspend fun deleteEventPhotos(eventId: String) {
        photoEntityQueries.deleteEventPhotos(eventId)
    }
}