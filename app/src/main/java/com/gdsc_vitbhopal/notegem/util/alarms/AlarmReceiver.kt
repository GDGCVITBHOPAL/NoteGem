package com.gdsc_vitbhopal.notegem.util.alarms


import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import com.gdsc_vitbhopal.notegem.domain.useCase.alarm.DeleteAlarmUseCase
import com.gdsc_vitbhopal.notegem.domain.useCase.tasks.GetTaskByIdUseCase
import com.gdsc_vitbhopal.notegem.domain.useCase.tasks.UpdateTaskUseCase
import com.gdsc_vitbhopal.notegem.util.Constants
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var deleteAlarmUseCase: DeleteAlarmUseCase
    @Inject
    lateinit var getTaskByIdUseCase: GetTaskByIdUseCase
//    @Inject
//    lateinit var updateTaskByIdUseCase: UpdateTaskUseCase

    override fun onReceive(context: Context?, intent: Intent?) {
        runBlocking {
            val task = intent?.getIntExtra(Constants.TASK_ID_EXTRA, 0)?.let { getTaskByIdUseCase(it) }
            task?.let {
                val manager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                manager.sendNotification(task, context, task.id)
//                updateTaskByIdUseCase(task.copy(dueDate = 0L))
                deleteAlarmUseCase(task.id)
            }
        }
    }
}