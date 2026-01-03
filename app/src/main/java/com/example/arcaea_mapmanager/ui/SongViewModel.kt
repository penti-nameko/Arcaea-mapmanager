import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SongViewModel(private val dao: SongDao) : ViewModel() {
    val allSongs = dao.getAllSongs()

    fun addSong(title: String, constant: Double, bpm: Int, status: String) {
        viewModelScope.launch {
            val newSong = SongEntity(
                title = title, constant = constant, bpm = bpm,
                highScore = 0, pure = 0, far = 0, lost = 0, status = status
            )
            dao.insertSong(newSong)
        }
    }
}