package com.gdsc_vitbhopal.notegem.domain.useCase.alarm

import com.gdsc_vitbhopal.notegem.domain.model.Alarm
import com.gdsc_vitbhopal.notegem.domain.repository.AlarmRepository
import javax.inject.Inject

class DeleteAlarmUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository
) {
    suspend operator fun invoke(alarm: Alarm) = alarmRepository.deleteAlarm(alarm)

    suspend operator fun invoke(alarmId: Int) = alarmRepository.deleteAlarm(alarmId)
}