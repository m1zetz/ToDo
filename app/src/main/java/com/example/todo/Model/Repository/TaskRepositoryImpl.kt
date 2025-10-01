package com.example.todo.Model.Repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.todo.Model.DataClasses.TaskData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate

class TaskRepositoryImpl : TaskRepository {
    private var _listOfTasks = MutableStateFlow(emptyList<TaskData>())
    override val listOfTasks = _listOfTasks.asStateFlow()

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getTasks() {
        val newTask = TaskData(
            title = "Моя задача",
            description = "Описание задачи",
            dateOfAnnouncement = LocalDate.now(),
            importance = "Высокая",
            restOfDays = 5
        )

        _listOfTasks.value = _listOfTasks.value + newTask
    }

    override suspend fun addTask() {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTask() {
        TODO("Not yet implemented")
    }

}