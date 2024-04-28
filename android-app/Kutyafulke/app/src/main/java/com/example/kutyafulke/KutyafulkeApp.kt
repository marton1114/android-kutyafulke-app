package com.example.kutyafulke

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class KutyafulkeApp: Application() {
    override fun onCreate() {
        super.onCreate()

        val locationChannel = NotificationChannel(
            "location",
            "Location",
            NotificationManager.IMPORTANCE_LOW
        )
        val pillChannel = NotificationChannel(
            "pill",
            "pill",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(locationChannel)
        notificationManager.createNotificationChannel(pillChannel)
    }
}