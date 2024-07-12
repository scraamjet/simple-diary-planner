package com.example.simple_diary_planner.feature_create_task.presentation

import androidx.lifecycle.viewModelScope
import com.example.simple_diary_planner.core_base.BaseViewModel
import com.example.simple_diary_planner.core_model.TaskEntity
import com.example.simple_diary_planner.feature_create_task.domain.CreateTaskUseCase
import com.example.simple_diary_planner.utils.TaskResult
import com.example.simple_diary_planner.utils.Utils.withTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateTaskViewModel @Inject constructor(
    private val createTaskUseCase: CreateTaskUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow(CreateTaskState())
    val state: StateFlow<CreateTaskState> get() = _state

    init {
        viewModelScope.launch {
            combine(
                _state.map { it.name },
                _state.map { it.description },
                _state.map { it.selectedDate },
                _state.map { it.startTime },
                _state.map { it.endTime }
            ) { name, description, selectedDate, startTime, endTime ->
                name.length in 3..22 && description.length in 5..40
                        && selectedDate != 0L && startTime.isNotEmpty() && endTime.isNotEmpty()
            }.collect { isValid ->
                _state.value = _state.value.copy(isFormValid = isValid)
            }
        }
    }

    fun onNameChange(name: String) {
        _state.value = _state.value.copy(name = name)
    }

    fun onDescriptionChange(description: String) {
        _state.value = _state.value.copy(description = description)
    }

    fun onDateChange(date: Long) {
        _state.value = _state.value.copy(selectedDate = date)
    }

    fun onStartTimeChange(time: String) {
        _state.value = _state.value.copy(startTime = time)
    }

    fun onEndTimeChange(time: String) {
        _state.value = _state.value.copy(endTime = time)
    }

    fun createTask() {
        viewModelScope.launch {
            if (_state.value.isFormValid) {
                val startDateTime = _state.value.selectedDate.withTime(_state.value.startTime)
                val endDateTime = _state.value.selectedDate.withTime(_state.value.endTime)

                val newTask = TaskEntity(
                    name = _state.value.name,
                    description = _state.value.description,
                    dateStart = startDateTime,
                    dateFinish = endDateTime
                )

                val isTimeAvailable = createTaskUseCase.isTaskTimeAvailable(newTask)
                if (isTimeAvailable) {
                    createTaskUseCase.createTask(newTask).collect { result ->
                        when (result) {
                            is TaskResult.Success -> {
                                _state.value = _state.value.copy(event = CreateTaskEvent.TaskCreated)
                            }
                            is TaskResult.Error -> {
                                _error.value = result.message
                            }
                        }
                    }
                } else {
                    _state.value = _state.value.copy(event = CreateTaskEvent.ShowError("Task already exists at this time"))
                }
            } else {
                _state.value = _state.value.copy(event = CreateTaskEvent.ShowError("Fill all fields correctly"))
            }
        }
    }

    fun clearEvent() {
        _state.value = _state.value.copy(event = null)
    }
}