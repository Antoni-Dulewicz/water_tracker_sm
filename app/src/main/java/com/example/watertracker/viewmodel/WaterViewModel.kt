package com.example.watertracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.watertracker.data.model.WaterEntry
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.util.Calendar

class WaterViewModel : ViewModel() {

    private val _entries =
        MutableStateFlow<List<WaterEntry>>(emptyList())

    private val _dailyGoal = MutableStateFlow(2000)

    val dailyGoal = _dailyGoal.asStateFlow()

    private fun isToday(timestamp: Long): Boolean {
        val entryCalendar = Calendar.getInstance().apply {
            timeInMillis = timestamp
        }

        val todayCalendar = Calendar.getInstance()

        return entryCalendar.get(Calendar.YEAR) ==
                todayCalendar.get(Calendar.YEAR) &&
                entryCalendar.get(Calendar.DAY_OF_YEAR) ==
                todayCalendar.get(Calendar.DAY_OF_YEAR)
    }

    val todayEntries = _entries
        .map { entries ->
            entries.filter { isToday(it.timestamp) }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    fun addWater(amount: Int) {
        if (amount <= 0) return

        val entry = WaterEntry(amount = amount)

        _entries.value += entry
    }

    fun undoLastEntry() {
        val lastEntry = _entries.value
            .lastOrNull { isToday(it.timestamp) }
            ?: return

        _entries.value = _entries.value.filterNot {
            it.id == lastEntry.id
        }
    }

    fun updateDailyGoal(goal: Int) {
        if (goal in 500..5000) {
            _dailyGoal.value = goal
        }
    }
}