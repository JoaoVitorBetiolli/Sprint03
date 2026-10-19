package com.example.sprint03.ui.viewmodel

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sprint03.R
import com.example.sprint03.domain.EmptyTitleException
import com.example.sprint03.domain.usecase.AddTaskUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AddTaskUiState(
    val isSaving: Boolean = false,
    val saved: Boolean = false,
    @StringRes val errorRes: Int? = null
)

class AddTaskViewModel(
    private val addTask: AddTaskUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddTaskUiState())
    val uiState: StateFlow<AddTaskUiState> = _uiState.asStateFlow()

    fun save(title: String, description: String) {
        if (_uiState.value.isSaving) return
        _uiState.value = AddTaskUiState(isSaving = true)
        viewModelScope.launch {
            addTask(title, description).fold(
                onSuccess = { _uiState.value = AddTaskUiState(saved = true) },
                onFailure = { error ->
                    val message = if (error is EmptyTitleException) {
                        R.string.error_empty_title
                    } else {
                        R.string.error_save_task
                    }
                    _uiState.value = AddTaskUiState(errorRes = message)
                }
            )
        }
    }
}