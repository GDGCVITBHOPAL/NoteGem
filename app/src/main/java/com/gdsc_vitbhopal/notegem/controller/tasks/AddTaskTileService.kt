package com.gdsc_vitbhopal.notegem.controller.tasks

import android.annotation.SuppressLint
import android.content.Intent
import android.service.quicksettings.TileService
import androidx.core.net.toUri
import com.gdsc_vitbhopal.notegem.controller.main.MainActivity
import com.gdsc_vitbhopal.notegem.util.Constants

@Suppress("DEPRECATION")
class AddTaskTileService: TileService() {

    @SuppressLint("StartActivityAndCollapseDeprecated")
    override fun onClick() {
        super.onClick()
        val intent = Intent(
            Intent.ACTION_VIEW,
            "${Constants.ADD_TASK_URI}/true".toUri(),
            this,
            MainActivity::class.java
        ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivityAndCollapse(intent)
    }
}