package com.example.watertracker.data.dao

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Insert
import androidx.room3.Query
import com.example.watertracker.data.model.WaterEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface WaterDao {

    @Insert
    suspend fun insert(entry: WaterEntry)

    @Delete
    suspend fun delete(entry: WaterEntry)

    @Query("SELECT * FROM water_entries ORDER BY timestamp DESC")
    fun observeAll(): Flow<List<WaterEntry>>

    @Query("""
        SELECT * FROM water_entries
        WHERE timestamp >= :startOfDay
        AND timestamp < :endOfDay
        ORDER BY timestamp DESC
        LIMIT 1
    """)
    suspend fun getLastEntryOfDay(
        startOfDay: Long,
        endOfDay: Long
    ): WaterEntry?
}