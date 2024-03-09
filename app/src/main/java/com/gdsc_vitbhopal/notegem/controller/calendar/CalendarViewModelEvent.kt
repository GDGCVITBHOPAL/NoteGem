package com.gdsc_vitbhopal.notegem.controller.calendar

import com.gdsc_vitbhopal.notegem.domain.model.Calendar

sealed class CalendarViewModelEvent {
    data class IncludeCalendar(val calendar: Calendar) : CalendarViewModelEvent()
    data class ReadPermissionChanged(val hasPermission: Boolean) : CalendarViewModelEvent()
}