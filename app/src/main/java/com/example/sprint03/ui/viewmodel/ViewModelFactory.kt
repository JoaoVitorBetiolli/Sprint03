package com.example.sprint03.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sprint03.AppContainer

class ViewModelFactory(
    private val container: AppContainer,
    private val taskId: Int = -1
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when {
        modelClass.isAssignableFrom(HomeViewModel::class.java) ->
            HomeViewModel(container.observeTasks)

        modelClass.isAssignableFrom(AddTaskViewModel::class.java) ->
            AddTaskViewModel(container.addTask)

        modelClass.isAssignableFrom(TaskDetailsViewModel::class.java) ->
            TaskDetailsViewModel(
                taskId,
                container.getTask,
                container.toggleTaskCompleted,
                container.deleteTask
            )

        else -> throw IllegalArgumentException("ViewModel desconhecido: ${modelClass.name}")
    } as T
}