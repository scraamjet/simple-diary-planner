package com.example.simple_diary_planner.feature_task_detail.repository

import com.example.simple_diary_planner.core_data.room.TaskDao
import com.example.simple_diary_planner.core_model.TaskEntity
import javax.inject.Inject


class TaskDetailRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao
) : TaskDetailRepository {
    override suspend fun getTaskById(taskId: Int): TaskEntity {
        return taskDao.getTaskById(taskId)
    }
}
