package com.example.todo.Model.DataClasses

import java.time.LocalDate

data class TaskData(
    val title: String,
    val description: String,
    val dateOfAnnouncement: LocalDate,
    val importance: String,
    val restOfDays: Int
)
