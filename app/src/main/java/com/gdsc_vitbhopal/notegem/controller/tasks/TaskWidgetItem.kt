package com.gdsc_vitbhopal.notegem.controller.tasks

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.Action
import androidx.glance.action.actionParametersOf
import androidx.glance.action.clickable
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.cornerRadius
import androidx.glance.background
import androidx.glance.layout.*
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextDecoration
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.gdsc_vitbhopal.notegem.R
import com.gdsc_vitbhopal.notegem.controller.glance_widgets.CompleteTaskAction
import com.gdsc_vitbhopal.notegem.controller.glance_widgets.TaskWidgetItemClickAction
import com.gdsc_vitbhopal.notegem.controller.glance_widgets.completed
import com.gdsc_vitbhopal.notegem.controller.glance_widgets.taskId
import com.gdsc_vitbhopal.notegem.domain.model.Task
import com.gdsc_vitbhopal.notegem.util.date.formatDate
import com.gdsc_vitbhopal.notegem.util.date.isDueDateOverdue
import com.gdsc_vitbhopal.notegem.util.settings.Priority
import com.gdsc_vitbhopal.notegem.util.settings.toPriority

@Composable
fun TaskWidgetItem(
    task: Task
) {
    Box(
        GlanceModifier
            .padding(bottom = 3.dp)
            .clickable(
                onClick = actionRunCallback<TaskWidgetItemClickAction>(
                    parameters = actionParametersOf(
                        taskId to task.id
                    )
                )
            )
    ) {
        Column(
            GlanceModifier
                .background(ImageProvider(R.drawable.small_item_rounded))
                .padding(10.dp)
        ) {
            Row(GlanceModifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                TaskWidgetCheckBox(
                    isComplete = task.isCompleted,
                    task.priority.toPriority().color,
                    onComplete = actionRunCallback<CompleteTaskAction>(
                        parameters = actionParametersOf(
                            taskId to task.id,
                            completed to !task.isCompleted
                        )
                    )
                )
                Spacer(GlanceModifier.width(6.dp))
                Text(
                    task.title,
                    style = TextStyle(
                        color = ColorProvider(Color.White),
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        textDecoration = if (task.isCompleted) TextDecoration.LineThrough else TextDecoration.None
                    ),
                    maxLines = 2,
                )
            }
            if (task.dueDate != 0L) {
                Spacer(GlanceModifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        modifier = GlanceModifier.size(10.dp),
                        provider = if (task.dueDate.isDueDateOverdue()) ImageProvider(R.drawable.ic_alarm_red) else ImageProvider(R.drawable.ic_alarm),
                        contentDescription = "",
                    )
                    Spacer(GlanceModifier.width(3.dp))
                    Text(
                        text = task.dueDate.formatDate(),
                        style = TextStyle(
                            color = if (task.dueDate.isDueDateOverdue()) ColorProvider(Color.Red) else ColorProvider(Color.White),
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp,
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun TaskWidgetCheckBox(
    isComplete: Boolean,
    borderColor: Color,
    onComplete: Action
) {
    Box(
        modifier = GlanceModifier
            .size(25.dp)
            .cornerRadius(99.dp)
            .background(ImageProvider(
                when (borderColor){
                    Priority.LOW.color -> R.drawable.task_check_box_background
                    Priority.MEDIUM.color -> R.drawable.task_orange_check_box
                    else -> R.drawable.task_red_check_box
                }))
            .clickable(
                onClick = onComplete
            ).padding(3.dp)
    ) {
        if (isComplete) {
            Image(
                modifier = GlanceModifier.size(14.dp),
                provider = ImageProvider(R.drawable.ic_check),
                contentDescription = null
            )
        }
    }
}