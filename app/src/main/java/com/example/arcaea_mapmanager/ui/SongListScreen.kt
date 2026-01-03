import androidx.compose.runtime.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*

@Composable
fun SongListScreen(viewModel: SongViewModel) {
    val songList by viewModel.allSongs.collectAsState(initial = emptyList())

    Scaffold(
        topBar = { TopAppBar(title = { Text("譜面管理ノート") }) }
    ) { padding ->
        LazyColumn(contentPadding = padding) {
            items(songList) { song ->
                Card(modifier = Modifier.padding(8.dp).fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = song.title, style = MaterialTheme.typography.headlineSmall)
                        Text(text = "定数: ${song.constant} | BPM: ${song.bpm}")
                        Text(text = "状態: ${song.status}", color = MaterialTheme.colorScheme.primary)
                        Text(text = "Score: ${song.highScore} (P:${song.pure} / F:${song.far} / L:${song.lost})")
                    }
                }
            }
        }
    }
}