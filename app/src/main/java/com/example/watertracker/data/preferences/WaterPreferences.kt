package com.example.watertracker.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.watertracker.data.model.GlassSizes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


private val Context.dataStore by preferencesDataStore(
    name = "water_preferences"
)

class WaterPreferences(
    private val context: Context
){
    private val smallGlassKey = intPreferencesKey("small_glass")
    private val mediumGlassKey = intPreferencesKey("medium_glass")
    private val largeGlassKey = intPreferencesKey("large_glass")
    private val dailyGoalKey = intPreferencesKey("daily_goal")

    val glassSizes: Flow<GlassSizes> =
        context.dataStore.data.map { preferences ->
            GlassSizes(
                small = preferences[smallGlassKey] ?: 150,
                medium = preferences[mediumGlassKey] ?: 250,
                large = preferences[largeGlassKey] ?: 500
            )
        }

    suspend fun saveGlassSizes(
        small: Int,
        medium: Int,
        large: Int
    ) {
        context.dataStore.edit { preferences ->
            preferences[smallGlassKey] = small
            preferences[mediumGlassKey] = medium
            preferences[largeGlassKey] = large
        }
    }

    val dailyGoal: Flow<Int> =
        context.dataStore.data.map { preferences ->
            preferences[dailyGoalKey] ?: 2000
        }

    suspend fun saveDailyGoal(goal: Int) {
        context.dataStore.edit { preferences ->
            preferences[dailyGoalKey] = goal
        }
    }
}