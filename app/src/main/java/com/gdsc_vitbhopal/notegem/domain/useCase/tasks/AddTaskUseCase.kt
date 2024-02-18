package com.gdsc_vitbhopal.notegem.domain.useCase.tasks

import com.gdsc_vitbhopal.notegem.domain.model.Task
import com.gdsc_vitbhopal.notegem.domain.repository.TaskRepository
import javax.inject.Inject

class AddTaskUseCase @Inject constructor(
    private val tasksRepository: TaskRepository
) {
    suspend operator fun invoke(task: Task) = tasksRepository.insertTask(task)
}