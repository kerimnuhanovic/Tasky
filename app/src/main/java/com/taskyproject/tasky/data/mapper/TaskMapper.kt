package com.taskyproject.tasky.data.mapper

import com.taskyproject.tasky.data.network.dto.TaskDto
import com.taskyproject.tasky.domain.model.Task
import taskydatabase.TaskEntity

fun TaskDto.toTask(): Task {
    return Task(
        id = id,
        title = title,
        description = description,
        time = time,
        remindAt = remindAt,
        isDone = isDone
    )
}

fun Task.toTaskDto(): TaskDto {
    return TaskDto(
        id = id,
        title = title,
        description = description,
        time = time,
        remindAt = remindAt,
        isDone = isDone
    )
}

fun TaskEntity.toTask(): Task {
    return Task(
        id = id,
        title = title,
        description = description,
        time = time,
        remindAt = remindAt,
        isDone = isDone == 1L
    )
}