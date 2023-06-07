package com.example.airportapp1.ui.theme.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.airportapp1.data.AirportDao
import com.example.airportapp1.data.AirportList
import com.example.airportapp1.data.Destination
import com.example.airportapp1.ui.theme.AirportApp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale

class AirportViewModel(private val airportDao: AirportDao) : ViewModel() {

    private val _searchResult = MutableStateFlow<List<AirportList>>(emptyList())
    val searchResult: StateFlow<List<AirportList>> = _searchResult

    private val _selectedAirport = MutableStateFlow<AirportList?>(null)
    val selectedAirport: StateFlow<AirportList?> = _selectedAirport

    private val _destination = MutableStateFlow(Destination.SearchScreen)
    val destination: StateFlow<Destination> = _destination.asStateFlow()

    private val _randomAirports = MutableStateFlow<List<AirportList>>(emptyList())
    val randomAirports: StateFlow<List<AirportList>> = _randomAirports.asStateFlow()

    init {
        getRandomAirports()
    }

    fun searchAirports(query: String) {
        viewModelScope.launch {
            val result = if (query.isNotBlank()) {
                val nameResultDeferred = async { getAirportByName(query).first() }
                val iataCodeResultDeferred = async { getAirportByIataCode(query).first() }

                val nameResult = nameResultDeferred.await()
                val iataCodeResult = iataCodeResultDeferred.await()

                val combinedResult = nameResult + iataCodeResult
                _searchResult.value = combinedResult

                combinedResult
            } else {
                emptyList()
            }
            _searchResult.value = result
        }
    }

    fun selectAirport(airport: AirportList?) {
        _selectedAirport.value = airport
        navigateTo(Destination.RouteScreen)
    }

    private suspend fun getAirportByIataCode(iataCode: String): Flow<List<AirportList>> {
        val result = airportDao.getAirportByIataCode(iataCode = iataCode.uppercase(Locale.getDefault()))
        _searchResult.value = result.first()
        return result
    }

    private suspend fun getAirportByName(name: String): Flow<List<AirportList>> {
        val result = airportDao.getAirportByName(name)
        _searchResult.value = result.first()
        return result
    }

    private fun getRandomAirports() {
        viewModelScope.launch {
            val airports = airportDao.getRandomAirports()
            _randomAirports.value = airports.first()
        }
    }

    fun navigateTo(destination: Destination) {
        _destination.value = destination
    }

    companion object {
        val factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as AirportApp)
                AirportViewModel(application.database.airportDao())
            }
        }
    }
}
