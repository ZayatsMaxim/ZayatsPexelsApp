package com.example.zayatspexelsapp.app.downloader

interface PhotoDownloader {
    fun downloadPhoto(url: String, fileName: String): Long
}