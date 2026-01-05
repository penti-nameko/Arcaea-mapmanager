package com.example.arcaea_mapmanager.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.arcaea_mapmanager.data.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class SongViewModel(private val songDao: SongDao) : ViewModel() {

    val allSongs: Flow<List<SongEntity>> = songDao.getAllSongs()
    val inProgressSongs: Flow<List<SongEntity>> = songDao.getInProgressSongs()

    fun addSong(
        title: String,
        difficulty: String,
        constant: Double,
        bpm: Int
    ) {
        viewModelScope.launch {
            val song = SongEntity(
                title = title,
                difficulty = difficulty,
                constant = constant,
                bpm = bpm
            )
            songDao.insert(song)
        }
    }

    fun updateScore(
        song: SongEntity,
        score: Int,
        pure: Int,
        far: Int,
        lost: Int
    ) {
        viewModelScope.launch {
            val updatedSong = song.copy(
                highScore = score,
                pure = pure,
                far = far,
                lost = lost,
                grade = calculateGrade(score)
            )
            songDao.update(updatedSong)
        }
    }

    fun updateStatus(song: SongEntity, status: ClearStatus) {
        viewModelScope.launch {
            songDao.update(song.copy(status = status))
        }
    }

    fun toggleInProgress(song: SongEntity) {
        viewModelScope.launch {
            songDao.update(song.copy(isInProgress = !song.isInProgress))
        }
    }

    fun updateMemo(song: SongEntity, memo: String) {
        viewModelScope.launch {
            songDao.update(song.copy(memo = memo))
        }
    }

    fun deleteSong(song: SongEntity) {
        viewModelScope.launch {
            songDao.delete(song)
        }
    }

    private fun calculateGrade(score: Int): Grade {
        return when {
            score >= 9900000 -> Grade.EX_PLUS
            score >= 9800000 -> Grade.EX
            score >= 9500000 -> Grade.AA
            score >= 9200000 -> Grade.A
            score >= 8900000 -> Grade.B
            score >= 8600000 -> Grade.C
            else -> Grade.D
        }
    }
}