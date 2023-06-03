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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.airportapp1.ui.theme.AirportApp1Theme
import com.example.airportapp1.ui.theme.screens.AirportMainScreen
import com.example.airportapp1.ui.theme.screens.AirportViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val viewModel: AirportViewModel by viewModels { AirportViewModel.factory}
        super.onCreate(savedInstanceState)
        setContent {
            AirportApp1Theme {
                AirportMainScreen(airportViewModel = viewModel)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AirportApp1Theme {
    }
}