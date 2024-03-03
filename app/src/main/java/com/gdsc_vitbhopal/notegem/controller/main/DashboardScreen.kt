package com.gdsc_vitbhopal.notegem.controller.main

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
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
import com.gdsc_vitbhopal.notegem.controller.main.components.DashboardCard
import com.gdsc_vitbhopal.notegem.controller.main.components.DashboardTallCard
import com.gdsc_vitbhopal.notegem.controller.main.components.DashboardWideCard
import com.gdsc_vitbhopal.notegem.controller.util.Screen
import com.gdsc_vitbhopal.notegem.ui.theme.DarkCard1
import com.gdsc_vitbhopal.notegem.ui.theme.DarkCard2
import com.gdsc_vitbhopal.notegem.ui.theme.LightBlueCard
import com.gdsc_vitbhopal.notegem.ui.theme.ModerateBlueCard
import com.gdsc_vitbhopal.notegem.ui.theme.NoteGemTheme

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DashboardScreen(
    navController: NavHostController
) {
    val isDarkMode = isSystemInDarkTheme()

    // Define the colors based on the theme
    val primaryColor = if (isDarkMode) DarkCard1 else ModerateBlueCard
    val secondaryColor = if (isDarkMode) DarkCard2 else LightBlueCard
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
        LazyColumn{
            item {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(7.dp, 0.dp, 7.dp, 0.dp)) {
                    Column(modifier = Modifier.weight(1f)) {
                        DashboardTallCard(
                            modifier = Modifier.fillMaxWidth(),
                            title = stringResource(R.string.notes),
                            image = R.drawable.notes,
                            backgroundColor = primaryColor
                        ) {
                            navController.navigate(Screen.NotesScreen.route)
                        }
//                        Spacer(Modifier.height(5.dp))
                        DashboardCard(
                            modifier = Modifier.fillMaxWidth(),
                            title = stringResource(R.string.grocery),
                            image = R.drawable.grocery,
                            backgroundColor = secondaryColor
                        ) {
                            navController.navigate(Screen.GroceryScreen.route)
                        }
                    }
//                    Spacer(Modifier.width(5.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        DashboardCard(
                            modifier = Modifier.fillMaxWidth(),
                            title = stringResource(R.string.tasks),
                            image = R.drawable.done,
                            backgroundColor = secondaryColor
                        ) {
                            navController.navigate(Screen.TasksScreen.route)
                        }
//                        Spacer(Modifier.height(16.dp))
                        DashboardTallCard(
                            modifier = Modifier.fillMaxWidth(),
                            title = stringResource(R.string.bookmarks),
                            image = R.drawable.bookmark,
                            backgroundColor = primaryColor
                        ) {
                            navController.navigate(Screen.BookmarksScreen.route)
                        }
                    }
                }
            }
            item {
                Row(modifier = Modifier.fillMaxWidth()
                    .padding(7.dp, 0.dp, 7.dp, 7.dp)) {
                    DashboardWideCard(
                        title = stringResource(R.string.calendar),
                        image = R.drawable.schedule,
                        backgroundColor = primaryColor
                    ){
                        navController.navigate(Screen.CalendarScreen.route)
                    }
                }
            }
            item { Spacer(Modifier.height(100.dp)) }
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