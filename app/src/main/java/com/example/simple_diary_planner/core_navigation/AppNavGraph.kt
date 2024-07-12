package com.example.simple_diary_planner.core_navigation

import com.example.simple_diary_planner.feature_calendar.presentation.CalendarScreen
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.simple_diary_planner.feature_calendar.presentation.CalendarViewModel
import com.example.simple_diary_planner.feature_create_task.presentation.CreateTaskScreen
import com.example.simple_diary_planner.feature_create_task.presentation.CreateTaskViewModel
import com.example.simple_diary_planner.feature_task_detail.presentation.TaskDetailScreen
import com.example.simple_diary_planner.feature_task_detail.presentation.TaskDetailViewModel

sealed class Screen(val route: String) {
    data object Calendar : Screen("calendar")
    data object TaskDetail : Screen("task_detail/{taskId}") {
        fun createRoute(taskId: Int) = "task_detail/$taskId"
    }
    data object CreateTask: Screen("create_task")
}

@Composable
fun DiaryNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Calendar.route) {
        composable(Screen.Calendar.route) {
            val viewModel: CalendarViewModel = hiltViewModel()
            CalendarScreen().Create(viewModel, navController)
        }
        composable(Screen.TaskDetail.route) {
            val viewModel: TaskDetailViewModel = hiltViewModel()
            TaskDetailScreen().Create(viewModel, navController)
        }
        composable(Screen.CreateTask.route) {
            val viewModel: CreateTaskViewModel = hiltViewModel()
            CreateTaskScreen().Create(viewModel, navController)
        }
    }
}

