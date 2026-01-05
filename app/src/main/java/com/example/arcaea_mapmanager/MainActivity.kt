package com.example.arcaea_mapmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.example.arcaea_mapmanager.data.AppDatabase
import com.example.arcaea_mapmanager.ui.SettingsViewModel
import com.example.arcaea_mapmanager.ui.SongListScreen
import com.example.arcaea_mapmanager.ui.SongViewModel
import com.example.arcaea_mapmanager.ui.SplashScreenAdvanced
import com.example.arcaea_mapmanager.ui.theme.ArcaeaMapManagerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = AppDatabase.getDatabase(applicationContext)
        val songViewModel = SongViewModel(database.songDao())
        val settingsViewModel = SettingsViewModel(database.settingsDao())

        setContent {
            val settings by settingsViewModel.settings.collectAsState(
                initial = com.example.arcaea_mapmanager.data.SettingsEntity()
            )
            var showSplash by remember { mutableStateOf(true) }

            ArcaeaMapManagerTheme(darkTheme = settings.isDarkMode) {
                if (showSplash) {
                    SplashScreenAdvanced(
                        onTimeout = { showSplash = false }
                    )
                } else {
                    SongListScreen(
                        viewModel = songViewModel,
                        settingsViewModel = settingsViewModel
                    )
                }
            }
        }
    }
}