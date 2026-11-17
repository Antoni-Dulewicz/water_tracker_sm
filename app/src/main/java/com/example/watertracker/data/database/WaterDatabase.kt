package com.example.watertracker.data.database

import androidx.room3.RoomDatabase
import androidx.room3.Database
import androidx.room3.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import android.content.Context
import com.example.watertracker.data.dao.WaterDao
import com.example.watertracker.data.model.WaterEntry

@Database(
    entities = [WaterEntry::class],
    version = 1
)
abstract class WaterDatabase : RoomDatabase() {

    abstract fun waterDao(): WaterDao

    companion object {
        @Volatile
        private var INSTANCE: WaterDatabase? = null

        fun getInstance(context: Context): WaterDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room
                    .databaseBuilder<WaterDatabase>(
                        context.applicationContext,
                        "water_tracker.db"
                    )
                    .setDriver(BundledSQLiteDriver())
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}