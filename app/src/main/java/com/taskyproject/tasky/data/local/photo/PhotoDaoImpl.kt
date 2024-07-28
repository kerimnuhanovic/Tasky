package com.taskyproject.tasky.data.local.photo

import com.taskyproject.tasky.domain.model.Photo
import taskydatabase.PhotoEntityQueries
import javax.inject.Inject

class PhotoDaoImpl @Inject constructor(
    private val photoEntityQueries: PhotoEntityQueries
) : PhotoDao {
    override suspend fun insetPhoto(photo: Photo) {
        photoEntityQueries.insertPhoto(key = photo.key, url = photo.url, eventId = photo.eventId, shouldBeDeleted = 0)
    }
}