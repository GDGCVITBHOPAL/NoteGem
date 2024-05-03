package com.gdsc_vitbhopal.notegem.controller.main

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
import com.gdsc_vitbhopal.notegem.controller.bookmarks.BookmarkDetailsScreen
import com.gdsc_vitbhopal.notegem.controller.bookmarks.BookmarkSearchScreen
import com.gdsc_vitbhopal.notegem.controller.bookmarks.BookmarksScreen
import com.gdsc_vitbhopal.notegem.controller.calendar.CalendarScreen
import com.gdsc_vitbhopal.notegem.controller.grocery.GroceryChartScreen
import com.gdsc_vitbhopal.notegem.controller.grocery.GroceryEntryDetailsScreen
import com.gdsc_vitbhopal.notegem.controller.grocery.GroceryScreen
import com.gdsc_vitbhopal.notegem.controller.grocery.GrocerySearchScreen
import com.gdsc_vitbhopal.notegem.controller.notes.NoteDetailsScreen
import com.gdsc_vitbhopal.notegem.controller.notes.NotesScreen
import com.gdsc_vitbhopal.notegem.controller.notes.NotesSearchScreen
import com.gdsc_vitbhopal.notegem.controller.tasks.TaskDetailScreen
import com.gdsc_vitbhopal.notegem.controller.tasks.TasksScreen
import com.gdsc_vitbhopal.notegem.controller.tasks.TasksSearchScreen
import com.gdsc_vitbhopal.notegem.controller.util.Screen
import com.gdsc_vitbhopal.notegem.ui.theme.BlueSurface
import com.gdsc_vitbhopal.notegem.ui.theme.DarkBackground
import com.gdsc_vitbhopal.notegem.ui.theme.Kanit
import com.gdsc_vitbhopal.notegem.ui.theme.NoteGemTheme
import com.gdsc_vitbhopal.notegem.util.Constants
import com.gdsc_vitbhopal.notegem.util.settings.StartUpScreenSettings
import com.gdsc_vitbhopal.notegem.util.settings.ThemeSettings
import com.gdsc_vitbhopal.notegem.util.settings.toFontFamily
import com.gdsc_vitbhopal.notegem.util.settings.toInt
import com.google.accompanist.systemuicontroller.rememberSystemUiController
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
            val themeMode = viewModel.themeMode.collectAsState(initial = ThemeSettings.AUTO.value)
            val font = viewModel.font.collectAsState(initial = Kanit.toInt())
            var startUpScreenSettings by remember { mutableStateOf(StartUpScreenSettings.DASHBOARD.value) }
            val systemUiController = rememberSystemUiController()
            LaunchedEffect(true) {
                runBlocking {
                    startUpScreenSettings = viewModel.defaultStartUpScreen.first()
                }
            }
            val startUpScreen =
                if (startUpScreenSettings == StartUpScreenSettings.DASHBOARD.value)
                    Screen.DashboardScreen.route else Screen.HomeScreen.route
            val isDarkMode = when (themeMode.value) {
                ThemeSettings.DARK.value -> true
                ThemeSettings.LIGHT.value -> false
                else -> isSystemInDarkTheme()
            }
            val isAutoMode = when (themeMode.value) {
                ThemeSettings.AUTO.value -> true
                else -> false
            }
