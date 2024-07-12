package com.example.simple_diary_planner.core_data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.simple_diary_planner.core_model.TaskEntity

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTasks(tasks: List<TaskEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity)

    @Query("SELECT * FROM tasks WHERE dateStart BETWEEN :startDate AND :endDate")
    suspend fun getTasksForDate(startDate: Long, endDate: Long): List<TaskEntity>

    @Query("SELECT * FROM tasks WHERE id = :taskId")
    suspend fun getTaskById(taskId: Int): TaskEntity

    @Query("SELECT * FROM tasks WHERE dateStart = :startTime AND dateFinish = :endTime")
    suspend fun getTasksInTimeRange(startTime: Long, endTime: Long): List<TaskEntity>

}
