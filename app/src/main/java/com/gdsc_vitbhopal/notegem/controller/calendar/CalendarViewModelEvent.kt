package com.gdsc_vitbhopal.notegem.controller.calendar

import com.gdsc_vitbhopal.notegem.domain.model.Calendar
import com.gdsc_vitbhopal.notegem.domain.model.CalendarEvent

sealed class CalendarViewModelEvent {
    data class IncludeCalendar(val calendar: Calendar) : CalendarViewModelEvent()
    data class ReadPermissionChanged(val hasPermission: Boolean) : CalendarViewModelEvent()
    data class EditEvent(val event: CalendarEvent) : CalendarViewModelEvent()
    data class DeleteEvent(val event: CalendarEvent) : CalendarViewModelEvent()
    data class AddEvent(val event: CalendarEvent) : CalendarViewModelEvent()
    object ErrorDisplayed : CalendarViewModelEvent()
}