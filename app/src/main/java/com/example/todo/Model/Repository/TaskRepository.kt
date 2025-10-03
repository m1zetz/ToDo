package com.example.todo.Model.Repository


import com.example.todo.Model.DataClasses.TaskEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface TaskRepository {
    val listOfTasks: Flow<List<TaskEntity>>
    suspend fun addTask(task: TaskEntity)
    suspend fun deleteTask(task: TaskEntity)
}