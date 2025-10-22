package com.example.todo.Notifications

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.annotation.RequiresPermission

object NotificationScheduler {
    @SuppressLint("ScheduleExactAlarm")
    @RequiresPermission(Manifest.permission.SCHEDULE_EXACT_ALARM)
    fun scheduleNotification(context: Context, title: String, description: String, triggerTimeMillis: Long) {
        val intent = Intent(context, NotificationReciever::class.java).apply {
            putExtra("title", title)
            putExtra("description", description)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            title.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            triggerTimeMillis,
            pendingIntent
        )
    }
}