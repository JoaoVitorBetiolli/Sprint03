package com.example.sprint03.domain

import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun observeTasks(): Flow<List<Task>>
    suspend fun getTask(id: Int): Task?
    suspend fun addTask(title: String, description: String)
    suspend fun updateTask(task: Task)
    suspend fun deleteTask(id: Int)
}