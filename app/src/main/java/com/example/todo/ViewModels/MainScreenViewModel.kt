package com.example.todo.ViewModels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.Model.DataClasses.TaskEntity
import com.example.todo.Model.Repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.Locale

class MainScreenViewModel(
    private val repo: TaskRepository
) : ViewModel() {

    val tasks = repo.listOfTasks.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = emptyList()
    )

    private var _openDeleteDialogState = MutableStateFlow(false)
    val openDeleteDialogState = _openDeleteDialogState.asStateFlow()

    private var _openAddDialogState = MutableStateFlow(false)
    val openAddDialogState = _openAddDialogState.asStateFlow()

    private val _currentTask = MutableStateFlow<TaskEntity?>(null)
    val currentTask = _currentTask.asStateFlow()
    fun deleteDialog(state: Boolean) {
        _openDeleteDialogState.value = state
    }


    fun addTaskForm(state: Boolean) {
        _openAddDialogState.value = state

    }

    fun addTask() {
        viewModelScope.launch {
            val testTask = TaskEntity(
                title = "Тестовая задача",
                description = "Это пример",
                dateOfAnnouncement = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()),
                importance = 1,
                restOfDays = 5
            )
            repo.addTask(testTask)

        }
    }

    fun deleteRequestTask(task: TaskEntity) {
        _currentTask.value = task
        _openDeleteDialogState.value = true
    }

    fun deleteConfirmTask() {
        viewModelScope.launch {
            _currentTask.value?.let { task ->
                repo.deleteTask(task)
            }
            _openDeleteDialogState.value = false
        }
    }


}