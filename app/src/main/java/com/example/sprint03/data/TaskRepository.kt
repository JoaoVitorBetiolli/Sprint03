package com.example.sprint03.data

import com.example.sprint03.domain.Task
import androidx.compose.runtime.mutableStateListOf

class TaskRepository {

    private val tasks = mutableStateListOf(
        Task(id = 1, title = "Comprar mantimentos"),
        Task(id = 2, title = "Estudar para a prova"),
        Task(
            id = 3,
            title = "Pagar conta de luz",
            description = "Pagar a conta de luz do mês antes do vencimento, dia 20.",
            isCompleted = true
        ),
        Task(id = 4, title = "Lavar o carro")
    )

    fun getTasks(): List<Task> = tasks

    fun getTaskById(id: Int): Task? = tasks.find { it.id == id }

    fun addTask(title: String, description: String) {
        val newId = (tasks.maxOfOrNull { it.id } ?: 0) + 1
        tasks.add(Task(id = newId, title = title, description = description))
    }

    fun toggleTaskCompleted(id: Int) {
        val index = tasks.indexOfFirst { it.id == id }
        if (index != -1) {
            val task = tasks[index]
            tasks[index] = task.copy(isCompleted = !task.isCompleted)
        }
    }

    fun deleteTask(id: Int) {
        tasks.removeAll { it.id == id }
    }
}