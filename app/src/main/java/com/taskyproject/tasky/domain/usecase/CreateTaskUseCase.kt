package com.taskyproject.tasky.domain.usecase

import com.taskyproject.tasky.domain.model.Task
import com.taskyproject.tasky.domain.repository.TaskRepository
import com.taskyproject.tasky.domain.util.Result
import javax.inject.Inject

class CreateTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(task: Task): Result<Task> {
        return taskRepository.createTask(task)
    }
}