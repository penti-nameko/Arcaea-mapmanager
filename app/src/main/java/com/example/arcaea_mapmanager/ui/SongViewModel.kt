package com.example.arcaea_mapmanager.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.arcaea_mapmanager.data.SongDao
import com.example.arcaea_mapmanager.data.SongEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class SongViewModel(private val songDao: SongDao) : ViewModel() {

    val allSongs: Flow<List<SongEntity>> = songDao.getAllSongs()

    fun addSong(title: String, constant: Double, bpm: Int, status: String) {
        viewModelScope.launch {
            val song = SongEntity(
                title = title,
                constant = constant,
                bpm = bpm,
                status = status
            )
            songDao.insert(song)
        }
    }

    fun updateSong(song: SongEntity) {
        viewModelScope.launch {
            songDao.update(song)
        }
    }

    fun deleteSong(song: SongEntity) {
        viewModelScope.launch {
            songDao.delete(song)
        }
    }
}