package com.example.watertracker


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import com.example.watertracker.data.preferences.darkModeFlow
import com.example.watertracker.data.preferences.setDarkMode
import com.example.watertracker.navigation.AppNavigation
import com.example.watertracker.ui.theme.WaterTrackerTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            // Odczyt aktualnego ustawienia z DataStore
            val isDarkMode by applicationContext
                .darkModeFlow
                .collectAsState(initial = false)

            val coroutineScope = rememberCoroutineScope()

            // Cała aplikacja korzysta z jednego motywu
            WaterTrackerTheme(darkTheme = isDarkMode) {

                AppNavigation(
                    isDarkMode = isDarkMode,
                    onDarkModeChange = { enabled ->
                        coroutineScope.launch {
                            applicationContext.setDarkMode(enabled)
                        }
                    }
                )
            }
        }
    }
}
