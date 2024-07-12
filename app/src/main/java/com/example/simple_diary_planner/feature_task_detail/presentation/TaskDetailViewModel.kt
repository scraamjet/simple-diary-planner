package com.example.simple_diary_planner.feature_task_detail.presentation

import androidx.lifecycle.viewModelScope
import com.example.simple_diary_planner.core_base.BaseViewModel
import com.example.simple_diary_planner.feature_task_detail.domain.TaskDetailUseCase
import com.example.simple_diary_planner.utils.TaskResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TaskDetailViewModel @Inject constructor(
    private val taskDetailUseCase: TaskDetailUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow(TaskDetailState())
    val state: StateFlow<TaskDetailState> get() = _state

    fun loadTaskById(taskId: Int) {
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            taskDetailUseCase.getTaskById(taskId).collect { result ->
                when (result) {
                    is TaskResult.Success -> {
                        _state.value = _state.value.copy(
                            task = result.data,
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                    is TaskResult.Error -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            errorMessage = result.message
                        )
                    }
                }
            }
        }
    }
}

