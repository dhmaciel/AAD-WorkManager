package com.example.aad_workmanager.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.aad_workmanager.R

class NotificationCenter {

    fun showSimpleNotification(context: Context, title: String, content: String) {

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_stat_image_worker)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        getNotificationManager(context).notify(NOTIFICATION_ID, builder.build())
    }

    fun createNotificationChanel(context: Context) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME_IMAGE,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.apply {
                description = "Notification from image"
                enableVibration(true)
            }
            getNotificationManager(context).createNotificationChannel(notificationChannel)
        }
    }

    private fun getNotificationManager(context: Context) =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    companion object {
        const val CHANNEL_ID = "work_manager_01"
        const val NOTIFICATION_ID = 101
        const val CHANNEL_NAME_IMAGE = "Download image notification"
    }
}