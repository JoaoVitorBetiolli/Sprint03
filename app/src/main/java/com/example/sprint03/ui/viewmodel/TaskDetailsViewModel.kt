package com.example.sprint03.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sprint03.R
import com.example.sprint03.domain.Task
import com.example.sprint03.domain.usecase.DeleteTaskUseCase
import com.example.sprint03.domain.usecase.GetTaskUseCase
import com.example.sprint03.domain.usecase.ToggleTaskCompletedUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TaskDetailsViewModel(
    private val taskId: Int,
    private val getTask: GetTaskUseCase,
    private val toggleCompleted: ToggleTaskCompletedUseCase,
    private val deleteTask: DeleteTaskUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<Task>>(UiState.Loading)
    val uiState: StateFlow<UiState<Task>> = _uiState.asStateFlow()

    private val _deleted = MutableStateFlow(false)
    val deleted: StateFlow<Boolean> = _deleted.asStateFlow()

    init {
        load()
    }

    fun load() {
        _uiState.value = UiState.Loading
        viewModelScope.launch { _uiState.value = fetchTask() }
    }

    fun onToggleCompleted() {
        viewModelScope.launch {
            toggleCompleted(taskId).fold(
                onSuccess = { _uiState.value = fetchTask() },
                onFailure = { _uiState.value = UiState.Error(R.string.error_update_task) }
            )
        }
    }

    fun onDelete() {
        viewModelScope.launch {
            deleteTask(taskId).fold(
                onSuccess = { _deleted.value = true },
                onFailure = { _uiState.value = UiState.Error(R.string.error_delete_task) }
            )
        }
    }

    private suspend fun fetchTask(): UiState<Task> = try {
        val task = getTask(taskId)
        if (task != null) UiState.Success(task) else UiState.Error(R.string.error_task_not_found)
    } catch (e: Exception) {
        UiState.Error(R.string.error_load_task)
    }
}