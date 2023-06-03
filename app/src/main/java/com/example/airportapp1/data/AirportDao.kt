package com.example.airportapp1.data

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AirportDao {
    @Query("SELECT * FROM airport WHERE iata_code = :iataCode")
    fun getAirportByIataCode(iataCode: String): Flow<List<AirportList>>

    @Query("SELECT * FROM airport WHERE name LIKE '%' || :name || '%'")
    fun getAirportByName(name: String): Flow<List<AirportList>>


}

