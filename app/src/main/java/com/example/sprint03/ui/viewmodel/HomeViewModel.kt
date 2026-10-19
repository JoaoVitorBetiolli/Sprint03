package com.example.sprint03.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sprint03.R
import com.example.sprint03.domain.Task
import com.example.sprint03.domain.usecase.ObserveTasksUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val observeTasks: ObserveTasksUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Task>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Task>>> = _uiState.asStateFlow()

    private var loadJob: Job? = null

    init {
        load()
    }

    fun load() {
        loadJob?.cancel()
        _uiState.value = UiState.Loading
        loadJob = viewModelScope.launch {
            observeTasks()
                .catch { _uiState.value = UiState.Error(R.string.error_load_tasks) }
                .collect { tasks -> _uiState.value = UiState.Success(tasks) }
        }
    }
}