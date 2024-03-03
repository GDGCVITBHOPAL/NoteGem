package com.gdsc_vitbhopal.notegem.domain.useCase.calendar


import com.gdsc_vitbhopal.notegem.domain.model.CalendarEvent
import com.gdsc_vitbhopal.notegem.domain.repository.CalendarRepository
import javax.inject.Inject

class GetAllEventsUseCase @Inject constructor(
    private val calendarRepository: CalendarRepository
) {
    suspend operator fun invoke(): List<CalendarEvent> {
        return calendarRepository.getEvents()
    }
}