package com.example.todo.Model.Repository

import com.example.todo.Model.DataClasses.TaskData
import kotlinx.coroutines.flow.StateFlow

interface TaskRepository {
    val listOfTasks: StateFlow<List<TaskData>>
    suspend fun getTasks()
    suspend fun addTask()
    suspend fun deleteTask()
}