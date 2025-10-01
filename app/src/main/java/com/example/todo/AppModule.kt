package com.example.todo

import com.example.todo.Model.Repository.TaskRepository
import com.example.todo.Model.Repository.TaskRepositoryImpl
import com.example.todo.ViewModels.MainScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<TaskRepository> { TaskRepositoryImpl() }
    viewModel { MainScreenViewModel(get()) }
}