 package com.example.rent_a_boatnavigationcomponents

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder

 class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sendFakeNotification()
    }

    private fun sendFakeNotification() {
        val notificationManger = getSystemService(NOTIFICATION_SERVICE) as  NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val notificationChannel = NotificationChannel("channelO", "notification channel",
            NotificationManager.IMPORTANCE_HIGH)
                notificationManger.createNotificationChannel(notificationChannel)
        }
        var args = BoatFragmentArgs.Builder(3).build().toBundle()

        val pendingIntent = NavDeepLinkBuilder(this)
            .setGraph(R.navigation.nav_graph)
            .setDestination(R.id.boatFragment)
            .setArguments(args)
            .createPendingIntent()

        val notification = Notification.Builder(this, "channelO")
            .setContentTitle("Now on sale!")
            .setSmallIcon(R.drawable.ic_boat_24)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        notificationManger.notify(0, notification.build())

    }
}