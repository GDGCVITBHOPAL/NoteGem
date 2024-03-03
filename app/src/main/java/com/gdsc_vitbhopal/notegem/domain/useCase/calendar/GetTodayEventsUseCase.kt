package com.gdsc_vitbhopal.notegem.domain.useCase.calendar

import android.text.format.DateUtils
import com.gdsc_vitbhopal.notegem.domain.model.CalendarEvent
import com.gdsc_vitbhopal.notegem.domain.repository.CalendarRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetTodayEventsUseCase @Inject constructor(
    private val calendarRepository: CalendarRepository
) {
    suspend operator fun invoke(): List<CalendarEvent> {
        val allEvents = calendarRepository.getEvents()
        return withContext(Dispatchers.Default) { allEvents.filter { DateUtils.isToday(it.start) } }
    }
}