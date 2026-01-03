package com.example.arcaea_mapmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.arcaea_mapmanager.data.AppDatabase
import com.example.arcaea_mapmanager.ui.SongListScreen
import com.example.arcaea_mapmanager.ui.SongViewModel
import com.example.arcaea_mapmanager.ui.SongViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = AppDatabase.getDatabase(applicationContext)
        val dao = database.songDao()

        setContent {
            val viewModel: SongViewModel = viewModel(
                factory = SongViewModelFactory(dao)
            )
            SongListScreen(viewModel = viewModel)
        }
    }
}