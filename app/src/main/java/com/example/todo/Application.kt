package com.example.todo

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.todo.Model.DataClasses.TaskDB
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class Application : Application() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        val notificationChannel = NotificationChannel(
            "notification_channel_id",
            "Notification name",
            NotificationManager.IMPORTANCE_HIGH
        )
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(notificationChannel)
        startKoin {
            androidContext(this@Application)
            modules(appModule)
        }
    }
    val database by lazy {
        TaskDB.createDB(this)
    }
}