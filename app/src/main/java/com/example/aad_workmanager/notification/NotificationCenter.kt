package com.example.aad_workmanager.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import com.example.aad_workmanager.R

object NotificationCenter {

    fun showSimpleNotification(context: Context, title: String, content: String) {
        val builder = getNotificationBuilder(context, title, content)
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

    fun cancelNotification(notificationId: Int, context: Context) {
        getNotificationManager(context).cancel(notificationId)
    }

    fun updateNotification(context: Context) {
        val androidImage =
            BitmapFactory.decodeResource(context.resources, R.drawable.mascot_1)
        val notificationBuilder = getNotificationBuilder(
            context
        ).setStyle(
            NotificationCompat.BigPictureStyle()
                .bigPicture(androidImage)
                .setBigContentTitle("Notification Updated!")
        )
        getNotificationManager(context).notify(NOTIFICATION_ID, notificationBuilder.build())
    }

    private fun getNotificationManager(context: Context) =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    //TODO: Get downloaded image show to the user and remove fixed values.
    private fun getNotificationBuilder(context: Context, title: String = "batatinha", content: String = "potato") =
        NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_stat_image_worker)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    private const val CHANNEL_ID = "work_manager_01"
    const val NOTIFICATION_ID = 101
    private const val CHANNEL_NAME_IMAGE = "Download image notification"

}