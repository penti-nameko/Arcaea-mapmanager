package com.example.arcaea_mapmanager.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "songs")
data class SongEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val constant: Double,    // 譜面定数
    val bpm: Int,
    val highScore: Int = 0,
    val pure: Int = 0,
    val far: Int = 0,
    val lost: Int = 0,
    val status: String = "未クリア" // 未クリア, 詰めている最中, クリア済み
)