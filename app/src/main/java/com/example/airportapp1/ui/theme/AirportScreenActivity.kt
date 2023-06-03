package com.example.airportapp1.ui.theme

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.airportapp1.ui.theme.screens.AirportMainScreen
import com.example.airportapp1.ui.theme.screens.AirportViewModel


class AirportScreenActivity : AppCompatActivity() {
    private val viewModel: AirportViewModel by viewModels { AirportViewModel.factory}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AirportMainScreen(airportViewModel = viewModel)
        }
    }
}