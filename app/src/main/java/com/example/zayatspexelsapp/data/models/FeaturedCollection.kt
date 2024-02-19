package com.example.zayatspexelsapp.data.models

data class FeaturedCollection(
    val id: String,
    val title: String,
    val description: String?,
    val private: Boolean,
    val media_count: Int,
    val photos_count: Int,
    val videos_count: Int
)