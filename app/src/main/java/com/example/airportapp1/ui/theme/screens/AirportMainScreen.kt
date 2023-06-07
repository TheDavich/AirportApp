package com.example.airportapp1.ui.theme.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.airportapp1.data.AirportDatabase
import com.example.airportapp1.data.AirportList
import com.example.airportapp1.data.Destination




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AirportMainScreen(
    airportViewModel: AirportViewModel,
    navController: NavHostController
) {
    val searchResult by airportViewModel.searchResult.collectAsState()
    val selectedAirport by airportViewModel.selectedAirport.collectAsState()
    val randomAirports by airportViewModel.randomAirports.collectAsState(emptyList())

    LaunchedEffect(Unit) {
        airportViewModel.searchAirports("") // Обновляем список searchResult при возобновлении экрана
    }

    val currentRoute = navController.currentBackStackEntry?.destination?.route
    val isSearchBarVisible = remember(selectedAirport) { selectedAirport == null }

    BackHandler(enabled = selectedAirport != null || currentRoute == Destination.SearchScreen.route) {
        if (selectedAirport != null) {
            airportViewModel.selectAirport(null)
        } else {
            navController.navigate(Destination.SearchScreen.route)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Airport Search") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate(Destination.SearchScreen.route)
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            Modifier.padding(innerPadding)
        ) {
            if (isSearchBarVisible || currentRoute == Destination.SearchScreen.route) {
                SearchBar(airportViewModel = airportViewModel)
            }

            if (selectedAirport != null && currentRoute == Destination.AirportDetailsScreen.route) {
                AirportDetailsScreen(
                    airportViewModel = airportViewModel,
                    navController = navController,
                    randomAirports = randomAirports,
                    selectedAirport = selectedAirport!!
                )
            } else if (searchResult.isNotEmpty() || currentRoute == Destination.RouteScreen.route || currentRoute == Destination.SearchScreen.route) {
                AirportList(
                    searchResult = searchResult,
                    navController = navController,
                    onAirportSelected = { airport ->
                        airportViewModel.selectAirport(airport)
                        navController.navigate(Destination.AirportDetailsScreen.route)
                    }
                )
            }
        }
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    airportViewModel: AirportViewModel,
    modifier: Modifier = Modifier
) {
    val searchQuery = remember { mutableStateOf("") }
    val searchResult by airportViewModel.searchResult.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        TextField(
            value = searchQuery.value,
            onValueChange = {
                searchQuery.value = it
                airportViewModel.searchAirports(it)
            },
            label = { Text("Search airports") },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp)),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon",
                    modifier = Modifier.padding(8.dp)
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (searchResult.isEmpty() && searchQuery.value.isNotEmpty()) {
            Text(
                text = "No search results",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 16.dp, top = 8.dp)
            )
        }
    }
}



@Composable
fun ClickableText(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        modifier = modifier.clickable { onClick() }
    )
}



@Composable
fun AirportList(
    searchResult: List<AirportList>,
    navController: NavHostController,
    onAirportSelected: (AirportList) -> Unit
) {
    LazyColumn {
        items(searchResult) { airport ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ClickableText(
                    text = airport.iataCode,
                    onClick = {
                        onAirportSelected(airport)
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp)
                        .align(Alignment.CenterVertically)
                )
                Spacer(modifier = Modifier.height(36.dp))

                ClickableText(
                    text = airport.name,
                    onClick = {
                        onAirportSelected(airport)
                    },
                    modifier = Modifier
                        .weight(2f)
                        .padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun DestinationAndArrivalCard(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    departureAirport: AirportList,
    arrivalAirport: AirportList
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .background(Color(0xFFb1b4c4))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Departure:",
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 24.dp, top = 8.dp)
            )
            Text(
                text = departureAirport.iataCode,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.padding(start = 16.dp, top = 8.dp)
            )
            Text(
                text = departureAirport.name,
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 16.dp, top = 8.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Arrival:",
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 24.dp, top = 8.dp)
            )
            Text(
                text = arrivalAirport.iataCode,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier.padding(start = 16.dp, top = 8.dp)
            )
            Text(
                text = arrivalAirport.name,
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 16.dp, top = 8.dp)
            )
        }
    }
}



@Composable
fun DestinationAndArrivalList(
    navController: NavHostController,
    selectedAirport: AirportList?,
    randomAirports: List<AirportList>,
    airportViewModel: AirportViewModel,
) {
    LazyColumn {
        itemsIndexed(randomAirports) { index, airport ->
            DestinationAndArrivalCard(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                navController = navController,
                departureAirport = selectedAirport ?: airport,
                arrivalAirport = airport
            )
        }
    }
}







@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
fun AirportMainScreenPreview() {
    val context = LocalContext.current
    val airportDao = AirportDatabase.getDatabase(context).airportDao()
    val airportViewModel = AirportViewModel(airportDao = airportDao)
    AirportMainScreen(
        airportViewModel = airportViewModel,
        navController = rememberNavController()
    )
}

@Preview
@Composable
fun DestinationAndArrivalCardPreview() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val airportDao = AirportDatabase.getDatabase(context).airportDao()
    val viewModel = AirportViewModel(airportDao = airportDao)
    val selectedAirport = AirportList(
        id = 0,
        iataCode = "FRU",
        name = "Manas Airport",
        passengers = 200
    )
    DestinationAndArrivalCard(
        navController = navController,
        departureAirport = selectedAirport,
        arrivalAirport = AirportList(
            id = 1,
            iataCode = "ALA",
            name = "Almaty Airport",
            passengers = 200
        ))
}

