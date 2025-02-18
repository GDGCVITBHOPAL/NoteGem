package com.gdsc_vitbhopal.notegem.controller.main.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gdsc_vitbhopal.notegem.controller.main.DashboardScreen
import com.gdsc_vitbhopal.notegem.controller.main.HomeScreen
import com.gdsc_vitbhopal.notegem.controller.main.SettingsScreen
import com.gdsc_vitbhopal.notegem.controller.util.Screen

@ExperimentalAnimationApi
@Composable
fun NavigationGraph(
    navController: NavHostController,
    mainNavController: NavHostController,
    startUpScreen: String
) {
    NavHost(navController = navController, startDestination = startUpScreen){

        composable(Screen.HomeScreen.route){
            HomeScreen(mainNavController)
        }
        composable(Screen.DashboardScreen.route){
            DashboardScreen(mainNavController)
        }
        composable(Screen.SettingsScreen.route){
            SettingsScreen()
        }
    }
}