package com.gdsc_vitbhopal.notegem.controller.glance_widgets

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.gdsc_vitbhopal.notegem.domain.useCase.tasks.UpdateTaskCompletedUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class CompleteTaskWidgetReceiver : BroadcastReceiver() {

    @Inject
    lateinit var completeTask: UpdateTaskCompletedUseCase

    override fun onReceive(context: Context, intent: Intent) {
        val id = intent.getIntExtra("taskId", -1)
        val completed = intent.getBooleanExtra("completed", true)
        if (id != -1) {
            runBlocking {
                completeTask(id, completed)
            }
        }
    }
}