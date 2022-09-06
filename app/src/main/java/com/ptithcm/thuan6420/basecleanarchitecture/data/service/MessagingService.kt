package com.ptithcm.thuan6420.basecleanarchitecture.data.service

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.ptithcm.thuan6420.basecleanarchitecture.Constants.CHANNEL_ID
import com.ptithcm.thuan6420.basecleanarchitecture.R


class MessagingService: FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val notifications = message.notification ?: return
        sendNotifications(notifications.title, notifications.body)

    }

    private fun sendNotifications(title: String?, body: String?) {
        val notificationBuilder = NotificationCompat
            .Builder(this, CHANNEL_ID)
            .setContentTitle(title).setContentText(body).setSmallIcon(R.mipmap.ic_batman)

        val notification = notificationBuilder.build()
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(1,notification)
    }

    companion object{
        const val TAG = "Messaging Service"
    }
}