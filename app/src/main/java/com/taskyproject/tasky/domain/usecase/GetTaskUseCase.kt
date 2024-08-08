package com.taskyproject.tasky.domain.usecase

import com.taskyproject.tasky.domain.model.Task
import com.taskyproject.tasky.domain.repository.TaskRepository
import javax.inject.Inject

class GetTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(id: String): Task {
        return taskRepository.getTask(id)
    }
}