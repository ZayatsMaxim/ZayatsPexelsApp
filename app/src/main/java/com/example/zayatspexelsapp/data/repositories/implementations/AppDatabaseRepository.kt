package com.example.zayatspexelsapp.data.repositories.implementations

import android.util.Log
import com.example.zayatspexelsapp.common.Constants.APP_LOG_TAG
import com.example.zayatspexelsapp.data.database.AppDatabase
import com.example.zayatspexelsapp.data.models.Photo
import com.example.zayatspexelsapp.data.repositories.DatabaseRepository

class AppDatabaseRepository(
    private val db: AppDatabase
) : DatabaseRepository{

    override suspend fun getPhotosList(): List<Photo> {
        return try {
            db.dao.getFavourites()
        } catch (e: Exception) {
            listOf()
        }
    }

    override suspend fun getPhotoById(id: Int): Photo? {
        return try {
            db.dao.getPhotoById(id).first()
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun countPhotosById(id: Int): Int {
        return try {
            db.dao.countById(id)
        } catch (e: Exception) {
            Log.e(APP_LOG_TAG, "countPhotosById: $e")
            0
        }
    }

    override suspend fun addFavouritePhoto(photo: Photo) {
        try {
            db.dao.addFavouritePhoto(photo)
        } catch (e: Exception) {
            Log.e(APP_LOG_TAG, "addFavouritePhoto: $e")
        }
    }

    override suspend fun removeFavouritePhoto(photo: Photo) {
        try {
            db.dao.removeFavouritePhoto(photo)
        } catch (e: Exception) {
            Log.e(APP_LOG_TAG, "removeFavouritePhoto: $e")
        }
    }
}