import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "songs")
data class SongEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val constant: Double, // 譜面定数
    val bpm: Int,
    val highScore: Int,
    val pure: Int,
    val far: Int,
    val lost: Int,
    val status: String // "未クリア", "クリア済み", "詰めている最中"
)