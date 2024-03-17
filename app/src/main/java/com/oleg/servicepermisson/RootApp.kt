package com.oleg.servicepermisson

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import com.markodevcic.peko.PermissionRequester

class RootApp : Application() {
    companion object {
        const val SERVICE_NAME = "com.oleg.service_permission"
        const val NOTIFICATION_CHANNEL_ID = "com.oleg.service_permission.notification_channel_id"

        private var appInstance: RootApp? = null

        fun getInstance(): RootApp = appInstance ?: RootApp().also {
            appInstance = it
        }
    }

    override fun onCreate() {
        super.onCreate()

        // PEKO
        PermissionRequester.initialize(applicationContext)

        // Create and register app notification channel
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return

        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            SERVICE_NAME,
            NotificationManager.IMPORTANCE_HIGH
        )

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(channel)
    }
}