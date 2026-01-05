package com.example.arcaea_mapmanager.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "songs")
data class SongEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val difficulty: String, // PST, PRS, FTR, BYD
    val constant: Double,
    val bpm: Int,
    val highScore: Int = 0,
    val pure: Int = 0,
    val far: Int = 0,
    val lost: Int = 0,
    val status: ClearStatus = ClearStatus.NOT_CLEARED,
    val grade: Grade = Grade.D,
    val isInProgress: Boolean = false,
    val memo: String = "",
    val dateAdded: Long = System.currentTimeMillis() // 追加日時
)

enum class ClearStatus {
    NOT_CLEARED,
    TRACK_LOST,
    CLEARED,
    FULL_RECALL,
    PURE_MEMORY,
    EASY_CLEAR,
    HARD_CLEAR
}

enum class Grade {
    EX_PLUS,
    EX,
    AA,
    A,
    B,
    C,
    D
}

enum class Difficulty {
    PAST,
    PRESENT,
    FUTURE,
    BEYOND
}