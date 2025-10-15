package com.example.todo.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@RequiresApi(Build.VERSION_CODES.O)
fun calculateDays(dateOfComplete: String): Long {
    val today = LocalDate.now()
    val end = LocalDate.parse(dateOfComplete)
    return ChronoUnit.DAYS.between(today, end)
}