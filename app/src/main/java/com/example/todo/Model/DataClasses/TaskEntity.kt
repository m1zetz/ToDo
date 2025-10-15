package com.example.todo.Model.DataClasses

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todo.utils.calculateDays

@Entity(tableName = "tasks")
data class TaskEntity @RequiresApi(Build.VERSION_CODES.O) constructor(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "taskId")
    var id: Int? = null,
    val title: String,
    val description: String,
    val dateOfAnnouncement: String,
    val importance: String,
    val dateOfComplete: String,
    val restOfDays: Long
)