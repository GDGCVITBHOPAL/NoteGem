package com.gdsc_vitbhopal.notegem.controller.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gdsc_vitbhopal.notegem.domain.model.CalendarEvent
import com.gdsc_vitbhopal.notegem.domain.model.GroceryEntry
import com.gdsc_vitbhopal.notegem.domain.model.Task
import com.gdsc_vitbhopal.notegem.domain.useCase.calendar.GetAllEventsUseCase
import com.gdsc_vitbhopal.notegem.domain.useCase.grocery.GetAllEntriesUseCase
import com.gdsc_vitbhopal.notegem.domain.useCase.settings.GetSettingsUseCase
import com.gdsc_vitbhopal.notegem.domain.useCase.tasks.GetAllTasksUseCase
import com.gdsc_vitbhopal.notegem.domain.useCase.tasks.UpdateTaskUseCase
import com.gdsc_vitbhopal.notegem.util.Constants
import com.gdsc_vitbhopal.notegem.util.settings.StartUpScreenSettings
import com.gdsc_vitbhopal.notegem.util.settings.ThemeSettings
import com.gdsc_vitbhopal.notegem.util.settings.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getSettings: GetSettingsUseCase,
    private val getAllTasks: GetAllTasksUseCase,
    private val getAllEntriesUseCase: GetAllEntriesUseCase,
    private val upDateTask: UpdateTaskUseCase,
    private val getAllEventsUseCase: GetAllEventsUseCase
) : ViewModel() {

    var uiState by mutableStateOf(UiState())
        private set

    private var refreshTasksJob : Job? = null

    val themMode = getSettings(intPreferencesKey(Constants.SETTINGS_THEME_KEY), ThemeSettings.AUTO.value)
    val defaultStartUpScreen = getSettings(intPreferencesKey(Constants.DEFAULT_START_UP_SCREEN_KEY), StartUpScreenSettings.DASHBOARD.value)

    fun onHomeEvent(event: HomeEvent) {
        when(event) {
            is HomeEvent.ReadPermissionChanged -> {
                if (event.hasPermission)
                    getCalendarEvents()
            }
            is HomeEvent.UpdateTask -> viewModelScope.launch {
                upDateTask(event.task)
            }
            HomeEvent.InitAll -> collectHomeData()
        }
    }

    data class UiState(
        val homeTasks: List<Task> = emptyList(),
        val homeEvents: Map<String, List<CalendarEvent>> = emptyMap(),
        val summaryTasks: List<Task> = emptyList(),
        val homeEntries: List<GroceryEntry> = emptyList()
    )

    private fun getCalendarEvents() = viewModelScope.launch {
        val excluded = getSettings(
            stringSetPreferencesKey(Constants.EXCLUDED_CALENDARS_KEY),
            emptySet()
        ).first()
        val events = getAllEventsUseCase(excluded.toIntList())
        uiState = uiState.copy(
            homeEvents = events
        )
    }

    private fun collectHomeData() = viewModelScope.launch {
        combine(
            getSettings(
                intPreferencesKey(Constants.TASKS_ORDER_KEY),
                Order.DateModified(OrderType.ASC()).toInt()
            ),
            getSettings(
                booleanPreferencesKey(Constants.SHOW_COMPLETED_TASKS_KEY),
                false
            ),
            getAllEntriesUseCase(Order.DateCreated(OrderType.ASC()))
        ) { order, showCompleted, entries ->
            uiState = uiState.copy(
                homeEntries = entries,
            )
            refreshTasks(order.toOrder(), showCompleted)
        }.collect()
    }

    private fun refreshTasks(order: Order, showCompleted: Boolean) {
        refreshTasksJob?.cancel()
        refreshTasksJob = getAllTasks(order)
            .map { list ->
                if (showCompleted)
                    list
                else
                    list.filter { !it.isCompleted }
            }.onEach { tasks ->
                uiState = uiState.copy(
                    homeTasks = tasks
                )
            }.launchIn(viewModelScope)
    }

}