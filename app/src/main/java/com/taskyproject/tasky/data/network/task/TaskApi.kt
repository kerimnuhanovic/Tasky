package com.taskyproject.tasky.data.network.task

import com.taskyproject.tasky.domain.model.Task

interface TaskApi {
    suspend fun createTask(task: Task)
}