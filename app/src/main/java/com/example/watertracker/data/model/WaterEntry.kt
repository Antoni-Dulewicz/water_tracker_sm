package com.example.watertracker.data.model

import androidx.room3.Entity
import androidx.room3.PrimaryKey
import java.util.UUID

@Entity(tableName = "water_entries")
data class WaterEntry(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val amount: Int,
    val timestamp: Long = System.currentTimeMillis()
)