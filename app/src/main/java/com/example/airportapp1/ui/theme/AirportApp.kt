package com.example.airportapp1.ui.theme

import android.app.Application
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.airportapp1.R
import com.example.airportapp1.data.AirportDatabase
import com.example.airportapp1.ui.theme.screens.AirportMainScreen
import com.example.airportapp1.ui.theme.screens.AirportViewModel

class AirportApp: Application() {
    val database by lazy { AirportDatabase.getDatabase(this) }
    override fun onCreate() {
        super.onCreate()
    }

}