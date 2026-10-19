package com.example.sprint03.data

import com.example.sprint03.data.local.TaskDao
import com.example.sprint03.data.local.TaskEntity
import com.example.sprint03.domain.Task
import com.example.sprint03.domain.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskRepositoryImpl(private val dao: TaskDao) : TaskRepository {

    override fun observeTasks(): Flow<List<Task>> =
        dao.observeAll().map { entities -> entities.map { it.toDomain() } }

    override suspend fun getTask(id: Int): Task? = dao.getById(id)?.toDomain()

    override suspend fun addTask(title: String, description: String) {
        dao.insert(TaskEntity(title = title, description = description, isCompleted = false))
    }

    override suspend fun updateTask(task: Task) {
        dao.update(task.toEntity())
    }

    override suspend fun deleteTask(id: Int) {
        dao.deleteById(id)
    }
}