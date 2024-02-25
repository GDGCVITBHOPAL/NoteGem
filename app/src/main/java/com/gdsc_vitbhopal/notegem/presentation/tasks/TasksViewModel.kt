package com.gdsc_vitbhopal.notegem.presentation.tasks

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gdsc_vitbhopal.notegem.R
import com.gdsc_vitbhopal.notegem.app.getString
import com.gdsc_vitbhopal.notegem.domain.model.Alarm
import com.gdsc_vitbhopal.notegem.domain.model.Task
import com.gdsc_vitbhopal.notegem.domain.useCase.alarm.AddAlarmUseCase
import com.gdsc_vitbhopal.notegem.domain.useCase.settings.GetSettingsUseCase
import com.gdsc_vitbhopal.notegem.domain.useCase.settings.SaveSettingsUseCase
import com.gdsc_vitbhopal.notegem.domain.useCase.tasks.AddTaskUseCase
import com.gdsc_vitbhopal.notegem.domain.useCase.tasks.GetAllTasksUseCase
import com.gdsc_vitbhopal.notegem.domain.useCase.tasks.UpdateTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val addTask: AddTaskUseCase,
    private val getTasks: GetAllTasksUseCase,
    private val updateTask: UpdateTaskUseCase,
    private val getSettings: GetSettingsUseCase,
    private val saveSettings: SaveSettingsUseCase,
    private val addAlarm: AddAlarmUseCase
): ViewModel() {

    var uiState by mutableStateOf(UiState())
        private set

    init {
        viewModelScope.launch {
            uiState = uiState.copy(
                tasks = getTasks(),
            )
        }
    }

    fun onEvent(event: TaskEvent) {
        when (event) {
            is TaskEvent.AddTask -> {
                if (event.task.title.isNotBlank()) {
                    viewModelScope.launch {
                        val taskId = addTask(event.task)
                        uiState = uiState.copy(tasks = getTasks())
                        if (event.task.dueDate != 0L)
                            addAlarm(
                                Alarm(
                                    taskId.toInt(),
                                    event.task.dueDate,
                                )
                            )
                    }

                }else
                    uiState = uiState.copy(error = getString(R.string.error_empty_title))
            }
            is TaskEvent.CompleteTask -> viewModelScope.launch {
                updateTask(event.task.copy(isCompleted = event.complete))
                uiState = uiState.copy(tasks = getTasks())
            }
            is TaskEvent.GetTasks -> TODO()
            is TaskEvent.AddAlarm -> TODO()
            TaskEvent.ErrorDisplayed -> uiState = uiState.copy(error = null)
        }
    }

    data class UiState(
        val tasks: List<Task> = emptyList(),
        val error: String? = null,
    )

}