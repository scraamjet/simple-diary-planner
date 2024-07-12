package com.example.simple_diary_planner.feature_calendar.repository

import com.example.simple_diary_planner.core_data.room.TaskDao
import com.example.simple_diary_planner.core_model.TaskEntity
import javax.inject.Inject

class CalendarRepositoryImpl @Inject constructor(private val taskDao: TaskDao): CalendarRepository {

    override suspend fun getTasksForDate(startDate: Long, endDate: Long): List<TaskEntity> {
        return taskDao.getTasksForDate(startDate, endDate)
    }
}

