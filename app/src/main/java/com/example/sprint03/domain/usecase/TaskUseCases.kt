package com.example.sprint03.domain.usecase

import com.example.sprint03.domain.EmptyTitleException
import com.example.sprint03.domain.Task
import com.example.sprint03.domain.TaskNotFoundException
import com.example.sprint03.domain.TaskRepository
import kotlinx.coroutines.flow.Flow

class ObserveTasksUseCase(private val repository: TaskRepository) {
    operator fun invoke(): Flow<List<Task>> = repository.observeTasks()
}

class GetTaskUseCase(private val repository: TaskRepository) {
    suspend operator fun invoke(id: Int): Task? = repository.getTask(id)
}

class AddTaskUseCase(private val repository: TaskRepository) {
    suspend operator fun invoke(title: String, description: String): Result<Unit> {
        if (title.isBlank()) return Result.failure(EmptyTitleException())
        return runCatching { repository.addTask(title.trim(), description.trim()) }
    }
}

class ToggleTaskCompletedUseCase(private val repository: TaskRepository) {
    suspend operator fun invoke(id: Int): Result<Unit> = runCatching {
        val task = repository.getTask(id) ?: throw TaskNotFoundException()
        repository.updateTask(task.copy(isCompleted = !task.isCompleted))
    }
}

class DeleteTaskUseCase(private val repository: TaskRepository) {
    suspend operator fun invoke(id: Int): Result<Unit> = runCatching {
        repository.deleteTask(id)
    }
}