package com.example.simple_diary_planner.core_data.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.simple_diary_planner.core_data.json.JsonDataSource
import com.example.simple_diary_planner.core_data.room.TaskDao
import com.example.simple_diary_planner.core_data.room.TaskDatabase
import com.example.simple_diary_planner.core_data.mapper.TaskEntityToTaskMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(appContext: Context): TaskDatabase {
        return Room.databaseBuilder(
            appContext,
            TaskDatabase::class.java,
            "task_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideTaskDao(db: TaskDatabase): TaskDao = db.taskDao()

    @Provides
    @Singleton
    fun provideJsonDataSource(context: Context): JsonDataSource = JsonDataSource(context)

    @Provides
    @Singleton
    fun provideCoroutineScope() = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    @Provides
    @Singleton
    fun provideContext(application: Application): Context = application.applicationContext

    @Provides
    fun provideTaskEntityToTaskMapper(): TaskEntityToTaskMapper {
        return TaskEntityToTaskMapper()
    }
}
