package com.gdsc_vitbhopal.notegem.controller.main

import androidx.datastore.preferences.core.intPreferencesKey
import androidx.lifecycle.ViewModel
import com.gdsc_vitbhopal.notegem.domain.useCase.settings.GetSettingsUseCase
import com.gdsc_vitbhopal.notegem.util.Constants
import com.gdsc_vitbhopal.notegem.util.settings.StartUpScreenSettings
import com.gdsc_vitbhopal.notegem.util.settings.ThemeSettings
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getSettings: GetSettingsUseCase
) : ViewModel() {

    val themMode = getSettings(intPreferencesKey(Constants.SETTINGS_THEME_KEY), ThemeSettings.AUTO.value)
    val defaultStartUpScreen = getSettings(intPreferencesKey(Constants.DEFAULT_START_UP_SCREEN_KEY),StartUpScreenSettings.DASHBOARD.value)

}