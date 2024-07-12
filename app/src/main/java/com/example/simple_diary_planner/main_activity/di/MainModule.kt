package com.example.simple_diary_planner.main_activity.di

import com.example.simple_diary_planner.core_data.json.JsonDataSource
import com.example.simple_diary_planner.core_data.room.TaskDao
import com.example.simple_diary_planner.main_activity.domain.MainUseCase
import com.example.simple_diary_planner.main_activity.domain.MainUseCaseImpl
import com.example.simple_diary_planner.main_activity.repository.MainRepository
import com.example.simple_diary_planner.main_activity.repository.MainRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Provides
    @Singleton
    fun provideMainRepository(
        jsonDataSource: JsonDataSource,
        taskDao: TaskDao
    ): MainRepository = MainRepositoryImpl(jsonDataSource, taskDao)

    @Provides
    @Singleton
    fun provideMainUseCase(
        mainRepository: MainRepository
    ): MainUseCase = MainUseCaseImpl(mainRepository)
}
