package com.gdsc_vitbhopal.notegem.domain.useCase.calendar

import com.gdsc_vitbhopal.notegem.domain.model.Calendar
import com.gdsc_vitbhopal.notegem.domain.repository.CalendarRepository
import javax.inject.Inject

class GetAllCalendarsUseCase @Inject constructor(
    private val calendarRepository: CalendarRepository
) {
    suspend operator fun invoke(excluded: List<Int>): Map<String, List<Calendar>> {
        return calendarRepository.getCalendars().map { it.copy(included = (it.id.toInt() !in excluded)) }.groupBy { it.account }
    }
}