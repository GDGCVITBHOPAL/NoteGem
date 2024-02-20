package com.gdsc_vitbhopal.notegem.domain.useCase.settings

import androidx.datastore.preferences.core.Preferences
import com.gdsc_vitbhopal.notegem.domain.repository.SettingsRepository
import javax.inject.Inject

class SaveSettingsUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun <T> invoke(key: Preferences.Key<T>, value: T) = settingsRepository.saveSettings(key, value)
}