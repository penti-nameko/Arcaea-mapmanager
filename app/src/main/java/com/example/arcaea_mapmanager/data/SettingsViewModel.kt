package com.example.arcaea_mapmanager.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.arcaea_mapmanager.data.SettingsDao
import com.example.arcaea_mapmanager.data.SettingsEntity
import com.example.arcaea_mapmanager.data.SortOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class SettingsViewModel(private val settingsDao: SettingsDao) : ViewModel() {

    val settings: Flow<SettingsEntity> = settingsDao.getSettings().map {
        it ?: SettingsEntity() // デフォルト設定を返す
    }

    init {
        // 初回起動時にデフォルト設定を挿入
        viewModelScope.launch {
            settingsDao.insertSettings(SettingsEntity())
        }
    }

    fun updateDarkMode(enabled: Boolean) {
        viewModelScope.launch {
            settingsDao.updateDarkMode(enabled)
        }
    }

    fun updateSortOrder(sortOrder: SortOrder) {
        viewModelScope.launch {
            settingsDao.updateSortOrder(sortOrder)
        }
    }

    fun updateShowInProgressOnly(showOnly: Boolean) {
        viewModelScope.launch {
            settingsDao.updateShowInProgressOnly(showOnly)
        }
    }

    fun updateAutoBackup(enabled: Boolean) {
        viewModelScope.launch {
            settingsDao.updateAutoBackup(enabled)
        }
    }

    fun updateLastBackupDate(date: Long) {
        viewModelScope.launch {
            settingsDao.updateLastBackupDate(date)
        }
    }
}