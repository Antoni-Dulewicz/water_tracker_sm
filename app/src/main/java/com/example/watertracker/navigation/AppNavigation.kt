package pl.example.watertracker.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.watertracker.navigation.Screen
import com.example.watertracker.ui.screens.HistoryScreen
import com.example.watertracker.ui.screens.SettingsScreen
import com.example.watertracker.ui.screens.TodayScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val backStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry.value?.destination?.route

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = currentRoute == Screen.Today.route,
                    onClick = {
                        navController.navigate(Screen.Today.route) {
                            popUpTo(Screen.Today.route) {
                                inclusive = false
                            }
                            launchSingleTop = true
                        }
                    },
                    icon = {
                        Icon(Icons.Filled.Home, contentDescription = null)
                    },
                    label = { Text("Dzisiaj") }
                )

                NavigationBarItem(
                    selected = currentRoute == Screen.History.route,
                    onClick = {
                        navController.navigate(Screen.History.route) {
                            launchSingleTop = true
                        }
                    },
                    icon = {
                        Icon(
                            Icons.Filled.CalendarMonth,
                            contentDescription = null
                        )
                    },
                    label = { Text("Historia") }
                )

                NavigationBarItem(
                    selected = currentRoute == Screen.Settings.route,
                    onClick = {
                        navController.navigate(Screen.Settings.route) {
                            launchSingleTop = true
                        }
                    },
                    icon = {
                        Icon(
                            Icons.Filled.Settings,
                            contentDescription = null
                        )
                    },
                    label = { Text("Ustawienia") }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Today.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Today.route) {
                TodayScreen()
            }

            composable(Screen.History.route) {
                HistoryScreen()
            }

            composable(Screen.Settings.route) {
                SettingsScreen()
            }
        }
    }
}