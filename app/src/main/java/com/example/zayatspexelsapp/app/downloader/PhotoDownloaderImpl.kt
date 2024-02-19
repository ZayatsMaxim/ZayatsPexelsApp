package com.example.zayatspexelsapp.app.downloader

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import android.widget.Toast
import androidx.core.net.toUri
import com.example.zayatspexelsapp.R

class PhotoDownloaderImpl(
    private val context: Context
) : PhotoDownloader {
    private val downloadManager = context.getSystemService(DownloadManager::class.java)

    override fun downloadPhoto(url: String, fileName: String): Long {
        val request = DownloadManager.Request(url.toUri())
            .setMimeType("image/jpeg")
            .setTitle("$fileName.jpg")
            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "$fileName.jpg")

        return try {
            Toast.makeText(
                context,
                context.getString(R.string.download_started),
                Toast.LENGTH_SHORT
            ).show()
            downloadManager.enqueue(request)
        } catch (e: Exception) {
            Toast.makeText(
                context,
                context.getString(R.string.download_failed, e),
                Toast.LENGTH_SHORT
            ).show()
            -1
        }
    }


}