package com.taskyproject.tasky.domain.repository

import com.taskyproject.tasky.domain.model.Task
import com.taskyproject.tasky.domain.util.Result

interface TaskRepository {
    suspend fun createTask(task: Task): Result<Task>
}