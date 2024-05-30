package com.gdsc_vitbhopal.notegem.domain.useCase.calendar

import android.content.Context
import android.content.Intent
import com.gdsc_vitbhopal.notegem.controller.glance_widgets.RefreshCalendarWidgetReceiver
import com.gdsc_vitbhopal.notegem.domain.model.CalendarEvent
import com.gdsc_vitbhopal.notegem.domain.repository.CalendarRepository

import javax.inject.Inject

class UpdateCalendarEventUseCase @Inject constructor(
    private val calendarRepository: CalendarRepository,
    private val context: Context
) {
    suspend operator fun invoke(event: CalendarEvent) {
        calendarRepository.updateEvent(event)
        val updateIntent = Intent(context, RefreshCalendarWidgetReceiver::class.java)
        context.sendBroadcast(updateIntent)
    }
}