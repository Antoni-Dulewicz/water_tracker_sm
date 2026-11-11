package com.example.watertracker.data.model

import java.util.UUID

data class WaterEntry(
    val id: String = UUID.randomUUID().toString(),
    val amount: Int,
    val timestamp: Long = System.currentTimeMillis()
)