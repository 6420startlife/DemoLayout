package com.ptithcm.thuan6420.basecleanarchitecture

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.ptithcm.thuan6420.basecleanarchitecture.Constants.CHANNEL_ID
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application(), Configuration.Provider {
    @Inject
    lateinit var workerFactory: HiltWorkerFactory
    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder().setWorkerFactory(workerFactory).build()
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, "Push notification", NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }
}