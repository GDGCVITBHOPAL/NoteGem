package com.gdsc_vitbhopal.notegem.domain.useCase.settings

import androidx.datastore.preferences.core.Preferences
import com.gdsc_vitbhopal.notegem.domain.repository.SettingsRepository
import javax.inject.Inject

class GetSettingsUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    operator fun <T> invoke(key: Preferences.Key<T>, defaultValue: T) = settingsRepository.getSettings(key, defaultValue)
}