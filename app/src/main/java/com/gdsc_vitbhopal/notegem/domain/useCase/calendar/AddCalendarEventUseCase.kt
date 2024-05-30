package com.gdsc_vitbhopal.notegem.domain.useCase.calendar

import android.content.Context
import android.content.Intent
import com.gdsc_vitbhopal.notegem.controller.glance_widgets.RefreshCalendarWidgetReceiver
import com.gdsc_vitbhopal.notegem.domain.model.CalendarEvent
import com.gdsc_vitbhopal.notegem.domain.repository.CalendarRepository
import javax.inject.Inject

class AddCalendarEventUseCase @Inject constructor(
    private val calendarEventRepository: CalendarRepository,
    private val context: Context
) {
    suspend operator fun invoke(calendarEvent: CalendarEvent) {
        calendarEventRepository.addEvent(calendarEvent)
        val updateIntent = Intent(context, RefreshCalendarWidgetReceiver::class.java)
        context.sendBroadcast(updateIntent)
    }

}