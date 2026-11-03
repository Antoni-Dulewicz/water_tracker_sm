package com.example.watertracker.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.watertracker.ui.components.*

@Composable
fun HistoryScreen() {
    val values = listOf(1400, 1800, 2100, 1600, 2300, 1900, 1250)
    val days = listOf("Pn", "Wt", "Śr", "Cz", "Pt", "Sb", "Nd")
    val average = values.average().toInt()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        ScreenTitle(
            title = "Historia",
            subtitle = "Sprawdź swoje postępy"
        )

        WaterCard {
            Text(
                "Średnie spożycie",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(Modifier.height(6.dp))

            Text(
                "$average ml",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.headlineMedium
            )

            Text(
                "Średnio dziennie w tym tygodniu",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodySmall
            )
        }

        WaterCard {
            Text(
                "Ostatnie 7 dni",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(Modifier.height(24.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom
            ) {
                values.forEachIndexed { index, value ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Box(
                            modifier = Modifier
                                .width(24.dp)
                                .height((value / 14f).dp)
                                .background(
                                    if (index == 6) MaterialTheme.colorScheme.primary
                                    else MaterialTheme.colorScheme.surfaceVariant,
                                    RoundedCornerShape(
                                        topStart = 8.dp,
                                        topEnd = 8.dp
                                    )
                                )
                        )

                        Spacer(Modifier.height(8.dp))

                        Text(
                            days[index],
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
        }

        WaterCard {
            Text(
                "Ostatnie dni",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(Modifier.height(12.dp))

            val recentDays = listOf(
                "Niedziela" to 1250,
                "Sobota" to 1900,
                "Piątek" to 2300,
                "Czwartek" to 1600
            )

            recentDays.forEach { (day, amount) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(day, color = MaterialTheme.colorScheme.onBackground)
                    Text(
                        "$amount ml",
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}