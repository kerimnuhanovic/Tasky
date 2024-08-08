package com.taskyproject.tasky.data.local.task

import com.taskyproject.tasky.domain.model.Task
import taskydatabase.TaskEntity
import taskydatabase.TaskEntityQueries
import javax.inject.Inject

class TaskDaoImpl @Inject constructor(
    private val taskEntityQueries: TaskEntityQueries
) : TaskDao {
    override suspend fun insertTask(
        task: Task,
        shouldBeDeleted: Boolean,
        shouldBeUpdated: Boolean,
        isAddedOnRemote: Boolean,
    ) {
        taskEntityQueries.insertTask(
            id = task.id,
            title = task.title,
            description = task.description,
            time = task.time,
            remindAt = task.remindAt,
            isDone = if (task.isDone) 1 else 0,
            shouldBeDeleted = if (shouldBeDeleted) 1 else 0,
            shouldBeUpdated = if (shouldBeUpdated) 1 else 0,
            isAddedOnRemote = if (isAddedOnRemote) 1 else 0
        )
    }

    override suspend fun getTask(id: String): TaskEntity {
        return taskEntityQueries.getTask(id).executeAsOne()
    }

    override suspend fun updateTask(
        task: Task,
        shouldBeDeleted: Boolean,
        shouldBeUpdated: Boolean,
        isAddedOnRemote: Boolean
    ) {
        taskEntityQueries.updateReminder(
            id = task.id,
            title = task.title,
            description = task.description,
            time = task.time,
            remindAt = task.remindAt,
            isDone = if (task.isDone) 1 else 0,
            shouldBeDeleted = if (shouldBeDeleted) 1 else 0,
            shouldBeUpdated = if (shouldBeUpdated) 1 else 0,
            isAddedOnRemote = if (isAddedOnRemote) 1 else 0
        )
    }
}