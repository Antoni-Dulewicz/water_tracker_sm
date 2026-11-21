package com.example.watertracker.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.watertracker.data.database.WaterDatabase
import com.example.watertracker.data.preferences.WaterPreferences
import com.example.watertracker.data.repository.WaterRepository

class WaterViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(
        modelClass: Class<T>
    ): T {
        if (modelClass.isAssignableFrom(WaterViewModel::class.java)) {

            val database = WaterDatabase.getInstance(context)
            val repository = WaterRepository(database.waterDao())
            val preferences = WaterPreferences(context)

            @Suppress("UNCHECKED_CAST")
            return WaterViewModel(repository, preferences) as T
        }

        throw IllegalArgumentException(
            "Unknown ViewModel class: ${modelClass.name}"
        )
    }
}