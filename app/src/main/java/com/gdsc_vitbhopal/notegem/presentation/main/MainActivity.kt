package com.gdsc_vitbhopal.notegem.presentation.main

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.gdsc_vitbhopal.notegem.presentation.tasks.TaskDetailScreen
import com.gdsc_vitbhopal.notegem.presentation.tasks.TasksScreen
import com.gdsc_vitbhopal.notegem.presentation.tasks.TasksSearchScreen
import com.gdsc_vitbhopal.notegem.presentation.util.Screen
import com.gdsc_vitbhopal.notegem.ui.theme.DarkBackground
import com.gdsc_vitbhopal.notegem.ui.theme.NoteGemTheme
import com.gdsc_vitbhopal.notegem.util.Constants
import com.gdsc_vitbhopal.notegem.util.settings.StartUpScreenSettings
import com.gdsc_vitbhopal.notegem.util.settings.ThemeSettings
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

@Suppress("BlockingMethodInNonBlockingContext")
@OptIn(ExperimentalAnimationApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    @SuppressLint("AutoboxingStateCreation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val themMode = viewModel.themMode.collectAsState(initial = ThemeSettings.AUTO.value)
            var startUpScreenSettings by remember { mutableStateOf(StartUpScreenSettings.DASHBOARD.value) }
            LaunchedEffect(true) {
                runBlocking {
                    startUpScreenSettings = viewModel.defaultStartUpScreen.first()
                }
            }
            val startUpScreen =
                if (startUpScreenSettings == StartUpScreenSettings.DASHBOARD.value)
                    Screen.DashboardScreen.route else Screen.HomeScreen.route
            val isDarkMode = when (themMode.value) {
                ThemeSettings.DARK.value -> true
                ThemeSettings.LIGHT.value -> false
                else -> isSystemInDarkTheme()
            }
            handleThemeChange(isDarkMode)
            NoteGemTheme(darkTheme = isDarkMode) {
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    NavHost(
                        startDestination = Screen.Main.route,
                        navController = navController
                    ) {
                        composable(Screen.Main.route) {
                            MainScreen(
                                startUpScreen = startUpScreen,
                                mainNavController = navController
                            )
                        }
                        composable(Screen.TasksScreen.route){
                            TasksScreen(navController = navController)
                        }
                        composable(
                            Screen.TaskDetailScreen.route,
                            arguments = listOf(navArgument(Constants.TASK_ID_ARG) {
                                type = NavType.IntType
                            }),
                            deepLinks =
                            listOf(
                                navDeepLink {
                                    uriPattern =
                                        "${Constants.TASK_DETAILS_URI}/{${Constants.TASK_ID_ARG}}"
                                }
                            )
                        ){
                            TaskDetailScreen(navController = navController, it.arguments?.getInt(Constants.TASK_ID_ARG)!!)
                        }
                        composable(Screen.TaskSearchScreen.route) {
                            TasksSearchScreen(navController = navController)
                        }
                        composable(Screen.NotesScreen.route) {}
                        composable(Screen.NoteAddScreen.route) {}
                        composable(Screen.NoteDetailScreen.route) {}
                        composable(Screen.NoteSearchScreen.route) {}
                        composable(Screen.GroceryScreen.route) {}
                        composable(Screen.GroceryAddScreen.route) {}
                        composable(Screen.GrocerySearchScreen.route) {}
                        composable(Screen.GroceryDetailScreen.route) {}
                        composable(Screen.GrocerySummaryScreen.route) {}
                        composable(Screen.BookmarksScreen.route) {}
                        composable(Screen.BookmarkAddScreen.route) {}
                        composable(Screen.BookmarkDetailScreen.route) {}
                        composable(Screen.BookmarkSearchScreen.route) {}
                        composable(Screen.CalendarScreen.route) {}
                        composable(Screen.CalendarSearchScreen.route) {}
                    }
                }
            }
        }
    }

    private fun handleThemeChange(isDarkMode: Boolean) {
        window.statusBarColor = if (isDarkMode) DarkBackground.toArgb() else Color.White.toArgb()
        window.navigationBarColor =
            if (isDarkMode) DarkBackground.toArgb() else Color.White.toArgb()
    }
}