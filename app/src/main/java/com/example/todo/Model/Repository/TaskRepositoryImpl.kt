package com.example.todo.Model.Repository

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Database
import com.example.todo.Model.DataClasses.TaskDB
import com.example.todo.Model.DataClasses.TaskEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate

class TaskRepositoryImpl(val database: TaskDB) : TaskRepository {

    override val listOfTasks: Flow<List<TaskEntity>> = database.dao.getAllTasks()


    override suspend fun addTask(task: TaskEntity) {

        database.dao.insertTask(
            task
        )
    }

    override suspend fun deleteTask(task: TaskEntity) {
        database.dao.deleteTask(task)
    }

}



