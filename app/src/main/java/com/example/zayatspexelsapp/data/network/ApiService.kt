package com.example.zayatspexelsapp.data.network

import com.example.zayatspexelsapp.data.responses.FeaturedCollectionsResponse
import com.example.zayatspexelsapp.data.responses.PhotosResponse
import com.example.zayatspexelsapp.data.models.Photo
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("curated")
    suspend fun getCuratedPhotos(
        @Query("per_page") perPage: Int,
        @Query("page") page: Int,
        @Header("Authorization") apiKey: String
    ): PhotosResponse

    @GET("photos/{id}")
    suspend fun getPhotoById(
        @Path("id") id: Int,
        @Header("Authorization") apiKey: String
    ): Photo

    @GET("search")
    suspend fun getPhotosBySearch(
        @Query("query") query: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int,
        @Header("Authorization") apiKey: String,
    ): PhotosResponse

    @GET("collections/featured")
    suspend fun getFeaturedCollections(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Header("Authorization") apiKey: String,
    ): FeaturedCollectionsResponse
}