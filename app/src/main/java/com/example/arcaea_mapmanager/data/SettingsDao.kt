package com.example.arcaea_mapmanager.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SettingsDao {
    @Query("SELECT * FROM settings WHERE id = 1")
    fun getSettings(): Flow<SettingsEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSettings(settings: SettingsEntity)

    @Update
    suspend fun updateSettings(settings: SettingsEntity)

    @Query("UPDATE settings SET isDarkMode = :isDarkMode WHERE id = 1")
    suspend fun updateDarkMode(isDarkMode: Boolean)

    @Query("UPDATE settings SET sortOrder = :sortOrder WHERE id = 1")
    suspend fun updateSortOrder(sortOrder: SortOrder)

    @Query("UPDATE settings SET showInProgressOnly = :showOnly WHERE id = 1")
    suspend fun updateShowInProgressOnly(showOnly: Boolean)

    @Query("UPDATE settings SET autoBackup = :enabled WHERE id = 1")
    suspend fun updateAutoBackup(enabled: Boolean)

    @Query("UPDATE settings SET lastBackupDate = :date WHERE id = 1")
    suspend fun updateLastBackupDate(date: Long)
}