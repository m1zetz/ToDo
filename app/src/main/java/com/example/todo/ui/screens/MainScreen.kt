package com.example.todo.ui.screens

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import android.provider.Settings
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import com.example.todo.Notifications.NotificationHandler
import com.example.todo.ViewModels.MainScreenViewModel
import com.example.todo.ui.Composables.MainCard
import org.koin.androidx.compose.koinViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "ServiceCast")
@Composable
fun MainScreen(context: Context) {

    val notificationHandler = NotificationHandler(context)


    val mainViewModel: MainScreenViewModel = koinViewModel()

    val listOfTasks by mainViewModel.tasks.collectAsState()
    val deleteDialogState = mainViewModel.openDeleteDialogState.collectAsState()
    val addDialogState = mainViewModel.openAddDialogState.collectAsState()
    val showDatePickerStart = mainViewModel.datePickerStateStart.collectAsState()
    val showDatePickerEnd = mainViewModel.datePickerStateEnd.collectAsState()

    val title = mainViewModel.titleEnter.collectAsState()
    val description = mainViewModel.descriptionEnter.collectAsState()
    val importance by mainViewModel.importance.collectAsState()
    val startDate by mainViewModel.dateOfAnnouncement.collectAsState()
    val endDate by mainViewModel.dateOfComplete.collectAsState()



    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Напоминания", fontSize = 22.sp, color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.DarkGray,
                    actionIconContentColor = Color.LightGray
                )
            )
        },
        modifier = Modifier,
        bottomBar = {
            BottomAppBar(
                containerColor = Color.DarkGray,
                contentColor = Color.LightGray,
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(onClick = {

                    }
                    ) {
                        Icon(Icons.Filled.Refresh, contentDescription = "Обновить")
                    }
                }

            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    mainViewModel.addTaskForm(true)
                }
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Добавить задачу")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(listOfTasks) { task ->
                MainCard(
                    task.title,
                    task.description,
                    task.importance,
                    task.dateOfAnnouncement,
                    task.dateOfComplete,
                    {
                        mainViewModel.deleteRequestTask(task)
                    }
                )
            }
        }
        if (deleteDialogState.value) {
            AlertDialog(
                onDismissRequest = { mainViewModel.deleteDialog(false) },
                title = { Text(text = "Подтверждение действия") },
                text = { Text("Вы действительно хотите удалить текущую задачу?") },
                confirmButton = {
                    Button({
                        mainViewModel.deleteConfirmTask()
                    }) {
                        Text("Подтвердить", fontSize = 22.sp)
                    }
                }
            )
        }
        if (addDialogState.value) {
            val datePickerStateStart = rememberDatePickerState()
            val datePickerStateEnd = rememberDatePickerState()
            AlertDialog(
                onDismissRequest = { mainViewModel.addTaskForm(false) },
                title = { Text(text = "Создайте напоминание:") },
                text = {
                    if (showDatePickerStart.value) {
                        Popup(
                            onDismissRequest = {
                                mainViewModel.changeDatePickerStateStart(false)
                                val millis = datePickerStateStart.selectedDateMillis
                                mainViewModel.setDateOfAnnouncement(
                                    mainViewModel.convertMillisToDate(
                                        millis
                                    )
                                )
                            },
                        ) {
                            DatePicker(
                                state = datePickerStateStart,
                                showModeToggle = false
                            )
                        }
                    }
                    if (showDatePickerEnd.value) {
                        Popup(
                            onDismissRequest = {
                                mainViewModel.changeDatePickerStateEnd(false)
                                val millis = datePickerStateEnd.selectedDateMillis
                                mainViewModel.setDateOfComplete(
                                    mainViewModel.convertMillisToDate(
                                        millis
                                    )
                                )
                            },
                        ) {
                            DatePicker(
                                state = datePickerStateEnd,
                                showModeToggle = false
                            )
                        }
                    }
                    Column {
                        TextField(
                            value = title.value,
                            onValueChange = { newValue ->
                                mainViewModel.setTitle(newValue)
                            },
                            label = { Text("Название..") }
                        )
                        Spacer(
                            modifier = Modifier.height(10.dp)
                        )
                        TextField(
                            value = description.value,
                            onValueChange = { newValue ->
                                mainViewModel.setDescription(newValue)
                            },
                            label = { Text("Описание..") }
                        )
                        Spacer(
                            modifier = Modifier.height(10.dp)
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Button(
                                onClick = {
                                    mainViewModel.changeDatePickerStateStart(true)
                                },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Выбрать дату начала")
                            }
                            Text("  -> ${startDate}")
                        }


                        Spacer(
                            modifier = Modifier.height(10.dp)
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Button(
                                onClick = { mainViewModel.changeDatePickerStateEnd(true) },
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Выбрать дату конца")
                            }
                            Text("  -> ${endDate}")
                        }

                        Spacer(
                            modifier = Modifier.height(10.dp)
                        )
                        Text(
                            "Сложность:"
                        )
                        Column() {
                            listOf("Легкая", "Нормальная", "Сложная").forEach { option ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    RadioButton(
                                        selected = (option == importance),
                                        onClick = { mainViewModel.setImportance(option) }
                                    )
                                    Text(
                                        option,
                                        fontSize = 18.sp
                                    )
                                }
                            }
                        }
                    }


                },
                confirmButton = {
                    Button({
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                            if (!alarmManager.canScheduleExactAlarms()) {
                                // открываем системное окно разрешения
                                val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                                context.startActivity(intent)
                                Toast.makeText(context, "Разреши использование точных будильников", Toast.LENGTH_LONG).show()
                                return@Button
                            }
                        }

                        mainViewModel.addTask(context)
                        mainViewModel.addTaskForm((false))
                        mainViewModel.clearFields()


                    }) {
                        Text("Создать", fontSize = 16.sp)
                    }
                }
            )
        }
    }
}