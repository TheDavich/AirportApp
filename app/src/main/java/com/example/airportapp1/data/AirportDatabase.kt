package com.example.airportapp1.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [AirportList::class], version = 1)
abstract class AirportDatabase: RoomDatabase() {
    abstract fun airportDao(): AirportDao

    companion object {
        @Volatile
        private var INSTANCE: AirportDatabase? = null

        fun getDatabase(context: Context): AirportDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    AirportDatabase::class.java,
                    "app_database"
                )
                    .createFromAsset("airport.db")
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    .fallbackToDestructiveMigration()
                    .build()
                    .also {
                        INSTANCE = it
                    }
            }
        }
    }
}