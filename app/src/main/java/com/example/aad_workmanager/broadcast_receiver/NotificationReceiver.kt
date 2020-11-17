package com.example.aad_workmanager.broadcast_receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.aad_workmanager.notification.NotificationCenter

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        NotificationCenter.updateNotification(context)
    }

    companion object {
        const val ACTION_UPDATE_NOTIFICATION =
            "com.example.aad_workmanager.ACTION_UPDATE_NOTIFICATION"
    }
}