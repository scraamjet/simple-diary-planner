package com.example.simple_diary_planner.core_data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.simple_diary_planner.core_model.TaskEntity

@Database(entities = [TaskEntity::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}
