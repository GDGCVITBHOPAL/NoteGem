package com.gdsc_vitbhopal.notegem.controller.main

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
import com.gdsc_vitbhopal.notegem.controller.calendar.CalendarHomeWidget
import com.gdsc_vitbhopal.notegem.controller.glance_widgets.TasksHomeWidget
import com.gdsc_vitbhopal.notegem.controller.tasks.TasksHomeWidget
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
                        text = stringResource(R.string.home),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Bold)
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
                CalendarHomeWidget(
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
                )
            }
            item {
                TasksHomeWidget(
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
                    onClick = {
                        navController.navigate(
                            Screen.TasksScreen.route
                        )
                    }
                )
            }
            item {
                Row {
//                    MoodCircularBar(
//                        entries = viewModel.uiState.homeEntries,
//                        showPercentage = false,
//                        modifier = Modifier.weight(1f, fill = true),
//                        onClick = {
//                            navController.navigate(
//                                Screen.DiaryChartScreen.route
//                            )
//                        }
//                    )                    TasksSummaryCard(
//                        modifier = Modifier.weight(1f, fill = true),
//                        tasks = viewModel.uiState.summaryTasks
//                    )

                }
            }
            item { Spacer(Modifier.height(65.dp)) }
        }
    }
}