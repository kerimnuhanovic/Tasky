package com.taskyproject.tasky.data.local.task

import com.taskyproject.tasky.domain.model.Task
import kotlinx.coroutines.flow.Flow
import taskydatabase.TaskEntity

interface TaskDao {
    suspend fun insertTask(
        task: Task,
        shouldBeDeleted: Boolean = false,
        shouldBeUpdated: Boolean = false,
        isAddedOnRemote: Boolean = false,
    )
    suspend fun getTask(id: String): TaskEntity
    fun listTasks(): Flow<List<TaskEntity>>
    suspend fun updateTask(
        task: Task,
        shouldBeDeleted: Boolean = false,
        shouldBeUpdated: Boolean = false,
        isAddedOnRemote: Boolean = false,
    )
}