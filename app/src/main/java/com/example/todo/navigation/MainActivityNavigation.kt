package com.example.todo.navigation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todo.ui.screens.MainScreen
import com.example.todo.ui.theme.ToDoTheme
import com.example.todo.utils.mainScreen

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToDoTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = mainScreen
                ){
                    composable(mainScreen) {
                        val context = LocalContext.current
                        MainScreen(context = context)
                    }
                }


            }
        }
    }
}