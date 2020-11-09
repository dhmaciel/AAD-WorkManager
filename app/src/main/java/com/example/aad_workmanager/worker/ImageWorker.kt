package com.example.aad_workmanager.worker

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.view.View
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.aad_workmanager.notification.NotificationCenter
import java.lang.Exception
import java.util.*

class ImageWorker(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    override fun doWork(): Result {
        val url = "https://picsum.photos/200"
        return try {
            val downloadName = downloadImage(url)
            Result.success(workDataOf("key_download_Image" to downloadName))
        } catch (ex: Exception) {
            Result.failure()
        }
    }

    private fun downloadImage(url: String): String {
        val title = getRandomImageName()
        //showDownloadNotification(title)

        val downloadManager: DownloadManager =
            applicationContext.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        val request: DownloadManager.Request = DownloadManager.Request(Uri.parse(url))
        val id = downloadManager.enqueue(request)
        return title
    }

    //TODO: Moves to extension
    private fun getRandomImageName() = "${Date().time}.jpg"

    private fun showDownloadNotification(imageTitle: String) =
        NotificationCenter().showSimpleNotification(
            applicationContext,
            "Download image...",
            imageTitle
        )
}