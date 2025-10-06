package com.example.todo.Model.DataClasses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "taskId")
    var id: Int? = null,
    val title: String,
    val description: String,
    val dateOfAnnouncement: String,
    val importance: Int,
    val dateOfComplete: String,
    val restOfDays: Int
)