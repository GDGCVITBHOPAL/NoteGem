package com.gdsc_vitbhopal.notegem.controller.main

import com.gdsc_vitbhopal.notegem.domain.model.Task

sealed class HomeEvent {
    data class ReadPermissionChanged(val hasPermission: Boolean) : HomeEvent()
    data class UpdateTask(val task: Task) : HomeEvent()
    object InitAll : HomeEvent()
}