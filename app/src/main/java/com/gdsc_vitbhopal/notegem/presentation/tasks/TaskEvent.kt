package com.gdsc_vitbhopal.notegem.presentation.tasks

import com.gdsc_vitbhopal.notegem.domain.model.Task


sealed class TaskEvent {
    data class CompleteTask(val task: Task, val complete: Boolean) : TaskEvent()
    data class AddTask(val task: Task) : TaskEvent()
    data class GetTasks(val query: String) : TaskEvent()
    data class AddAlarm(val task: Task) : TaskEvent()
    object ErrorDisplayed: TaskEvent()
}