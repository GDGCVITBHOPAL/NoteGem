package com.gdsc_vitbhopal.notegem.domain.useCase.tasks

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import com.gdsc_vitbhopal.notegem.controller.glance_widgets.TasksWidgetReceiver
import com.gdsc_vitbhopal.notegem.domain.model.Task
import com.gdsc_vitbhopal.notegem.domain.repository.TaskRepository
import javax.inject.Inject

class AddTaskUseCase @Inject constructor(
    private val tasksRepository: TaskRepository,
    private val context: Context
) {
    suspend operator fun invoke(task: Task): Long {
        val id = tasksRepository.insertTask(task)
        context.refreshTasksWidget()
        return id
    }
}

fun Context.refreshTasksWidget() {
    val updateIntent = Intent(this, TasksWidgetReceiver::class.java)
    updateIntent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE

    val widgetManager = AppWidgetManager.getInstance(this)
    val ids = widgetManager.getAppWidgetIds(
        ComponentName(
            this,
            TasksWidgetReceiver::class.java
        )
    )
    updateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)

    sendBroadcast(updateIntent)
}