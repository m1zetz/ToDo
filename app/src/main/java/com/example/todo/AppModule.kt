package com.example.todo

import androidx.room.Room
import com.example.todo.Model.DataClasses.TaskDB
import com.example.todo.Model.Repository.TaskRepository
import com.example.todo.Model.Repository.TaskRepositoryImpl
import com.example.todo.ViewModels.MainScreenViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    // Database
    single {
        Room.databaseBuilder(
            androidContext(),
            TaskDB::class.java,
            "task.db"
        ).build()
    }

    // DAO
    single { get<TaskDB>().dao }

    // Repository
    single<TaskRepository> { TaskRepositoryImpl(get()) }

    // ViewModel
    viewModel { MainScreenViewModel(get()) }
}