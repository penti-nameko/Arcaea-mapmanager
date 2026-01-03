package com.example.arcaea_mapmanager.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.arcaea_mapmanager.data.SongDao
import com.example.arcaea_mapmanager.data.SongEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class SongViewModel(private val dao: SongDao) : ViewModel() {
    val allSongs: Flow<List<SongEntity>> = dao.getAllSongs()

    fun addSong(title: String, constant: Double, bpm: Int, status: String) {
        viewModelScope.launch {
            val newSong = SongEntity(
                title = title,
                constant = constant,
                bpm = bpm,
                status = status
            )
            dao.insertSong(newSong)
        }
    }
}

// ViewModelにDaoを渡すためのFactory
class SongViewModelFactory(private val dao: SongDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SongViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SongViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}