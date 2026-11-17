package com.example.watertracker.data.repository

import com.example.watertracker.data.dao.WaterDao
import com.example.watertracker.data.model.WaterEntry
import kotlinx.coroutines.flow.Flow

class WaterRepository(
    private val dao: WaterDao
) {

    val entries: Flow<List<WaterEntry>> =
        dao.observeAll()

    suspend fun addWater(amount: Int) {
        if (amount <= 0) return

        dao.insert(
            WaterEntry(amount = amount)
        )
    }

    suspend fun deleteEntry(entry: WaterEntry) {
        dao.delete(entry)
    }

    suspend fun getLastEntryOfDay(
        startOfDay: Long,
        endOfDay: Long
    ): WaterEntry? {
        return dao.getLastEntryOfDay(
            startOfDay,
            endOfDay
        )
    }
}