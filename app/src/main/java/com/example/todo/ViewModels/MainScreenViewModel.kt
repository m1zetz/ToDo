package com.example.todo.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.Model.DataClasses.TaskData
import com.example.todo.Model.Repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainScreenViewModel(
    private val repo: TaskRepository
) : ViewModel() {

    val tasks = repo.listOfTasks
    private fun LoadTasks(){
        viewModelScope.launch {
            repo.getTasks()
        }
    }

    fun addTask(){
        viewModelScope.launch {
            repo.addTask()
        }
    }

    fun deleteTask(){
        viewModelScope.launch {
            repo.deleteTask()
        }
    }

    fun refresh(){
        LoadTasks()
    }

    init{
        refresh()
    }

}