package com.oleg.servicepermisson

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.widget.Toast
import androidx.core.app.NotificationCompat

class CustomService : Service() {
    override fun onBind(p0: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()

        Toast.makeText(this, "Start foreground service", Toast.LENGTH_SHORT).show()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        start()
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        Toast.makeText(this, "Destroy foreground service", Toast.LENGTH_SHORT).show()
    }

    private fun start() {
        val notification = NotificationCompat.Builder(this, RootApp.NOTIFICATION_CHANNEL_ID)
            .setSubText("sub Text")
            .setSmallIcon(R.drawable.ic_access_time_filled_24)
            .setContentTitle("Content Title")
            .setOngoing(true)
            .build()

        startForeground(1, notification)
    }
}