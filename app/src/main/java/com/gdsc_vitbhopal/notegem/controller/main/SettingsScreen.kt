package com.gdsc_vitbhopal.notegem.controller.main

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.hilt.navigation.compose.hiltViewModel
import com.gdsc_vitbhopal.notegem.BuildConfig
import com.gdsc_vitbhopal.notegem.R
import com.gdsc_vitbhopal.notegem.controller.settings.SettingsBasicLinkItem
import com.gdsc_vitbhopal.notegem.controller.settings.SettingsItemCard
import com.gdsc_vitbhopal.notegem.controller.settings.SettingsViewModel
import com.gdsc_vitbhopal.notegem.util.Constants
import com.gdsc_vitbhopal.notegem.util.settings.StartUpScreenSettings
import com.gdsc_vitbhopal.notegem.util.settings.ThemeSettings

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalAnimationApi
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.settings),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Bold)
                    )
                },
                backgroundColor = MaterialTheme.colors.background,
                elevation = 0.dp,
            )
        }
    ) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            item {
                Text(
                    text = stringResource(R.string.general),
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .padding(vertical = 16.dp, horizontal = 12.dp)
                )
            }

            item {
                val theme = viewModel
                    .getSettings(
                        intPreferencesKey(Constants.SETTINGS_THEME_KEY), ThemeSettings.AUTO.value
                    ).collectAsState(
                        initial = ThemeSettings.AUTO.value
                    )
                ThemeSettingsItem(theme.value) {
                    when (theme.value) {
                        ThemeSettings.AUTO.value -> viewModel.saveSettings(
                            intPreferencesKey(Constants.SETTINGS_THEME_KEY),
                            ThemeSettings.LIGHT.value
                        )
                        ThemeSettings.LIGHT.value -> viewModel.saveSettings(
                            intPreferencesKey(Constants.SETTINGS_THEME_KEY),
                            ThemeSettings.DARK.value
                        )
                        ThemeSettings.DARK.value -> viewModel.saveSettings(
                            intPreferencesKey(Constants.SETTINGS_THEME_KEY),
                            ThemeSettings.AUTO.value
                        )
                    }
                }
            }
            item {
                val screen = viewModel
                    .getSettings(
                        intPreferencesKey(Constants.DEFAULT_START_UP_SCREEN_KEY),
                        StartUpScreenSettings.DASHBOARD.value
                    ).collectAsState(
                        initial = StartUpScreenSettings.DASHBOARD.value
                    )
                StartUpScreenSettingsItem(
                    screen.value,
                    {
                        viewModel.saveSettings(
                            intPreferencesKey(Constants.DEFAULT_START_UP_SCREEN_KEY),
                            StartUpScreenSettings.DASHBOARD.value
                        )
                    },
                    {
                        viewModel.saveSettings(
                            intPreferencesKey(Constants.DEFAULT_START_UP_SCREEN_KEY),
                            StartUpScreenSettings.HOME.value
                        )
                    }
                )
            }

            item {
                Text(
                    text = stringResource(R.string.about),
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .padding(vertical = 16.dp, horizontal = 12.dp)
                )
            }

            item {
                SettingsBasicLinkItem(
                    title = R.string.project_on_github,
                    icon = R.drawable.ic_github,
                    link = Constants.PROJECT_SOURCE_CODE
                )
            }

            item {
                SettingsBasicLinkItem(
                    title = R.string.app_version,
                    icon = R.drawable.ic_code,
                    subtitle = BuildConfig.VERSION_NAME,
                )
            }


            item {
                Text(
                    text = stringResource(R.string.product),
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .padding(vertical = 16.dp, horizontal = 12.dp)
                )
            }

            item {
                SettingsBasicLinkItem(
                    title = R.string.request_feature_report_bug,
                    icon = R.drawable.ic_feature_issue,
                    link = Constants.GITHUB_ISSUES_LINK
                )
            }
            item { Spacer(Modifier.height(60.dp)) }
        }
    }
}

@Composable
fun ThemeSettingsItem(theme: Int = 0, onClick: () -> Unit = {}) {
    SettingsItemCard(
        onClick = onClick,
    ) {
        Text(
            text = stringResource(R.string.app_theme),
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.Bold
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = when (theme) {
                    ThemeSettings.LIGHT.value -> stringResource(R.string.light_theme)
                    ThemeSettings.DARK.value -> stringResource(R.string.dark_theme)
                    else -> stringResource(R.string.auto_theme)
                },
                style = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                painter = when (theme) {
                    ThemeSettings.LIGHT.value -> painterResource(id = R.drawable.ic_sun)
                    ThemeSettings.DARK.value -> painterResource(id = R.drawable.ic_dark)
                    else -> painterResource(id = R.drawable.ic_auto)
                },
                contentDescription = theme.toString()
            )
        }
    }
}

@Composable
fun StartUpScreenSettingsItem(
    screen: Int,
    onDashboardClick: () -> Unit = {},
    onHomeClick: () -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }
    SettingsItemCard(
        onClick = {
            expanded = true
        },
    ) {
        Text(
            text = stringResource(R.string.start_up_screen),
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.Bold
        )
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = when (screen) {
                        StartUpScreenSettings.DASHBOARD.value -> stringResource(R.string.dashboard)
                        StartUpScreenSettings.HOME.value -> stringResource(R.string.home)
                        else -> stringResource(R.string.dashboard)
                    },
                    style = MaterialTheme.typography.body1
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                DropdownMenuItem(onClick = {
                    onDashboardClick()
                    expanded = false
                }) {
                    Text(text = stringResource(id = R.string.dashboard), style = MaterialTheme.typography.body1)
                }
                DropdownMenuItem(onClick = {
                    onHomeClick()
                    expanded = false
                }) {
                    Text(text = stringResource(id = R.string.home), style = MaterialTheme.typography.body1)
                }
            }
        }
    }
}