package com.example.todo.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.Model.DataClasses.TaskEntity
import com.example.todo.Model.Repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

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

    var _titleEnter = MutableStateFlow("")
    val titleEnter = _titleEnter.asStateFlow()

    var _descriptionEnter = MutableStateFlow("")
    val descriptionEnter = _descriptionEnter.asStateFlow()

    var _dateOfAnnouncement = MutableStateFlow("")
    val dateOfAnnouncement = _dateOfAnnouncement.asStateFlow()

    var _dateOfComplete = MutableStateFlow("")
    val dateOfComplete = _dateOfComplete.asStateFlow()

    var _importance = MutableStateFlow(0)
    val importance = _importance.asStateFlow()

    var _restOfDays = MutableStateFlow(0)
    val restOfDays = _restOfDays.asStateFlow()

    fun setTitle(title: String) {
        _titleEnter.value = title
    }

    fun setDescription(desc: String) {
        _descriptionEnter.value = desc
    }

    fun setDate(date: String) {
        _dateOfAnnouncement.value = date
    }

    fun setImportance(importance: Int) {
        _importance.value = importance
    }

    fun setDateOfComplete(date: String) {
        _dateOfComplete.value = date
    }

    fun calculateRestOfDays(dateOfAnnouncement: String, dateOfComplete: String) : Int{
//        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        return 0
    }
    fun addTask() {
        viewModelScope.launch {
            val testTask = TaskEntity(
                title = _titleEnter.value,
                description = _descriptionEnter.value,
                dateOfAnnouncement = _dateOfAnnouncement.value,
                importance = _importance.value,
                dateOfComplete = _dateOfComplete.value,
                restOfDays = calculateRestOfDays(dateOfAnnouncement.value, _dateOfComplete.value )
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