package com.example.simple_diary_planner.feature_calendar.repository

import com.example.simple_diary_planner.core_model.TaskEntity

interface CalendarRepository {
    suspend fun getTasksForDate(startDate: Long, endDate: Long): List<TaskEntity>
}