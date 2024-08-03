package com.taskyproject.tasky.data.local.task

import com.taskyproject.tasky.domain.model.Task

interface TaskDao {
    suspend fun insertTask(
        task: Task,
        shouldBeDeleted: Boolean = false,
        shouldBeUpdated: Boolean = false,
        isAddedOnRemote: Boolean = false,
    )
}