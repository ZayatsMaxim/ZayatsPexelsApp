package com.example.zayatspexelsapp.data.repositories

import com.example.zayatspexelsapp.data.models.Photo

interface DatabaseRepository {
    suspend fun getPhotosList(): List<Photo>

    suspend fun getPhotoById(id: Int): Photo?

    suspend fun countPhotosById(id: Int): Int

    suspend fun addFavouritePhoto(photo: Photo)

    suspend fun removeFavouritePhoto(photo: Photo)
}