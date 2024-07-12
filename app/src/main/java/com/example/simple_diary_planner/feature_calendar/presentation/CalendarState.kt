package com.example.simple_diary_planner.feature_calendar.presentation

import com.example.simple_diary_planner.core_model.Task

data class CalendarState(
    val selectedDate: Long = System.currentTimeMillis(),
    val tasks: List<Task> = emptyList(),
)