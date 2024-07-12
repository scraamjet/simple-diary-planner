package com.example.simple_diary_planner.main_activity.domain

import com.example.simple_diary_planner.main_activity.repository.MainRepository
import javax.inject.Inject


class MainUseCaseImpl @Inject constructor(
    private val repository: MainRepository
) : MainUseCase {
    override suspend fun initializeTasks() {
        val tasks = repository.loadTasks()
        repository.insertTasks(tasks)
    }
}
