package com.example.arcaea_mapmanager.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.arcaea_mapmanager.data.SongEntity

@Composable
fun SongListScreen(viewModel: SongViewModel) {
    val songList by viewModel.allSongs.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            SmallTopAppBar(title = { Text("Arcaea 譜面管理") })
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            // 簡易的な入力欄（本来は別画面やダイアログが望ましい）
            Button(
                onClick = { viewModel.addSong("Test Song", 10.7, 180, "未クリア") },
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            ) {
                Text("テスト曲を追加")
            }

            LazyColumn {
                items(songList) { song ->
                    SongItem(song)
                }
            }
        }
    }
}

@Composable
fun SongItem(song: SongEntity) {
    Card(modifier = Modifier.padding(8.dp).fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = song.title, style = MaterialTheme.typography.headlineSmall)
            Text(text = "定数: ${song.constant} / BPM: ${song.bpm}")
            Text(text = "Status: ${song.status}", color = MaterialTheme.colorScheme.primary)
        }
    }
}