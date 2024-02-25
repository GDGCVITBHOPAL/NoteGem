package com.gdsc_vitbhopal.notegem.presentation.main.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.gdsc_vitbhopal.notegem.presentation.util.BottomNavItem
import com.gdsc_vitbhopal.notegem.ui.theme.LightBlueCard

@Composable
fun MainBottomBar(
    navController: NavHostController,
    items: List<BottomNavItem>,
) {
    BottomNavigation (backgroundColor = MaterialTheme.colors.background) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        items.forEach {
            BottomNavigationItem(
                icon = { Icon(
                    if (currentDestination?.route == it.route)
                        painterResource(it.iconSelected)
                    else
                        painterResource(it.icon)
                    ,
                    contentDescription = stringResource(it.title),
                ) },
                label = {
                    Text(stringResource(it.title), style = MaterialTheme.typography.body2)
                },
                selected = currentDestination?.route == it.route,
                onClick = {
                    navController.navigate(it.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                alwaysShowLabel = false
            )
        }
    }
}