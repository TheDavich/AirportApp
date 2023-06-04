package com.example.airportapp1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.example.airportapp1.ui.theme.AirportApp
import com.example.airportapp1.ui.theme.AirportApp1Theme
import com.example.airportapp1.ui.theme.screens.AirportMainScreen
import com.example.airportapp1.ui.theme.screens.AirportViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = setupViewModel()
        setContent {
            AirportApp1Theme {
                val searchResult by viewModel.searchResult.collectAsState()
                AirportMainScreen(airportViewModel = viewModel, searchResult = searchResult)
            }
        }
    }

    private fun setupViewModel(): AirportViewModel {
        return ViewModelProvider(this, AirportViewModel.factory).get(AirportViewModel::class.java)
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AirportApp1Theme {
    }
}