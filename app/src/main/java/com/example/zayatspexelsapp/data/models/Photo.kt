package com.example.zayatspexelsapp.data.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("favourite_photos")
data class Photo(
    @PrimaryKey
    val id: Int,
    val width: Int,
    val height: Int,
    val url: String,
    val photographer: String,
    val photographerUrl: String?,
    val photographerId: Int,
    val avgColor: String?,
    @Embedded(prefix = "src_")
    val src: PhotoSource,
    val liked: Boolean,
    val alt: String
)

data class PhotoSource(
    val original: String,
    val large2x: String,
    val large: String,
    val medium: String,
    val small: String,
    val portrait: String,
    val landscape: String,
    val tiny: String
)