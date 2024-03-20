package com.gdsc_vitbhopal.notegem.controller.main

//import android.annotation.SuppressLint
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.aspectRatio
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.material.MaterialTheme
//import androidx.compose.material.Scaffold
//import androidx.compose.material.Text
//import androidx.compose.material.TopAppBar
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.derivedStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.glance.appwidget.lazy.LazyColumn
//import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.navigation.NavHostController
//import com.gdsc_vitbhopal.notegem.R
//import com.gdsc_vitbhopal.notegem.controller.calendar.CalendarWidget
//import com.gdsc_vitbhopal.notegem.controller.grocery.MoodCircularBar
//import com.gdsc_vitbhopal.notegem.controller.tasks.TasksWidget
//import com.gdsc_vitbhopal.notegem.controller.util.Screen
//import com.gdsc_vitbhopal.notegem.util.Constants
//import com.gdsc_vitbhopal.notegem.util.date.inTheLastWeek
//
//@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
//@Composable
//fun HomeScreen(
//    navController: NavHostController,
//    viewModel: MainViewModel = hiltViewModel()
//) {
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = {
//                    Text(
//                        text = stringResource(R.string.home),
//                        style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Bold)
//                    )
//                },
//                backgroundColor = MaterialTheme.colors.background,
//                elevation = 0.dp
//            )
//        }
//    ) {paddingValues ->
//        LaunchedEffect(true) { viewModel.onHomeEvent(HomeEvent.InitAll) }
//        LazyColumn(contentPadding = paddingValues){
//            item {
//                CalendarWidget(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .aspectRatio(1.5f),
//                    events = viewModel.uiState.homeEvents,
//                    onClick = {
//                        navController.navigate(
//                            Screen.CalendarScreen.route
//                        )
//                    },
//                    onPermission = {
//                        viewModel.onHomeEvent(HomeEvent.ReadPermissionChanged(it))
//                    }
//                )
//            }
//            item {
//                TasksWidget(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .aspectRatio(1.5f),
//                    tasks = viewModel.uiState.homeTasks,
//                    onCheck = {
//                        viewModel.onHomeEvent(HomeEvent.UpdateTask(it))
//                    },
//                    onTaskClick = {
//                        navController.navigate(
//                            Screen.TaskDetailScreen.route
//                                .replace(
//                                    "{${Constants.TASK_ID_ARG}}",
//                                    it.id.toString()
//                                )
//                        )
//                    },
//                    onAddClick = {
//                        navController.navigate(
//                            Screen.TasksScreen
//                                .route
//                                .replace(
//                                    "{${Constants.ADD_TASK_TILE_ARG}}",
//                                    "true"
//                                )
//                        )
//                    },
//                    onClick = {
//                        navController.navigate(
//                            Screen.TasksScreen.route
//                        )
//                    }
//                )
//            }
//            item {
//                Row {
//                    val tasks = remember(viewModel.uiState.homeTasks) {
//                        derivedStateOf {
//                            viewModel.uiState.homeTasks.filter { it.createdDate.inTheLastWeek() }
//                        }
//                    }
//                    MoodCircularBar(
//                        entries = viewModel.uiState.homeEntries,
//                        showPercentage = false,
//                        modifier = Modifier.weight(1f, fill = true),
//                        onClick = {
//                            navController.navigate(
//                                Screen.DiaryChartScreen.route
//                            )
//                        }
//                    )
//                    TasksSummaryCard(
//                        modifier = Modifier.weight(1f, fill = true),
//                        tasks = tasks.value
//                    )
//                }
//            }
//            item { Spacer(Modifier.height(65.dp)) }
//        }
//    }
//}

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.gdsc_vitbhopal.notegem.R
import com.gdsc_vitbhopal.notegem.controller.calendar.CalendarWidget
import com.gdsc_vitbhopal.notegem.controller.grocery.MoodCircularBar
import com.gdsc_vitbhopal.notegem.controller.tasks.TasksWidget
import com.gdsc_vitbhopal.notegem.controller.util.Screen
import com.gdsc_vitbhopal.notegem.util.Constants

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: MainViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.dashboard),
                        style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold)
                    )
                },
                backgroundColor = MaterialTheme.colors.background,
                elevation = 0.dp
            )
        }
    ) {paddingValues ->
        LaunchedEffect(true) { viewModel.onHomeEvent(HomeEvent.InitAll) }
        LazyColumn(contentPadding = paddingValues) {
            item {
                CalendarWidget(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.5f),
                    events = viewModel.uiState.homeEvents,
                    onClick = {
                        navController.navigate(
                            Screen.CalendarScreen.route
                        )
                    },
                    onPermission = {
                        viewModel.onHomeEvent(HomeEvent.ReadPermissionChanged(it))
                    }
//                    onAddEventClicked = {
//                        navController.navigate(
//                            Screen.CalendarEventDetailsScreen.route.replace(
//                                "{${Constants.CALENDAR_EVENT_ARG}}",
//                                " "
//                            )
//                        )
//                    },
//                    onEventClicked = {
//                        val eventJson = Gson().toJson(it, CalendarEvent::class.java)
//                        // encoding the string to avoid crashes when the event contains fields that equals a URL
//                        val encodedJson = URLEncoder.encode(eventJson, StandardCharsets.UTF_8.toString())
//                        navController.navigate(
//                            Screen.CalendarEventDetailsScreen.route.replace(
//                                "{${Constants.CALENDAR_EVENT_ARG}}",
//                                encodedJson
//                            )
//                        )
//                    }
                )
            }
            item {
                TasksWidget(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.5f),
                    tasks = viewModel.uiState.homeTasks,
                    onCheck = {
                        viewModel.onHomeEvent(HomeEvent.UpdateTask(it))
                    },
                    onTaskClick = {
                        navController.navigate(
                            Screen.TaskDetailScreen.route
                                .replace(
                                    "{${Constants.TASK_ID_ARG}}",
                                    it.id.toString()
                                )
                        )
                    },
//                    onAddClick = {
//                        navController.navigate(
//                            Screen.TasksScreen
//                                .route
//                                .replace(
//                                    "{${Constants.ADD_TASK_ARG}}",
//                                    "true"
//                                )
//                        )
//                    },
                    onClick = {
                        navController.navigate(
                            Screen.TasksScreen.route
                        )
                    }
                )
            }
            item {
                Row {
                    MoodCircularBar(
                        entries = viewModel.uiState.homeEntries,
                        showPercentage = false,
                        modifier = Modifier.weight(1f, fill = true),
                        onClick = {
                            navController.navigate(
                                Screen.DiaryChartScreen.route
                            )
                        }
                    )
                    TasksSummaryCard(
                        modifier = Modifier.weight(1f, fill = true),
                        tasks = viewModel.uiState.summaryTasks
                    )
                }
            }
            item { Spacer(Modifier.height(65.dp)) }
        }
    }
}