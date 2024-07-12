package com.example.simple_diary_planner.main_activity.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.example.simple_diary_planner.core_navigation.DiaryNavGraph
import com.example.simple_diary_planner.ui.theme.CalendarAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalendarAppTheme{
                val navController = rememberNavController()
                DiaryNavGraph(navController = navController)                }
            }
        viewModel.initializeTasks()
    }
}

