package com.example.todo.ViewModels

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.Model.DataClasses.TaskEntity
import com.example.todo.Model.Repository.TaskRepository
import com.example.todo.Notifications.NotificationScheduler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit

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

    private var _datePickerStateStart = MutableStateFlow(false)
    val datePickerStateStart = _datePickerStateStart.asStateFlow()

    private var _datePickerStateEnd = MutableStateFlow(false)
    val datePickerStateEnd = _datePickerStateEnd.asStateFlow()

    fun changeDatePickerStateStart(state: Boolean){
        _datePickerStateStart.value = state
    }
    fun changeDatePickerStateEnd(state: Boolean){
        _datePickerStateEnd.value = state
    }
    private val _currentTask = MutableStateFlow<TaskEntity?>(null)
    val currentTask = _currentTask.asStateFlow()
    fun deleteDialog(state: Boolean) {
        _openDeleteDialogState.value = state
    }


    fun addTaskForm(state: Boolean) {
        _openAddDialogState.value = state

    }

    private var _titleEnter = MutableStateFlow("")
    val titleEnter = _titleEnter.asStateFlow()

    private var _descriptionEnter = MutableStateFlow("")
    val descriptionEnter = _descriptionEnter.asStateFlow()

    private var _dateOfAnnouncement = MutableStateFlow("?")
    val dateOfAnnouncement = _dateOfAnnouncement.asStateFlow()

    private var _dateOfComplete = MutableStateFlow("?")
    val dateOfComplete = _dateOfComplete.asStateFlow()

    private var _importance = MutableStateFlow("Легкая")
    val importance = _importance.asStateFlow()


    fun setTitle(title: String) {
        _titleEnter.value = title
    }

    fun setDescription(desc: String) {
        _descriptionEnter.value = desc
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setDateOfAnnouncement(date: LocalDate?) {
        _dateOfAnnouncement.value = date.toString()
    }

    fun setImportance(importance: String) {
        _importance.value = importance
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setDateOfComplete(date: LocalDate?) {
        _dateOfComplete.value = date.toString()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun convertMillisToDate(millis: Long?): LocalDate? {
        return millis?.let {
            Instant.ofEpochMilli(it)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
        }
    }


    @SuppressLint("ScheduleExactAlarm")
    @RequiresApi(Build.VERSION_CODES.O)
    fun addTask(context: Context) {
        viewModelScope.launch {
            val testTask = TaskEntity(
                title = _titleEnter.value,
                description = _descriptionEnter.value,
                dateOfAnnouncement = _dateOfAnnouncement.value,
                importance = _importance.value,
                dateOfComplete = _dateOfComplete.value
            )
            repo.addTask(testTask)

        }

        val completeDate = LocalDate.parse(_dateOfComplete.value)
        val triggerDateTime = completeDate.atTime(10, 0)

        val triggerMillis = triggerDateTime
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()

        NotificationScheduler.scheduleNotification(
            context,
            _titleEnter.value,
            _descriptionEnter.value,
            triggerMillis
        )
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
    fun clearFields(){
        _titleEnter.value = ""
        _descriptionEnter.value = ""
        _dateOfAnnouncement.value = "?"
        _dateOfComplete.value = "?"
        _importance.value = "Легкая"
    }



}