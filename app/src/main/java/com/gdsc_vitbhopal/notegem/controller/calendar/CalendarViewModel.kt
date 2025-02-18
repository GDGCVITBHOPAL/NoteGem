package com.gdsc_vitbhopal.notegem.controller.calendar

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gdsc_vitbhopal.notegem.R
import com.gdsc_vitbhopal.notegem.app.getString
import com.gdsc_vitbhopal.notegem.domain.model.Calendar
import com.gdsc_vitbhopal.notegem.domain.model.CalendarEvent
import com.gdsc_vitbhopal.notegem.domain.useCase.calendar.AddCalendarEventUseCase
import com.gdsc_vitbhopal.notegem.domain.useCase.calendar.DeleteCalendarEventUseCase
import com.gdsc_vitbhopal.notegem.domain.useCase.calendar.GetAllCalendarsUseCase
import com.gdsc_vitbhopal.notegem.domain.useCase.calendar.GetAllEventsUseCase
import com.gdsc_vitbhopal.notegem.domain.useCase.calendar.UpdateCalendarEventUseCase
import com.gdsc_vitbhopal.notegem.domain.useCase.settings.GetSettingsUseCase
import com.gdsc_vitbhopal.notegem.domain.useCase.settings.SaveSettingsUseCase
import com.gdsc_vitbhopal.notegem.util.Constants
import com.gdsc_vitbhopal.notegem.util.date.monthName
import com.gdsc_vitbhopal.notegem.util.settings.addAndToStringSet
import com.gdsc_vitbhopal.notegem.util.settings.removeAndToStringSet
import com.gdsc_vitbhopal.notegem.util.settings.toIntList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val getAllEventsUseCase: GetAllEventsUseCase,
    private val getAllCalendarsUseCase: GetAllCalendarsUseCase,
    private val saveSettings: SaveSettingsUseCase,
    private val addEvent: AddCalendarEventUseCase,
    private val editEvent: UpdateCalendarEventUseCase,
    private val deleteEvent: DeleteCalendarEventUseCase,
    private val getSettings: GetSettingsUseCase
) : ViewModel() {

    var uiState by mutableStateOf(UiState())
        private set

    private var updateEventsJob : Job? = null

    fun onEvent(event: CalendarViewModelEvent) {
        when(event){
            is CalendarViewModelEvent.IncludeCalendar -> updateExcludedCalendars(event.calendar.id.toInt(), event.calendar.included)
            is CalendarViewModelEvent.ReadPermissionChanged -> {
                if (event.hasPermission) collectSettings()
                else updateEventsJob?.cancel()
            }
            is CalendarViewModelEvent.AddEvent -> viewModelScope.launch {
                uiState = if (event.event.title.isNotBlank()) {
                    if (event.event.start > System.currentTimeMillis()) {
                        addEvent(event.event)
                        uiState.copy(navigateUp = true)
                    } else {
                        uiState.copy(error = getString(R.string.error_future_event))
                    }
                } else {
                    uiState.copy(error = getString(R.string.error_empty_title))
                }
            }
            is CalendarViewModelEvent.DeleteEvent -> viewModelScope.launch {
                if (event.event.title.isNotBlank()) {
                    deleteEvent(event.event)
                    uiState = uiState.copy(navigateUp = true)
                }
            }
            is CalendarViewModelEvent.EditEvent -> viewModelScope.launch {
                uiState = if (event.event.title.isNotBlank()) {
                    if (event.event.start > System.currentTimeMillis()) {
                        editEvent(event.event)
                        uiState.copy(navigateUp = true)
                    } else {
                        uiState.copy(error = getString(R.string.error_future_event))
                    }
                } else {
                    uiState.copy(error = getString(R.string.error_empty_title))
                }
            }
            CalendarViewModelEvent.ErrorDisplayed -> {
                uiState = uiState.copy(error = null)
            }
        }
    }

    private fun updateExcludedCalendars(id: Int, add: Boolean) {
        viewModelScope.launch {
            saveSettings(
                stringSetPreferencesKey(Constants.EXCLUDED_CALENDARS_KEY),
                if (add) uiState.excludedCalendars.addAndToStringSet(id)
                else uiState.excludedCalendars.removeAndToStringSet(id)
            )
        }
    }

    private fun collectSettings() {
        updateEventsJob = getSettings(
            stringSetPreferencesKey(Constants.EXCLUDED_CALENDARS_KEY),
            emptySet()
        ).onEach { calendarsSet ->
            val events = getAllEventsUseCase(calendarsSet.toIntList())
            val calendars = getAllCalendarsUseCase(calendarsSet.toIntList())
            val allCalendars = getAllCalendarsUseCase(emptyList())
            val months = events.map {
                it.value.first().start.monthName()
            }.distinct()
            uiState = uiState.copy(
                excludedCalendars = calendarsSet.map { it.toInt() }.toMutableList(),
                events = events,
                calendars = calendars,
                months = months,
                calendarsList = allCalendars.values.flatten()
            )
        }.launchIn(viewModelScope)
    }

    data class UiState(
        val events: Map<String, List<CalendarEvent>> = emptyMap(),
        val calendars: Map<String, List<Calendar>> = emptyMap(),
        val calendarsList: List<Calendar> = emptyList(),
        val excludedCalendars: MutableList<Int> = mutableListOf(),
        val months: List<String> = emptyList(),
        val navigateUp: Boolean = false,
        val error: String? = null
    )
}