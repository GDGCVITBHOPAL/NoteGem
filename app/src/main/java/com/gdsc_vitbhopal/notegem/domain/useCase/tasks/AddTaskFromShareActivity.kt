package com.gdsc_vitbhopal.notegem.domain.useCase.tasks

import com.gdsc_vitbhopal.notegem.controller.tasks.TasksViewModel
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import com.gdsc_vitbhopal.notegem.R
import com.gdsc_vitbhopal.notegem.controller.tasks.TaskEvent
import com.gdsc_vitbhopal.notegem.domain.model.Task
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTaskFromShareActivity : ComponentActivity() {

    private val viewModel: TasksViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (intent != null) {
            if (intent.action == Intent.ACTION_SEND && intent.type == "text/plain") {
                val title = intent.getStringExtra(Intent.EXTRA_TEXT)
                if (!title.isNullOrBlank()) {
                    viewModel.onEvent(
                        TaskEvent.AddTask(
                            Task(
                                title = title,
                                createdDate = System.currentTimeMillis(),
                                updatedDate = System.currentTimeMillis()
                            )
                        )
                    )
                    Toast.makeText(this, getString(R.string.added_task), Toast.LENGTH_SHORT)
                        .show()
                } else
                    Toast.makeText(this, getString(R.string.error_empty_title), Toast.LENGTH_SHORT)
                        .show()
            }
        }
        finish()
    }
}