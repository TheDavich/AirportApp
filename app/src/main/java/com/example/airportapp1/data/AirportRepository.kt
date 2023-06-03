package com.example.airportapp1.data

import com.example.airportapp1.data.AirportDao
import com.example.airportapp1.data.AirportList
import kotlinx.coroutines.flow.Flow

class AirportRepository(private val airportDao: AirportDao) {
    fun getAirportByIataCode(iataCode: String): Flow<List<AirportList>> =
        airportDao.getAirportByIataCode(iataCode)

    fun getAirportByName(name: String): Flow<List<AirportList>> =
        airportDao.getAirportByName(name)
}