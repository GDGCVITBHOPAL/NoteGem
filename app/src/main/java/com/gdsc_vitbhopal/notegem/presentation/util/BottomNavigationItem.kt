package com.gdsc_vitbhopal.notegem.presentation.util

import com.gdsc_vitbhopal.notegem.R

sealed class BottomNavItem(val title: Int, val icon: Int, val iconSelected: Int, val route: String){

    object Dashboard : BottomNavItem(R.string.home, R.drawable.ic_home, R.drawable.ic_home_filled, Screen.HomeScreen.route)
    object Spaces : BottomNavItem(R.string.dashboard, R.drawable.ic_dashboard, R.drawable.ic_dashboard_filled, Screen.DashboardScreen.route)
    object Settings: BottomNavItem(R.string.settings, R.drawable.ic_settings, R.drawable.ic_settings_filled, Screen.SettingsScreen.route)

}