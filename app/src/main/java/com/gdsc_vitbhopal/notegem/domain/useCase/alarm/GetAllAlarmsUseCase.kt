package com.gdsc_vitbhopal.notegem.domain.useCase.alarm

import com.gdsc_vitbhopal.notegem.domain.repository.AlarmRepository
import javax.inject.Inject

class GetAllAlarmsUseCase @Inject constructor(
    private val alarmRepository: AlarmRepository
) {
    suspend operator fun invoke() = alarmRepository.getAlarms()
}