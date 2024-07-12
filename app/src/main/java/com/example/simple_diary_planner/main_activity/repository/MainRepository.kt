package com.example.simple_diary_planner.main_activity.repository

import com.example.simple_diary_planner.core_model.TaskEntity

interface MainRepository {
    suspend fun loadTasks(): List<TaskEntity>
    suspend fun insertTasks(tasks: List<TaskEntity>)
}