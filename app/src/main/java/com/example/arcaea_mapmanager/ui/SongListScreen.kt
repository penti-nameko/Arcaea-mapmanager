package com.example.arcaea_mapmanager.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.arcaea_mapmanager.data.SongEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongListScreen(viewModel: SongViewModel) {
    val songList by viewModel.allSongs.collectAsState(initial = emptyList())
    var showAddDialog by remember { mutableStateOf(false) }
    var showFilterMenu by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Arcaea 譜面管理") },
                actions = {
                    IconButton(onClick = { showFilterMenu = true }) {
                        Icon(Icons.Default.FilterList, "フィルター")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Add, "曲を追加")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            items(songList) { song ->
                SongItem(
                    song = song,
                    onEdit = { /* 編集ダイアログを開く */ },
                    viewModel = viewModel
                )
            }
        }
    }

    if (showAddDialog) {
        AddSongDialog(
            onDismiss = { showAddDialog = false },
            onConfirm = { title, difficulty, constant, bpm ->
                viewModel.addSong(title, difficulty, constant, bpm)
                showAddDialog = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongItem(
    song: SongEntity,
    onEdit: () -> Unit,
    viewModel: SongViewModel
) {
    var showScoreDialog by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
        onClick = { expanded = !expanded }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // タイトルと難易度
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = song.title,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = "${song.difficulty} ${song.constant}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = getDifficultyColor(song.difficulty)
                    )
                }

                // ステータスバッジ
                AssistChip(
                    onClick = { },
                    label = { Text(getStatusText(song.status)) },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = getStatusColor(song.status)
                    )
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 基本情報
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("BPM: ${song.bpm}")
                Text("Grade: ${song.grade}")
            }

            // スコア情報
            if (song.highScore > 0) {
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Score: ${formatScore(song.highScore)}")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("PURE: ${song.pure}")
                    Text("FAR: ${song.far}")
                    Text("LOST: ${song.lost}")
                }
            }

            // 詰めフラグ
            if (song.isInProgress) {
                Spacer(modifier = Modifier.height(8.dp))
                AssistChip(
                    onClick = { viewModel.toggleInProgress(song) },
                    label = { Text("詰めている") },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
            }

            // 展開時の詳細情報
            if (expanded) {
                Divider(modifier = Modifier.padding(vertical = 8.dp))

                // メモ
                if (song.memo.isNotEmpty()) {
                    Text(
                        text = "メモ: ${song.memo}",
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // アクションボタン
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { showScoreDialog = true },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("スコア更新")
                    }

                    OutlinedButton(
                        onClick = { viewModel.toggleInProgress(song) },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(if (song.isInProgress) "詰め解除" else "詰める")
                    }
                }
            }
        }
    }

    if (showScoreDialog) {
        ScoreUpdateDialog(
            song = song,
            onDismiss = { showScoreDialog = false },
            onConfirm = { score, pure, far, lost, status ->
                viewModel.updateScore(song, score, pure, far, lost)
                viewModel.updateStatus(song, status)
                showScoreDialog = false
            }
        )
    }
}

@Composable
fun getDifficultyColor(difficulty: String): androidx.compose.ui.graphics.Color {
    return when (difficulty) {
        "PAST" -> androidx.compose.ui.graphics.Color(0xFF4A90E2)
        "PRESENT" -> androidx.compose.ui.graphics.Color(0xFF7ED321)
        "FUTURE" -> androidx.compose.ui.graphics.Color(0xFFD0021B)
        "BEYOND" -> androidx.compose.ui.graphics.Color(0xFF8B572A)
        else -> MaterialTheme.colorScheme.onSurface
    }
}

@Composable
fun getStatusColor(status: com.example.arcaea_mapmanager.data.ClearStatus): androidx.compose.ui.graphics.Color {
    return when (status) {
        com.example.arcaea_mapmanager.data.ClearStatus.PURE_MEMORY -> androidx.compose.ui.graphics.Color(0xFFFFD700)
        com.example.arcaea_mapmanager.data.ClearStatus.FULL_RECALL -> androidx.compose.ui.graphics.Color(0xFFC0C0C0)
        com.example.arcaea_mapmanager.data.ClearStatus.CLEARED -> MaterialTheme.colorScheme.primaryContainer
        else -> MaterialTheme.colorScheme.surfaceVariant
    }
}

fun getStatusText(status: com.example.arcaea_mapmanager.data.ClearStatus): String {
    return when (status) {
        com.example.arcaea_mapmanager.data.ClearStatus.NOT_CLEARED -> "未クリア"
        com.example.arcaea_mapmanager.data.ClearStatus.TRACK_LOST -> "Track Lost"
        com.example.arcaea_mapmanager.data.ClearStatus.CLEARED -> "クリア"
        com.example.arcaea_mapmanager.data.ClearStatus.FULL_RECALL -> "Full Recall"
        com.example.arcaea_mapmanager.data.ClearStatus.PURE_MEMORY -> "Pure Memory"
        com.example.arcaea_mapmanager.data.ClearStatus.EASY_CLEAR -> "Easy Clear"
        com.example.arcaea_mapmanager.data.ClearStatus.HARD_CLEAR -> "Hard Clear"
    }
}

fun formatScore(score: Int): String {
    return String.format("%,d", score)
}