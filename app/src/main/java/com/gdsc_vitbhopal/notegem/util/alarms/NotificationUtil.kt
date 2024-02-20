package com.gdsc_vitbhopal.notegem.util.alarms

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.gdsc_vitbhopal.notegem.R
import com.gdsc_vitbhopal.notegem.app.getString
import com.gdsc_vitbhopal.notegem.domain.model.Task
import com.gdsc_vitbhopal.notegem.util.Constants

fun NotificationManager.sendNotification(task: Task, context: Context, id: Int) {

    val completeIntent = Intent(context, TaskActionButtonBroadcastReceiver::class.java).apply {
        action = Constants.ACTION_COMPLETE
        putExtra(Constants.TASK_ID_EXTRA, task.id)
    }
    val completePendingIntent: PendingIntent =
        PendingIntent.getBroadcast(
            context,
            task.id,
            completeIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    val notification = NotificationCompat.Builder(context, Constants.REMINDERS_CHANNEL_ID)
//       TODO() .setSmallIcon()
        .setSmallIcon(R.drawable.icon_small)
        .setContentTitle(task.title)
        .setContentText(task.description)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .addAction(R.drawable.ic_check, getString(R.string.complete), completePendingIntent)
        .setAutoCancel(true)
        .build()

    notify(id, notification)
}

fun NotificationManager.cancelNotification(id: Int) {
    cancel(id)
}