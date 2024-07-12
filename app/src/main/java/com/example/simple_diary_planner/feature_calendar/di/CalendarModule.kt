package com.example.simple_diary_planner.feature_calendar.di

import com.example.simple_diary_planner.core_data.room.TaskDao
import com.example.simple_diary_planner.core_data.mapper.TaskEntityToTaskMapper
import com.example.simple_diary_planner.feature_calendar.repository.CalendarRepository
import com.example.simple_diary_planner.feature_calendar.repository.CalendarRepositoryImpl
import com.example.simple_diary_planner.feature_calendar.domain.CalendarUseCase
import com.example.simple_diary_planner.feature_calendar.domain.CalendarUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CalendarModule {

    @Provides
    @Singleton
    fun provideTaskRepository(taskDao: TaskDao): CalendarRepository {
        return CalendarRepositoryImpl(taskDao)
    }

    @Provides
    @Singleton
    fun provideTaskUseCase(
        repository: CalendarRepository,
        mapper: TaskEntityToTaskMapper
    ): CalendarUseCase {
        return CalendarUseCaseImpl(repository, mapper)
    }
}
