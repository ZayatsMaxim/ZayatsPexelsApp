package com.example.zayatspexelsapp.data.repositories.implementations

import android.util.Log
import com.example.zayatspexelsapp.common.Constants.APP_LOG_TAG
import com.example.zayatspexelsapp.common.Constants.PEXELS_API_KEY
import com.example.zayatspexelsapp.data.models.FeaturedCollection
import com.example.zayatspexelsapp.data.models.Photo
import com.example.zayatspexelsapp.data.network.ApiService
import com.example.zayatspexelsapp.data.repositories.DataRepository

class PexelsDataRepository(
    private val apiService: ApiService
) : DataRepository {

    override suspend fun getPhotoById(id: Int): Photo? {
        return try {
            apiService.getPhotoById(id = id, apiKey = PEXELS_API_KEY)
        } catch (e: Exception) {
            Log.e(APP_LOG_TAG,"$this: $e")
            null
        }
//        return apiService.getPhotoById(id = id, apiKey = PEXELS_API_KEY)
    }

    override suspend fun getCuratedPhotos(per_page: Int, page: Int): List<Photo> {
        return try {
            apiService
                .getCuratedPhotos(perPage = per_page, page = page, apiKey = PEXELS_API_KEY)
                .photos
        } catch (e: Exception) {
            Log.e(APP_LOG_TAG,"$this: $e")
            listOf()
        }
    }

    override suspend fun getFeaturedCollectionsList(
        page: Int,
        per_page: Int
    ): List<FeaturedCollection> {
        return try {
            apiService
                .getFeaturedCollections(page = page, perPage = per_page, apiKey = PEXELS_API_KEY)
                .collections
        } catch (e: Exception) {
            Log.e(APP_LOG_TAG,"$this: $e")
            listOf()
        }
    }

    override suspend fun getPhotosListBySearch(
        query: String,
        per_page: Int,
        page: Int
    ): List<Photo> {
        return try {
            apiService
                .getPhotosBySearch(query = query, perPage = per_page, page = page, apiKey = PEXELS_API_KEY)
                .photos
        } catch (e: Exception) {
            Log.e(APP_LOG_TAG,"$this: $e")
            listOf()
        }
    }
}