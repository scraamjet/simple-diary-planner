package com.example.simple_diary_planner.feature_create_task.domain

import com.example.simple_diary_planner.core_model.TaskEntity
import com.example.simple_diary_planner.feature_create_task.repository.CreateTaskRepository
import com.example.simple_diary_planner.utils.TaskResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CreateTaskUseCaseImpl @Inject constructor(
    private val createTaskRepository: CreateTaskRepository
) : CreateTaskUseCase {
    override suspend fun createTask(task: TaskEntity): Flow<TaskResult<Unit?>> = flow {
        try {
            createTaskRepository.saveTask(task)
            emit(TaskResult.Success(Unit))
        } catch (e: Exception) {
            emit(TaskResult.Error(e.localizedMessage))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun isTaskTimeAvailable(task: TaskEntity): Boolean {
        return createTaskRepository.isTimeAvailable(task)
    }
}
