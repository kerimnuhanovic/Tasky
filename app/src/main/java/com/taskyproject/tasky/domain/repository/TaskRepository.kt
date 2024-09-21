package com.taskyproject.tasky.domain.repository

import com.taskyproject.tasky.domain.model.Task
import com.taskyproject.tasky.domain.util.Result

interface TaskRepository {
    suspend fun createTask(task: Task): Result<Task>
    suspend fun getTask(id: String): Task
    suspend fun updateTask(task: Task): Result<Task>
    suspend fun deleteTask(taskId: String): Result<Unit>
}