//            handleThemeChange(isDarkMode)
            val color = MaterialTheme.colors.background
            println("red: ${color.toArgb()}, green: ${color.green}, blue: ${color.blue}")
            SideEffect{
                systemUiController.setSystemBarsColor(
                    if (isDarkMode) DarkBackground else Color.White,
                    darkIcons = !isDarkMode
                )
            }
            NoteGemTheme(darkTheme = isDarkMode, fontFamily = font.value.toFontFamily()) {
                val navController = rememberNavController()
                if (!isDarkMode) {
                    LaunchedEffect(true) {
                        systemUiController.setSystemBarsColor(
                            BlueSurface,
                            darkIcons = true
                        )
                    }
                }
                if (!isAutoMode){
                    LaunchedEffect(true){
                        systemUiController.setSystemBarsColor(
                            BlueSurface,
                            darkIcons = true
                        )
                    }
                }
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
                        composable(
                            Screen.TasksScreen.route,
                            arguments = listOf(navArgument(Constants.ADD_TASK_TILE_ARG) {
                                type = NavType.BoolType
                                defaultValue = false
                            }),
                            deepLinks =
                            listOf(
                                navDeepLink {
                                    uriPattern = "${Constants.ADD_TASK_URI}/{${Constants.ADD_TASK_TILE_ARG}}"
                                }
                            )
                        ) {
                            TasksScreen(
                                navController = navController,
                                addTask = it.arguments?.getBoolean(Constants.ADD_TASK_TILE_ARG) ?: false
                            )
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
//                        composable(Screen.NotesScreen.route) {}
                        composable(
                            Screen.NotesScreen.route
                        ) {
                            NotesScreen(navController = navController)
                        }
                        composable(Screen.NoteAddScreen.route) {}
//                        composable(Screen.NoteDetailScreen.route) {}
                        composable(
                            Screen.NoteDetailsScreen.route,
                            arguments = listOf(navArgument(Constants.NOTE_ID_ARG) {
                                type = NavType.IntType
                            })
                        ) {
                            NoteDetailsScreen(
                                navController,
                                it.arguments?.getInt(Constants.NOTE_ID_ARG)!!
                            )
                        }
//                        composable(Screen.NoteSearchScreen.route) {}
                        composable(Screen.NoteSearchScreen.route) {
                            NotesSearchScreen(navController = navController)
                        }
                        composable(Screen.GroceryScreen.route) {
                            GroceryScreen(navController = navController)
                        }
                        composable(Screen.GrocerySearchScreen.route) {
                            GrocerySearchScreen(navController = navController)
                        }
                        composable(Screen.GroceryChartScreen.route) {
                            GroceryChartScreen()
                        }
                        composable(
                            Screen.GroceryDetailScreen.route,
                            arguments = listOf(navArgument(Constants.GROCERY_ID_ARG) {
                                type = NavType.IntType
                            })
                        ) {
                            GroceryEntryDetailsScreen(
                                navController = navController,
                                it.arguments?.getInt(Constants.GROCERY_ID_ARG)!!
                            )
                        }
//                        composable(Screen.BookmarksScreen.route) {}
//                        composable(Screen.BookmarkAddScreen.route) {}
//                        composable(Screen.BookmarkDetailScreen.route) {}
//                        composable(Screen.BookmarkSearchScreen.route) {}
                        composable(Screen.BookmarksScreen.route) {
                            BookmarksScreen(navController = navController)
                        }
                        composable(
                            Screen.BookmarkDetailScreen.route,
                            arguments = listOf(navArgument(Constants.BOOKMARK_ID_ARG) {
                                type = NavType.IntType
                            })
                        ) {
                            BookmarkDetailsScreen(
                                navController = navController,
                                it.arguments?.getInt(Constants.BOOKMARK_ID_ARG)!!
                            )
                        }
                        composable(Screen.BookmarkSearchScreen.route) {
                            BookmarkSearchScreen(navController = navController)
                        }
                        composable(
                            Screen.CalendarScreen.route,
                            deepLinks = listOf(
                                navDeepLink {
                                    uriPattern = Constants.CALENDAR_SCREEN_URI
                                }
                            )
                        ) {
                            CalendarScreen()
                        }
                        composable(Screen.CalendarSearchScreen.route) {}
                    }
                }
            }
        }
    }

//    private fun handleThemeChange(isDarkMode: Boolean) {
//        window.statusBarColor = if (isDarkMode) DarkBackground.toArgb() else Color.White.toArgb()
//        window.navigationBarColor =
//            if (isDarkMode) DarkBackground.toArgb() else Color.White.toArgb()
//    }
}
