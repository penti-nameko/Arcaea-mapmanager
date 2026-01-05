package com.example.arcaea_mapmanager.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings")
data class SettingsEntity(
    @PrimaryKey
    val id: Int = 1, // 常に1つのレコードのみ
    val isDarkMode: Boolean = false,
    val sortOrder: SortOrder = SortOrder.CONSTANT_DESC,
    val showInProgressOnly: Boolean = false,
    val autoBackup: Boolean = false,
    val lastBackupDate: Long = 0L
)

enum class SortOrder {
    CONSTANT_DESC,      // 定数順（降順）
    CONSTANT_ASC,       // 定数順（昇順）
    TITLE_ASC,          // 曲名順（昇順）
    TITLE_DESC,         // 曲名順（降順）
    DATE_ADDED_DESC,    // 追加日順（新しい順）
    DATE_ADDED_ASC,     // 追加日順（古い順）
    STATUS_DESC         // クリア状況順
}