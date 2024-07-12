package com.example.simple_diary_planner.feature_calendar.domain
import com.example.simple_diary_planner.core_model.Task
import com.example.simple_diary_planner.core_data.mapper.TaskEntityToTaskMapper
import com.example.simple_diary_planner.feature_calendar.repository.CalendarRepository
import com.example.simple_diary_planner.utils.TaskResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.time.Instant
import java.time.ZoneId
import javax.inject.Inject


class CalendarUseCaseImpl @Inject constructor(
    private val taskRepository: CalendarRepository,
    private val mapper: TaskEntityToTaskMapper
) : CalendarUseCase {
    override suspend fun getTasksForDate(date: Long): Flow<TaskResult<List<Task>?>> = flow {
        try {
            val datetime = Instant.ofEpochMilli(date).atZone(ZoneId.systemDefault()).toLocalDateTime().toLocalDate()
            val startDate = datetime.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
            val endDate = datetime.plusDays(1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

            val taskEntities = taskRepository.getTasksForDate(startDate, endDate)
            val tasks = taskEntities.map { mapper.map(it) }.sortedBy { it.dateStart }
            emit(TaskResult.Success(tasks))
        } catch (e: Exception) {
            emit(TaskResult.Error(e.message))
        }
    }.flowOn(Dispatchers.IO)
}
