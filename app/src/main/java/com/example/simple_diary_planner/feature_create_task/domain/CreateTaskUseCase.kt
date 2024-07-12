package com.example.simple_diary_planner.feature_create_task.domain

import com.example.simple_diary_planner.core_model.TaskEntity
import com.example.simple_diary_planner.utils.TaskResult
import kotlinx.coroutines.flow.Flow

interface CreateTaskUseCase {
    suspend fun createTask(task: TaskEntity): Flow<TaskResult<Unit?>>
    suspend fun isTaskTimeAvailable(task: TaskEntity): Boolean
}
