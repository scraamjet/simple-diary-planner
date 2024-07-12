package com.example.simple_diary_planner.feature_task_detail.presentation

import com.example.simple_diary_planner.core_model.Task

data class TaskDetailState(
    val task: Task? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
