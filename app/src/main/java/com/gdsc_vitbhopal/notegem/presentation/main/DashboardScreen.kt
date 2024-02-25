package com.gdsc_vitbhopal.notegem.presentation.main

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.gdsc_vitbhopal.notegem.R
import com.gdsc_vitbhopal.notegem.presentation.main.components.DashboardCard
import com.gdsc_vitbhopal.notegem.presentation.main.components.DashboardWideCard
import com.gdsc_vitbhopal.notegem.presentation.util.Screen
import com.gdsc_vitbhopal.notegem.ui.theme.DarkBlueCard
import com.gdsc_vitbhopal.notegem.ui.theme.NoteGemTheme

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DashboardScreen(
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.dashboard),
                        style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.Bold)
                    )
                },
                backgroundColor = MaterialTheme.colors.background,
                elevation = 0.dp
            )
        }
    ) {
        LazyColumn {
            item {
                Row {
                    DashboardCard(
                        modifier = Modifier.weight(1f, fill = true),
                        title = stringResource(R.string.notes),
                        image = R.drawable.notes,
                        backgroundColor = DarkBlueCard
                    ){
                        navController.navigate(Screen.NotesScreen.route)
                    }
                    DashboardCard(
                        modifier = Modifier.weight(1f, fill = true),
                        title = stringResource(R.string.tasks),
                        image = R.drawable.done,
                        backgroundColor = DarkBlueCard
                    ){
                        navController.navigate(Screen.TasksScreen.route)
                    }
                }
            }
            item {
                Row {
                    DashboardCard(
                        modifier = Modifier.weight(1f, fill = true),
                        title = stringResource(R.string.grocery),
                        image = R.drawable.grocery,
                        backgroundColor = DarkBlueCard
                    ){
                        navController.navigate(Screen.GroceryScreen.route)
                    }
                    DashboardCard(
                        modifier = Modifier.weight(1f, fill = true),
                        title = stringResource(R.string.bookmarks),
                        image = R.drawable.bookmark,
                        backgroundColor = DarkBlueCard
                    ){
                        navController.navigate(Screen.BookmarksScreen.route)
                    }
                }
            }
            item {
                DashboardWideCard(
                    title = stringResource(R.string.calendar),
                    image = R.drawable.schedule,
                    backgroundColor = DarkBlueCard
                ){
                    navController.navigate(Screen.CalendarScreen.route)
                }
            }
            item { Spacer(Modifier.height(60.dp)) }
        }
    }
}

@Preview
@Composable
fun DashboardScreenPreview() {
    val navController = rememberNavController()
    NoteGemTheme {
        DashboardScreen(navController = navController)
    }
}