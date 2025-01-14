package com.taskyproject.tasky.data.repository

import com.taskyproject.tasky.data.local.task.TaskDao
import com.taskyproject.tasky.data.mapper.toTask
import com.taskyproject.tasky.data.network.task.TaskApi
import com.taskyproject.tasky.domain.model.Task
import com.taskyproject.tasky.domain.repository.TaskRepository
import com.taskyproject.tasky.domain.util.Result
import com.taskyproject.tasky.domain.util.handleApiError
import java.net.UnknownHostException
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao,
    private val taskApi: TaskApi
) : TaskRepository {
    override suspend fun createTask(task: Task): Result<Task> {
        return try {
            taskDao.insertTask(task)
            taskApi.createTask(task)
            taskDao.insertTask(task, isAddedOnRemote = true)
            Result.Success(task)
        } catch (ex: UnknownHostException) {
            Result.Success(task)
        } catch (ex: Exception) {
            handleApiError(ex)
        }
    }

    override suspend fun getTask(id: String): Task {
        return taskDao.getTask(id).toTask()
    }

    override suspend fun updateTask(task: Task): Result<Task> {
        return try {
            val taskDocument = taskDao.getTask(task.id)
            taskDao.updateTask(
                task = task,
                isAddedOnRemote = taskDocument.isAddedOnRemote == 1L,
                shouldBeUpdated = taskDocument.isAddedOnRemote == 1L
            )
            if (taskDocument.isAddedOnRemote == 1L) {
                taskApi.updateTask(task)
                taskDao.updateTask(task, isAddedOnRemote = true, shouldBeUpdated = false)
            }
            Result.Success(task)
        } catch (ex: UnknownHostException) {
            Result.Success(task)
        } catch (ex: Exception) {
            handleApiError(ex)
        }
    }

    override suspend fun deleteTask(taskId: String): Result<Unit> {
        return try {
            val task = taskDao.getTask(taskId)
            if (task.isAddedOnRemote == 1L) {
                taskDao.markTaskForDelete(taskId)
                taskApi.deleteTask(taskId)
                taskDao.deleteTask(taskId)
            } else {
                taskDao.deleteTask(taskId)
            }
            Result.Success(Unit)
        } catch (ex: UnknownHostException) {
            Result.Success(Unit)
        } catch (ex: Exception) {
            handleApiError(ex)
        }
    }
}