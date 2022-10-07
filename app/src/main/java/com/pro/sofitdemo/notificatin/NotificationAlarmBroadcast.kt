package com.pro.sofitdemo.notificatin


import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.pro.sofitdemo.MainActivity
import com.pro.sofitdemo.R


class NotificationAlarmBroadcast : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.i("alaram", "onReceive: alaram triggered ")

        context?.let {
            createNotificationChannel(it)
            buildNotification(it)
        }
    }


    fun buildNotification(context: Context) {
        createNotificationChannel(context)
        val icon1 = BitmapFactory.decodeResource(context.resources, R.drawable.home_ic)
        val intent = Intent(context, MainActivity::class.java).putExtra("infromalram", true)

        val pendingIntent = PendingIntent.getActivity(context, 2586, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)


        val notification = NotificationCompat.Builder(context,"mychannal")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setSmallIcon(R.drawable.home_ic)
            .setColor(ContextCompat.getColor(context, R.color.black))
            .setContentTitle("Need some drinks open app now")
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setContentText("")
            .setLargeIcon(icon1)
            .setStyle(
                NotificationCompat.BigPictureStyle().bigPicture(icon1)
                    .bigLargeIcon(null)
            )
            .build()
        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify((1000..2343).random(), notification)
        }
        try {
            icon1.recycle()
        } catch (e: Exception) {
        } catch (e: java.lang.Exception) {
        }
    }

    private fun createNotificationChannel(context: Context) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var notificationChannel =
                notificationManager?.getNotificationChannel("mychannal")
            if (notificationChannel == null) {
                val channelName = "mychannal"
                notificationChannel =
                    NotificationChannel("mychannal", channelName, NotificationManager.IMPORTANCE_LOW)
                notificationChannel.description = channelName
                notificationManager?.createNotificationChannel(notificationChannel)
            }
        }
    }
}