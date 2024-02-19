package com.example.zayatspexelsapp.data.responses

import com.example.zayatspexelsapp.data.models.FeaturedCollection

data class FeaturedCollectionsResponse(
    val collections: List<FeaturedCollection>,
    val page: Int,
    val per_page: Int,
    val total_results: Int,
    val next_page: String,
    val prev_page: String
)