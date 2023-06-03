package com.example.airportapp1.ui.theme

import android.app.Application
import com.example.airportapp1.data.AirportDatabase

class AirportApp: Application() {
    val database by lazy { AirportDatabase.getDatabase(this) }

    override fun onCreate() {
        super.onCreate()
    }

}