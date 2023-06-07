package com.example.airportapp1.ui.theme.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.airportapp1.data.AirportList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AirportDetailsScreen(
    selectedAirport: AirportList,
    randomAirports: List<AirportList>,
    airportViewModel: AirportViewModel,
    navController: NavHostController
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Airport Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            Modifier.padding(innerPadding)
        ) {
            DestinationAndArrivalList(
                navController = navController,
                selectedAirport = selectedAirport,
                randomAirports = randomAirports,
                airportViewModel = airportViewModel
            )
        }
    }
}
