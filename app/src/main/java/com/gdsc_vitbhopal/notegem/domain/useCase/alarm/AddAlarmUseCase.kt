package com.gdsc_vitbhopal.notegem.domain.useCase.alarm

import android.app.AlarmManager
import android.content.Context
import com.gdsc_vitbhopal.notegem.domain.model.Alarm
import com.gdsc_vitbhopal.notegem.domain.repository.AlarmRepository
import com.gdsc_vitbhopal.notegem.util.alarms.scheduleAlarm
import javax.inject.Inject

class AddAlarmUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository,
    private val context: Context
) {
    suspend operator fun invoke(alarm: Alarm) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.scheduleAlarm(alarm, context)
        alarmRepository.insertAlarm(alarm)
    }
}