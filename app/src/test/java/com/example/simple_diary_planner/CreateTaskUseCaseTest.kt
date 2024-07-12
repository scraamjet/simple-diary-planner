package com.example.simple_diary_planner

import com.example.simple_diary_planner.feature_create_task.repository.CreateTaskRepository
import com.example.simple_diary_planner.feature_create_task.domain.CreateTaskUseCase
import com.example.simple_diary_planner.feature_create_task.domain.CreateTaskUseCaseImpl
import com.example.simple_diary_planner.core_model.TaskEntity
import com.example.simple_diary_planner.utils.TaskResult
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CreateTaskUseCaseTest {

    @Mock
    private lateinit var createTaskRepository: CreateTaskRepository

    private lateinit var createTaskUseCase: CreateTaskUseCase

    @Before
    fun setUp() {
        createTaskUseCase = CreateTaskUseCaseImpl(createTaskRepository)
    }

    @Test
    fun `createTask should emit Success when repository succeeds`() = runBlocking {
        val task = TaskEntity(100, 1721149200000, 1721152800000, "Task 1", "Description 1")

        Mockito.doReturn(Unit).`when`(createTaskRepository).saveTask(task)

        val result = createTaskUseCase.createTask(task).first()

        assertTrue(result is TaskResult.Success)
    }

    @Test
    fun `isTaskTimeAvailable should return true when time is available`() = runBlocking {
        val task = TaskEntity(101, 1721127600000, 1721131200000,"Task 2", "Description 2")

        Mockito.`when`(createTaskRepository.isTimeAvailable(task)).thenReturn(true)

        val result = createTaskUseCase.isTaskTimeAvailable(task)

        assertTrue(result)
    }
}
