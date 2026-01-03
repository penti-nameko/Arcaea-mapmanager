package com.example.arcaea_mapmanager.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "songs")
data class SongEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val constant: Double,
    val bpm: Int,
    val status: String
)