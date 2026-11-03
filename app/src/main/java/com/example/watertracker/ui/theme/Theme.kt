package com.example.watertracker.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Color(0xFF1976D2),
    onPrimary = Color.White,

    secondary = Color(0xFF42A5F5),
    onSecondary = Color.White,

    background = Color(0xFFF5F8FC),
    onBackground = Color(0xFF263238),

    surface = Color.White,
    onSurface = Color(0xFF263238),

    surfaceVariant = Color(0xFFE3F2FD),
    onSurfaceVariant = Color(0xFF546E7A)
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF90CAF9),
    onPrimary = Color(0xFF003258),

    secondary = Color(0xFF64B5F6),
    onSecondary = Color(0xFF003258),

    background = Color(0xFF121820),
    onBackground = Color(0xFFE7EDF3),

    surface = Color(0xFF1E2935),
    onSurface = Color(0xFFE7EDF3),

    surfaceVariant = Color(0xFF304252),
    onSurfaceVariant = Color(0xFFB0BEC5)
)

@Composable
fun WaterTrackerTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColors
    } else {
        LightColors
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}
