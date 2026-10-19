package com.example.sprint03

import android.content.Context
import com.example.sprint03.data.TaskRepositoryImpl
import com.example.sprint03.data.local.AppDatabase
import com.example.sprint03.domain.TaskRepository
import com.example.sprint03.domain.usecase.AddTaskUseCase
import com.example.sprint03.domain.usecase.DeleteTaskUseCase
import com.example.sprint03.domain.usecase.GetTaskUseCase
import com.example.sprint03.domain.usecase.ObserveTasksUseCase
import com.example.sprint03.domain.usecase.ToggleTaskCompletedUseCase

class AppContainer(context: Context) {

    private val database = AppDatabase.getInstance(context)
    private val repository: TaskRepository = TaskRepositoryImpl(database.taskDao())

    val observeTasks = ObserveTasksUseCase(repository)
    val getTask = GetTaskUseCase(repository)
    val addTask = AddTaskUseCase(repository)
    val toggleTaskCompleted = ToggleTaskCompletedUseCase(repository)
    val deleteTask = DeleteTaskUseCase(repository)
}