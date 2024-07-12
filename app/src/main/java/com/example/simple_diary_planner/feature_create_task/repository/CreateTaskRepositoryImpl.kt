package com.example.simple_diary_planner.feature_create_task.repository

import com.example.simple_diary_planner.core_data.room.TaskDao
import com.example.simple_diary_planner.core_model.TaskEntity
import javax.inject.Inject

class CreateTaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao
) : CreateTaskRepository {
    override suspend fun saveTask(taskEntity: TaskEntity) {
        taskDao.insertTask(taskEntity)
    }
    override suspend fun isTimeAvailable(task: TaskEntity): Boolean {
        val tasks = taskDao.getTasksInTimeRange(task.dateStart, task.dateFinish)
        return tasks.isEmpty()
    }
}
