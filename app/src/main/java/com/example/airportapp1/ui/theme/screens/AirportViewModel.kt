package com.example.airportapp1.ui.theme.screens

import android.util.Log
import androidx.compose.ui.text.toUpperCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.airportapp1.data.AirportDao
import com.example.airportapp1.data.AirportList
import com.example.airportapp1.ui.theme.AirportApp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.async
import java.util.Locale

class AirportViewModel(private val airportDao: AirportDao) : ViewModel() {

    private val _searchResult = MutableStateFlow<List<AirportList>>(emptyList())
    val searchResult: StateFlow<List<AirportList>> = _searchResult

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



    private suspend fun getAirportByIataCode(iataCode: String): Flow<List<AirportList>> {
        val result = airportDao.getAirportByIataCode(iataCode = iataCode.uppercase(Locale.getDefault()))
        result.collect { airports ->
            Log.d("AirportViewModel", "getAirportByIataCode: $airports")
            _searchResult.value = airports
        }
        return result
    }

    private suspend fun getAirportByName(name: String): Flow<List<AirportList>> {
        val result = airportDao.getAirportByName(name)
        result.collect { airports ->
            Log.d("AirportViewModel", "getAirportByName: $airports")
            _searchResult.value = airports
        }
        return result
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