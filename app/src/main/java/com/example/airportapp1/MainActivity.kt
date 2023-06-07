package com.example.airportapp1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.airportapp1.data.Destination
import com.example.airportapp1.ui.theme.AirportApp1Theme
import com.example.airportapp1.ui.theme.screens.AirportDetailsScreen
import com.example.airportapp1.ui.theme.screens.AirportMainScreen
import com.example.airportapp1.ui.theme.screens.AirportViewModel
import com.example.airportapp1.ui.theme.screens.DestinationAndArrivalList

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = setupViewModel()
        setContent {
            AirportApp1Theme {
                val randomAirports by viewModel.randomAirports.collectAsState(emptyList())
                val navController: NavHostController = rememberNavController()

                NavHost(navController, startDestination = Destination.SearchScreen.route) {
                    composable(Destination.SearchScreen.route) {
                        AirportMainScreen(
                            airportViewModel = viewModel,
                            navController = navController
                        )
                    }
                    composable(Destination.AirportDetailsScreen.route) {
                        val selectedAirport = viewModel.selectedAirport.value ?: return@composable
                        AirportDetailsScreen(
                            selectedAirport = selectedAirport,
                            randomAirports = randomAirports,
                            airportViewModel = viewModel,
                            navController = navController
                        )
                    }
                }
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