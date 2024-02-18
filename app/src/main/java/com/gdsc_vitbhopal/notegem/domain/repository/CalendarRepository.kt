package com.gdsc_vitbhopal.notegem.domain.repository

import com.gdsc_vitbhopal.notegem.domain.model.CalendarEvent

interface CalendarRepository {
    suspend fun getEvents(): List<CalendarEvent>
}