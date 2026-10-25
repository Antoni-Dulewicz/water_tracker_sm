package com.example.watertracker.navigation

sealed class Screen(val route: String) {
    data object Today: Screen("today")
    data object History: Screen("history")
    data object Settings: Screen("settings")
}