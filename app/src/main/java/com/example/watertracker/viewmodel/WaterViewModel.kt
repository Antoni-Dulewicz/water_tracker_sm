package com.example.watertracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.watertracker.data.repository.WaterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar

class WaterViewModel(
    private val repository: WaterRepository
) : ViewModel() {

    private val entries = repository.entries

    private val _dailyGoal = MutableStateFlow(2000)
    val dailyGoal = _dailyGoal

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

    val todayEntries = entries
        .map { allEntries ->
            allEntries.filter { isToday(it.timestamp) }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    fun addWater(amount: Int) {
        viewModelScope.launch {
            repository.addWater(amount)
        }
    }

    fun undoLastEntry() {
        viewModelScope.launch {

            val calendar = Calendar.getInstance()

            calendar.set(
                Calendar.HOUR_OF_DAY,
                0
            )
            calendar.set(
                Calendar.MINUTE,
                0
            )
            calendar.set(
                Calendar.SECOND,
                0
            )
            calendar.set(
                Calendar.MILLISECOND,
                0
            )

            val startOfDay = calendar.timeInMillis

            calendar.add(Calendar.DAY_OF_YEAR, 1)

            val endOfDay = calendar.timeInMillis

            val lastEntry =
                repository.getLastEntryOfDay(
                    startOfDay,
                    endOfDay
                )

            if (lastEntry != null) {
                repository.deleteEntry(lastEntry)
            }
        }
    }

    val last7Days = entries
        .map { allEntries ->
            val calendar = Calendar.getInstance()

            (6 downTo 0).map { daysAgo ->
                calendar.timeInMillis = System.currentTimeMillis()

                calendar.add(
                    Calendar.DAY_OF_YEAR,
                    -daysAgo
                )

                val year = calendar.get(Calendar.YEAR)
                val dayOfYear = calendar.get(Calendar.DAY_OF_YEAR)

                val total = allEntries
                    .filter { entry ->
                        val entryCalendar = Calendar.getInstance().apply {
                            timeInMillis = entry.timestamp
                        }

                        entryCalendar.get(Calendar.YEAR) == year &&
                                entryCalendar.get(Calendar.DAY_OF_YEAR) == dayOfYear
                    }
                    .sumOf { it.amount }

                total
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            List(7) { 0 }
        )

    fun updateDailyGoal(goal: Int) {
        if (goal in 500..5000) {
            _dailyGoal.value = goal
        }
    }
}