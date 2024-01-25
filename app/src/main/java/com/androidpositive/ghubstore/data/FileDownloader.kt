package com.androidpositive.ghubstore.data

import android.app.DownloadManager
import android.os.Environment
import androidx.core.net.toUri
import dagger.hilt.android.components.ViewModelComponent
import it.czerwinski.android.hilt.annotations.BoundTo
import javax.inject.Inject

interface FileDownloader {
    fun downloadFile(url: String, fileName: String): Long
}

@BoundTo(supertype = FileDownloader::class, component = ViewModelComponent::class)
class FileDownloaderImpl @Inject constructor(
    private val downloadManager: DownloadManager
) : FileDownloader {

    override fun downloadFile(url: String, fileName: String): Long {
        val downloadRequest = DownloadManager.Request(url.toUri())
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
            .setTitle(fileName)
            .setDestinationInExternalPublicDir(
                Environment.DIRECTORY_DOWNLOADS,
                fileName
            )
        return downloadManager.enqueue(downloadRequest)
    }
}
