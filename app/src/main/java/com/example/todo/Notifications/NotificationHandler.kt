package com.example.todo.Notifications

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.todo.R
import kotlin.random.Random

class NotificationHandler(private val context: Context?) {
    private val notificationManager = context?.getSystemService(NotificationManager::class.java)
    private val notificationChannelID = "notification_channel_id"

    fun showTaskNotification(title: String, description: String ) {
        val notification = NotificationCompat.Builder(context, notificationChannelID)
            .setContentTitle(title)
            .setContentText(description)
            .setSmallIcon(R.drawable.time_icon)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        notificationManager?.notify(Random.nextInt(), notification)
    }
}

class NotificationReciever : BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {
        context ?: return
        val title = intent?.getStringExtra("title") ?: "Напоминание"
        val desc = intent?.getStringExtra("description") ?: "Пора выполнить задачу!"
        val handler = NotificationHandler(context)
        handler.showTaskNotification(title,desc)
    }

}