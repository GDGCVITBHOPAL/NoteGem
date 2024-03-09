package com.gdsc_vitbhopal.notegem.domain.useCase.calendar

import com.gdsc_vitbhopal.notegem.domain.model.CalendarEvent
import com.gdsc_vitbhopal.notegem.domain.repository.CalendarRepository
import com.gdsc_vitbhopal.notegem.util.date.formatDay
import javax.inject.Inject

class GetAllEventsUseCase @Inject constructor(
    private val calendarRepository: CalendarRepository
) {
    suspend operator fun invoke(excluded: List<Int>): Map<String, List<CalendarEvent>> {
        return calendarRepository.getEvents()
            .filter { it.calendarId.toInt() !in excluded }
            .groupBy { event ->
                event.start.formatDay()
            }
    }
}