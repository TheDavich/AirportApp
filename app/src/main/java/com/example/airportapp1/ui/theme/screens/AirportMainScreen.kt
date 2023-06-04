package com.example.airportapp1.ui.theme.screens

import android.graphics.drawable.Icon
import android.widget.Space
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.airportapp1.data.AirportDatabase
import com.example.airportapp1.data.AirportList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AirportMainScreen(
    airportViewModel: AirportViewModel,
    searchResult: List<AirportList>
) {
    SearchBar(airportViewModel = airportViewModel)
    AirportList(searchResult = searchResult)
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
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = searchQuery.value,
            onValueChange = {
                searchQuery.value = it
                airportViewModel.searchAirports(it) // we call the search function after every value change
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
                unfocusedIndicatorColor = Color.Transparent, // Set the unfocused indicator color to Transparent
                focusedIndicatorColor = Color.Transparent // Set the focused indicator color to Transparent
            ),
        )
        Spacer(modifier = Modifier.height(16.dp))
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
fun AirportList(searchResult: List<AirportList>) {
    LazyColumn {
        items(searchResult) { airport ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ClickableText(
                    text = airport.iataCode,
                    onClick = { /* Обработчик клика */ },
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp)
                        .align(Alignment.CenterVertically)
                )
                Spacer(modifier = Modifier.height(36.dp))

                ClickableText(
                    text = airport.name,
                    onClick = { /* Обработчик клика */ },
                    modifier = Modifier
                        .weight(2f)
                        .padding(16.dp)
                )
            }
        }
    }
}





@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
fun AirportMainScreenPreview() {
    val context = LocalContext.current
    val airportDao = AirportDatabase.getDatabase(context).airportDao()
    val airportViewModel = AirportViewModel(airportDao = airportDao)
    val searchResult = airportViewModel.searchResult.collectAsState()
    AirportMainScreen(airportViewModel = airportViewModel, searchResult = searchResult.value)
}

