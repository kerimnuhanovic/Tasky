package com.taskyproject.tasky.data.network.task

import com.taskyproject.tasky.data.mapper.toReminderDto
import com.taskyproject.tasky.data.mapper.toTaskDto
import com.taskyproject.tasky.data.network.constants.AUTHORIZATION
import com.taskyproject.tasky.data.network.constants.HttpRoutes
import com.taskyproject.tasky.data.network.constants.REMINDER_ID
import com.taskyproject.tasky.data.network.constants.TASK_ID
import com.taskyproject.tasky.data.network.constants.X_API_KEY
import com.taskyproject.tasky.data.network.dto.TaskDto
import com.taskyproject.tasky.domain.model.Task
import com.taskyproject.tasky.domain.preferences.Preferences
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

class TaskApiImpl @Inject constructor(
    private val client: HttpClient,
    private val preferences: Preferences
) : TaskApi {
    override suspend fun createTask(task: Task) {
        client.post {
            url(HttpRoutes.TASK)
            headers {
                append(X_API_KEY, "619417673")
                append(AUTHORIZATION, preferences.readToken()!!)
            }
            contentType(ContentType.Application.Json)
            setBody(task.toTaskDto())
        }
    }

    override suspend fun updateTask(task: Task) {
        client.put {
            url(HttpRoutes.TASK)
            headers {
                append(X_API_KEY, "619417673")
                append(AUTHORIZATION, preferences.readToken()!!)
            }
            contentType(ContentType.Application.Json)
            setBody(task.toTaskDto())
        }
    }

    override suspend fun deleteTask(taskId: String) {
        client.delete {
            url(HttpRoutes.TASK)
            parameter(TASK_ID, taskId)
            headers {
                append(X_API_KEY, "619417673")
                append(AUTHORIZATION, preferences.readToken()!!)
            }
        }
    }


}