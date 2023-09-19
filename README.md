# ✈️ Airport Information App

## 📖 Overview

The Airport Information App is a handy tool for retrieving information about airports. It allows users to search for airports by IATA code or name and provides details about the selected airport. This Android app is built using modern Android app development practices, including the MVVM architecture, Room database, and Jetpack Compose UI framework.

## 🧱 MVVM Architecture

The app follows the Model-View-ViewModel (MVVM) architecture:

### `AirportDao`

```kotlin
@Dao
interface AirportDao {
    // Database queries...
}
```
The AirportDao interface defines the database operations for retrieving airport information.

### `AirportDatabase`

```kotlin
@Database(entities = [AirportList::class, FavoriteList::class], version = 1)
abstract class AirportDatabase : RoomDatabase() {
    // Database configuration...
}
```
The AirportDatabase class sets up the Room database and provides access to the DAO.

### `AirportRepository`

```kotlin
class AirportRepository(private val airportDao: AirportDao) {
    // Repository methods...
}
```
The AirportRepository class acts as an intermediary between the ViewModel and the DAO, providing data retrieval methods.

### `UI Components`

The user interface is built using Jetpack Compose and includes components like SearchBar, AirportList, and AirportListText.

### `AirportViewModel`

```kotlin
class AirportViewModel(private val airportDao: AirportDao) : ViewModel() {
    // ViewModel code...
}
```
The AirportViewModel class manages UI data, communicates with the repository, and handles airport searches.

## 🚀 Getting Started
To run the app locally, follow these steps:

1. Clone this repository.
2. Open the project in Android Studio.
3. Build and run the app on an Android emulator or device.
