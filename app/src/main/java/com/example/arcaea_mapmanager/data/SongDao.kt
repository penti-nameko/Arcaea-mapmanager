package com.example.arcaea_mapmanager.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SongDao {
    @Query("SELECT * FROM songs ORDER BY constant DESC")
    fun getAllSongs(): Flow<List<SongEntity>>

    @Query("SELECT * FROM songs WHERE isInProgress = 1")
    fun getInProgressSongs(): Flow<List<SongEntity>>

    @Query("SELECT * FROM songs WHERE status = :status")
    fun getSongsByStatus(status: ClearStatus): Flow<List<SongEntity>>

    @Query("SELECT * FROM songs WHERE difficulty = :difficulty ORDER BY constant DESC")
    fun getSongsByDifficulty(difficulty: String): Flow<List<SongEntity>>

    @Insert
    suspend fun insert(song: SongEntity)

    @Update
    suspend fun update(song: SongEntity)

    @Delete
    suspend fun delete(song: SongEntity)

    @Query("DELETE FROM songs")
    suspend fun deleteAll()
}