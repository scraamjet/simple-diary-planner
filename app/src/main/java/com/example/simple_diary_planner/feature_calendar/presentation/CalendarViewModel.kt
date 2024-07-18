package com.example.simple_diary_planner.feature_calendar.presentation

import androidx.lifecycle.viewModelScope
import com.example.simple_diary_planner.core_base.BaseViewModel
import com.example.simple_diary_planner.feature_calendar.domain.CalendarUseCase
import com.example.simple_diary_planner.utils.TaskResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(private val calendarUseCase: CalendarUseCase) : BaseViewModel() {
    private val _state = MutableStateFlow(CalendarState())
    val state: StateFlow<CalendarState> get() = _state.asStateFlow()

    init {
        loadTasksForSelectedDate()
    }

    fun onSelectDate(event: CalendarEvent) {
        when (event) {
            is CalendarEvent.SelectDate -> {
                _state.value = _state.value.copy(selectedDate = event.date)
                loadTasksForSelectedDate()
            }
        }
    }

    private fun loadTasksForSelectedDate() {
        viewModelScope.launch {
            calendarUseCase.getTasksForDate(_state.value.selectedDate).collect { result ->
                when (result) {
                    is TaskResult.Success -> _state.value = _state.value.copy(tasks = result.data ?: emptyList())
                    is TaskResult.Error -> _error.value = result.message
                }
            }
        }
    }
}
