package com.gdsc_vitbhopal.notegem.controller.glance_widgets

import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.CalendarContract
import android.provider.Settings
import androidx.core.net.toUri
import androidx.glance.GlanceId
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.action.ActionCallback
import com.gdsc_vitbhopal.notegem.controller.main.MainActivity
import com.gdsc_vitbhopal.notegem.util.Constants

class AddEventAction : ActionCallback {
    suspend fun onRun(context: Context, glanceId: GlanceId, parameters: ActionParameters) {
        val intent = Intent(Intent.ACTION_INSERT).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.type = "vnd.android.cursor.item/event"
        context.startActivity(intent)
    }

    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        TODO("Not yet implemented")
    }

}

class NavigateToCalendarAction : ActionCallback {
     suspend fun onRun(context: Context, glanceId: GlanceId, parameters: ActionParameters) {
        val intent = Intent(
            Intent.ACTION_VIEW,
            Constants.CALENDAR_SCREEN_URI.toUri(),
            context,
            MainActivity::class.java
        ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        TODO("Not yet implemented")
    }
}

class CalendarWidgetItemClick : ActionCallback {
    suspend fun onRun(context: Context, glanceId: GlanceId, parameters: ActionParameters) {
        parameters[eventIdKey]?.let {
            val intent = Intent(Intent.ACTION_VIEW).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.data = ContentUris.withAppendedId(
                CalendarContract.Events.CONTENT_URI,
                it
            )
            context.startActivity(intent)
        }
    }

    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        TODO("Not yet implemented")
    }
}

class GoToSettingsAction : ActionCallback {
    suspend fun onRun(context: Context, glanceId: GlanceId, parameters: ActionParameters) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.data = Uri.fromParts("package", context.packageName, null)
        context.startActivity(intent)
    }

    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        TODO("Not yet implemented")
    }
}

class RefreshCalendarAction : ActionCallback {
    suspend fun onRun(context: Context, glanceId: GlanceId, parameters: ActionParameters) {
        val updateIntent = Intent(context, RefreshCalendarWidgetReceiver::class.java)
        context.sendBroadcast(updateIntent)
    }

    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        TODO("Not yet implemented")
    }
}


val eventIdKey = ActionParameters.Key<Long>("eventId")