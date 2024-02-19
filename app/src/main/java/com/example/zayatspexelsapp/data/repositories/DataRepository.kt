package com.example.zayatspexelsapp.data.repositories

import com.example.zayatspexelsapp.data.models.FeaturedCollection
import com.example.zayatspexelsapp.data.models.Photo

interface DataRepository {
    suspend fun getPhotoById(
        id: Int,
    ): Photo?

    suspend fun getCuratedPhotos(
        per_page: Int,
        page: Int
    ): List<Photo>

    suspend fun getFeaturedCollectionsList(
        page: Int,
        per_page: Int
    ): List<FeaturedCollection>

    suspend fun getPhotosListBySearch(
        query: String,
        per_page: Int,
        page: Int
    ): List<Photo>
}