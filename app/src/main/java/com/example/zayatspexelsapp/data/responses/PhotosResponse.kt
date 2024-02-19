package com.example.zayatspexelsapp.data.responses

import com.example.zayatspexelsapp.data.models.Photo

data class PhotosResponse(
    val page: Int,
    val perPage: Int,
    val photos: List<Photo>,
    val totalResults: Int,
    val nextPage: String?
)