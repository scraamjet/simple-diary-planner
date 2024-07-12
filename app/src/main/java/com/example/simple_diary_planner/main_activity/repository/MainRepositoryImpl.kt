package com.example.simple_diary_planner.main_activity.repository

import com.example.simple_diary_planner.core_data.json.JsonDataSource
import com.example.simple_diary_planner.core_data.room.TaskDao
import com.example.simple_diary_planner.core_model.TaskEntity
import javax.inject.Inject


class MainRepositoryImpl @Inject constructor(
    private val jsonDataSource: JsonDataSource,
    private val taskDao: TaskDao
) : MainRepository {
    override suspend fun loadTasks(): List<TaskEntity> {
        return jsonDataSource.loadTasks()
    }

    override suspend fun insertTasks(tasks: List<TaskEntity>) {
        taskDao.insertTasks(tasks)
    }
}
