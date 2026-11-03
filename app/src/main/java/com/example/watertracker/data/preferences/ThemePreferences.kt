package com.example.watertracker.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(
    name = "user_settings"
)

private val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")

val Context.darkModeFlow: Flow<Boolean>
    get() = dataStore.data.map { preferences ->
        preferences[DARK_MODE_KEY] ?: false
    }

suspend fun Context.setDarkMode(enabled: Boolean) {
    dataStore.edit { preferences ->
        preferences[DARK_MODE_KEY] = enabled
    }
}
