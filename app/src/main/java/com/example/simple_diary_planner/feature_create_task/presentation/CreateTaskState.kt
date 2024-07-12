package com.example.simple_diary_planner.feature_create_task.presentation

data class CreateTaskState(
    val name: String = "",
    val description: String = "",
    val selectedDate: Long = System.currentTimeMillis(),
    val startTime: String = "00:00",
    val endTime: String = "01:00",
    val isFormValid: Boolean = false,
    val isExist: Boolean = false,
    val event: CreateTaskEvent? = null
)
