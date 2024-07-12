package com.example.simple_diary_planner.main_activity.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simple_diary_planner.main_activity.domain.MainUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainUseCase: MainUseCase
) : ViewModel() {
    fun initializeTasks() {
        viewModelScope.launch {
            mainUseCase.initializeTasks()
        }
    }
}
