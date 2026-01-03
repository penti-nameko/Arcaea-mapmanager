package com.example.arcaea_mapmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.arcaea_mapmanager.data.AppDatabase
import com.example.arcaea_mapmanager.ui.SongListScreen
import com.example.arcaea_mapmanager.ui.SongViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = AppDatabase.getDatabase(applicationContext)
        val viewModel = SongViewModel(database.songDao())

        setContent {
            MaterialTheme {
                Surface {
                    SongListScreen(viewModel = viewModel)
                }
            }
        }
    }
}