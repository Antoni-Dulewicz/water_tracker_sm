
package com.example.watertracker.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.Undo
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.watertracker.ui.components.*
import com.example.watertracker.viewmodel.WaterViewModel

@Composable
fun TodayScreen(
    viewModel: WaterViewModel = viewModel()
) {
    val todayEntries by viewModel.todayEntries.collectAsState()
    val dailyGoal by viewModel.dailyGoal.collectAsState()

    val consumed = todayEntries.sumOf { it.amount }

    val progress = (consumed.toFloat() / dailyGoal)
        .coerceIn(0f, 1f)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        ScreenTitle(
            title = "Dzisiaj",
            subtitle = "Zadbaj o odpowiednie nawodnienie"
        )

        WaterCard {
            Text(
                "Twoje dzisiejsze spożycie",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(190.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.size(165.dp),
                    strokeWidth = 13.dp,
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Default.WaterDrop,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(28.dp)
                    )

                    Text(
                        "$consumed ml",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Text(
                        "z $dailyGoal ml",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Text(
                "Pozostało ${maxOf(dailyGoal - consumed, 0)} ml do celu",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                "Dodaj wodę",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                listOf(150, 250, 500).forEach { amount ->
                    Button(
                        onClick = {
                            viewModel.addWater(amount)
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(14.dp),
                        contentPadding = PaddingValues(vertical = 14.dp)
                    ) {
                        Text("+${amount} ml")
                    }
                }
            }

            OutlinedButton(
                onClick = {
                    viewModel.undoLastEntry()
                },
                enabled = todayEntries.isNotEmpty(),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp)
            ) {
                Icon(
                    Icons.Default.Undo,
                    contentDescription = null
                )

                Spacer(Modifier.width(8.dp))

                Text("Cofnij ostatni wpis")
            }
        }

        WaterCard {
            Text(
                "Dzisiejsze podsumowanie",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Realizacja celu",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Text(
                    "${(progress * 100).toInt()}%",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Spacer(Modifier.height(8.dp))

            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )
        }
    }
}
