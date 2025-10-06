package com.example.todo.ui.screens

import android.annotation.SuppressLint
import android.widget.DatePicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todo.ViewModels.MainScreenViewModel
import com.example.todo.ui.Composables.MainCard
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen() {

    val mainViewModel: MainScreenViewModel = koinViewModel()
    val listOfTasks by mainViewModel.tasks.collectAsState()
    val deleteDialogState = mainViewModel.openDeleteDialogState.collectAsState()
    val addDialogState = mainViewModel.openAddDialogState.collectAsState()

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
                    task.restOfDays,
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
            val datePickerState = rememberDatePickerState()
            AlertDialog(
                onDismissRequest = { mainViewModel.addTaskForm(false) },
                title = { Text(text = "Создайте напоминание:") },
                text = {
                    Column {
                        TextField(
                            value = "",
                            onValueChange = {},
                            label = {Text("Название..")}
                        )
                        Spacer(
                            modifier = Modifier.height(10.dp)
                        )
                        TextField(
                            value = "",
                            onValueChange = {},
                            label = {Text("Описание..")}
                        )
                        Spacer(
                            modifier = Modifier.height(10.dp)
                        )
                        Button(
                            onClick = {                            }
                        ) {
                            Text("Выбрать дату начала")
                        }
                        Spacer(
                            modifier = Modifier.height(10.dp)
                        )
                        Button(
                            onClick = {}
                        ) {
                            Text("Выбрать дату конца")
                        }
                        Spacer(
                            modifier = Modifier.height(10.dp)
                        )
                        Text(
                            "Сложность:"
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = true,
                                onClick = { }
                            )
                            Text("Легкая",
                                fontSize = 18.sp)
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = true,
                                onClick = { }
                            )
                            Text("Нормальная",
                                fontSize = 18.sp)
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = true,
                                onClick = { }
                            )
                            Text("Сложная",
                                fontSize = 18.sp)
                        }
                    }


                },
                confirmButton = {
                    Button({
                        mainViewModel.addTask()
                    }) {
                        Text("Создать", fontSize = 16.sp)
                    }
                }
            )
        }
    }
//    DatePicker(state = datePickerState)


}