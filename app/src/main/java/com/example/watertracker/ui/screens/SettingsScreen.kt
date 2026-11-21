package com.example.watertracker.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.watertracker.ui.components.*
import com.example.watertracker.viewmodel.WaterViewModel

@Composable
fun SettingsScreen(
    viewModel: WaterViewModel,
    isDarkMode: Boolean,
    onDarkModeChange: (Boolean) -> Unit
) {
    val dailyGoal by viewModel.dailyGoal.collectAsState()
    var remindersEnabled by remember { mutableStateOf(true) }
    var darkModeEnabled by remember { mutableStateOf(false) }
    val glassSizes by viewModel.glassSizes.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        ScreenTitle(
            title = "Ustawienia",
            subtitle = "Dostosuj aplikację do swoich potrzeb"
        )

        WaterCard {
            Text(
                "Dzienny cel",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(Modifier.height(12.dp))

            Text(
                "${dailyGoal} ml",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Slider(
                value = dailyGoal.toFloat(),
                onValueChange = {
                    viewModel.updateDailyGoal(it.toInt())
                },
                valueRange = 500f..5000f,
                steps = 17
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("500 ml", color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("5000 ml", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }

        WaterCard {
            Text(
                "Rozmiary szklanek",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(Modifier.height(16.dp))

            GlassSizeField(
                name = "Mała szklanka",
                value = glassSizes.small,
                onValueChange = { value ->
                    viewModel.updateGlassSizes(
                        small = value,
                        medium = glassSizes.medium,
                        large = glassSizes.large
                    )
                }
            )

            Spacer(Modifier.height(12.dp))

            GlassSizeField(
                name = "Średnia szklanka",
                value = glassSizes.medium,
                onValueChange = { value ->
                    viewModel.updateGlassSizes(
                        small = glassSizes.small,
                        medium = value,
                        large = glassSizes.large
                    )
                }
            )

            Spacer(Modifier.height(12.dp))

            GlassSizeField(
                name = "Duża szklanka",
                value = glassSizes.large,
                onValueChange = { value ->
                    viewModel.updateGlassSizes(
                        small = glassSizes.small,
                        medium = glassSizes.medium,
                        large = value
                    )
                }
            )
        }

        WaterCard {
            Text(
                "Powiadomienia",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Przypomnienia o wodzie")
                    Text(
                        "Przypominaj mi o regularnym piciu",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Switch(
                    checked = remindersEnabled,
                    onCheckedChange = { remindersEnabled = it }
                )
            }
        }

        WaterCard {
            Text(
                "Wygląd",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        "Tryb ciemny",
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Text(
                        "Zmień wygląd aplikacji",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Switch(
                    checked = isDarkMode,
                    onCheckedChange = onDarkModeChange
                )
            }
        }

        WaterCard {
            Text(
                "O aplikacji",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(Modifier.height(8.dp))

            Text(
                "WaterTracker",
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                "Wersja 1.0.0",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
