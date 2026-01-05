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
    val isInProgress: Boolean = false, // 詰めている最中
    val memo: String = ""
)

enum class ClearStatus {
    NOT_CLEARED,      // 未クリア
    TRACK_LOST,       // Track Lost
    CLEARED,          // クリア済み
    FULL_RECALL,      // Full Recall
    PURE_MEMORY,      // Pure Memory
    EASY_CLEAR,       // Easy Clear (Easyモード)
    HARD_CLEAR        // Hard Clear (Hardモード)
}

enum class Grade {
    EX_PLUS,  // EX+
    EX,       // EX
    AA,       // AA
    A,        // A
    B,        // B
    C,        // C
    D         // D
}

enum class Difficulty {
    PAST,
    PRESENT,
    FUTURE,
    BEYOND
}