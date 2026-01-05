package com.example.arcaea_mapmanager.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.arcaea_mapmanager.data.SortOrder
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBackClick: () -> Unit,
    viewModel: SongViewModel,
    settingsViewModel: SettingsViewModel
) {
    val settings by settingsViewModel.settings.collectAsState(
        initial = com.example.arcaea_mapmanager.data.SettingsEntity()
    )
    val songList by viewModel.allSongs.collectAsState(initial = emptyList())

    var showDeleteAllConfirm by remember { mutableStateOf(false) }
    var showExportDialog by remember { mutableStateOf(false) }
    var expandedSort by remember { mutableStateOf(false) }

    val sortOptions = mapOf(
        SortOrder.CONSTANT_DESC to "定数順（高→低）",
        SortOrder.CONSTANT_ASC to "定数順（低→高）",
        SortOrder.TITLE_ASC to "曲名順（A→Z）",
        SortOrder.TITLE_DESC to "曲名順（Z→A）",
        SortOrder.DATE_ADDED_DESC to "追加日順（新→古）",
        SortOrder.DATE_ADDED_ASC to "追加日順（古→新）",
        SortOrder.STATUS_DESC to "クリア状況順"
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("設定") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, "戻る")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // 表示設定セクション
            SettingsSection(title = "表示設定") {
                // ダークモード切り替え
                SettingsItem(
                    title = "ダークモード",
                    description = "アプリの外観を変更します",
                    trailing = {
                        Switch(
                            checked = settings.isDarkMode,
                            onCheckedChange = { settingsViewModel.updateDarkMode(it) }
                        )
                    }
                )

                Divider()

                // ソート順
                SettingsItem(
                    title = "曲のソート順",
                    description = sortOptions[settings.sortOrder] ?: "定数順",
                    onClick = { expandedSort = true }
                )

                Divider()

                // 詰めている曲のみ表示
                SettingsItem(
                    title = "詰めている曲のみ表示",
                    description = "進行中の曲だけを表示します",
                    trailing = {
                        Switch(
                            checked = settings.showInProgressOnly,
                            onCheckedChange = { settingsViewModel.updateShowInProgressOnly(it) }
                        )
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // データ管理セクション
            SettingsSection(title = "データ管理") {
                SettingsItem(
                    title = "自動バックアップ",
                    description = "データを自動的にバックアップします",
                    trailing = {
                        Switch(
                            checked = settings.autoBackup,
                            onCheckedChange = { settingsViewModel.updateAutoBackup(it) }
                        )
                    }
                )

                if (settings.lastBackupDate > 0) {
                    Divider()
                    val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault())
                    val lastBackupText = dateFormat.format(Date(settings.lastBackupDate))
                    SettingsItem(
                        title = "最終バックアップ",
                        description = lastBackupText
                    )
                }

                Divider()

                SettingsItem(
                    title = "データをエクスポート",
                    description = "曲データをファイルに出力します",
                    onClick = { showExportDialog = true }
                )

                Divider()

                SettingsItem(
                    title = "データをインポート",
                    description = "ファイルから曲データを読み込みます",
                    onClick = { /* TODO: インポート機能 */ }
                )

                Divider()

                SettingsItem(
                    title = "すべてのデータを削除",
                    description = "すべての曲データを削除します",
                    onClick = { showDeleteAllConfirm = true },
                    isDestructive = true
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 統計情報セクション
            SettingsSection(title = "統計情報") {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        val totalSongs = songList.size
                        val clearedSongs = songList.count {
                            it.status != com.example.arcaea_mapmanager.data.ClearStatus.NOT_CLEARED
                        }
                        val inProgressSongs = songList.count { it.isInProgress }
                        val avgConstant = if (totalSongs > 0) {
                            songList.map { it.constant }.average()
                        } else 0.0

                        StatRow("登録曲数", "$totalSongs 曲")
                        StatRow("クリア済み", "$clearedSongs 曲")
                        StatRow("詰めている曲", "$inProgressSongs 曲")
                        StatRow("平均譜面定数", String.format("%.2f", avgConstant))
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // アプリ情報セクション
            SettingsSection(title = "アプリ情報") {
                SettingsItem(
                    title = "バージョン",
                    description = "1.0.0"
                )

                Divider()

                SettingsItem(
                    title = "ライセンス情報",
                    description = "オープンソースライセンスを表示",
                    onClick = { /* TODO: ライセンス画面 */ }
                )

                Divider()

                SettingsItem(
                    title = "開発者について",
                    description = "Arcaea Map Manager",
                    onClick = { /* TODO: 開発者情報 */ }
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }

    // ソート順選択ダイアログ
    if (expandedSort) {
        AlertDialog(
            onDismissRequest = { expandedSort = false },
            title = { Text("ソート順を選択") },
            text = {
                Column {
                    sortOptions.forEach { (order, label) ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    settingsViewModel.updateSortOrder(order)
                                    expandedSort = false
                                }
                                .padding(vertical = 12.dp)
                        ) {
                            RadioButton(
                                selected = settings.sortOrder == order,
                                onClick = {
                                    settingsViewModel.updateSortOrder(order)
                                    expandedSort = false
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(label)
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { expandedSort = false }) {
                    Text("閉じる")
                }
            }
        )
    }

    // 全削除確認ダイアログ
    if (showDeleteAllConfirm) {
        AlertDialog(
            onDismissRequest = { showDeleteAllConfirm = false },
            title = { Text("すべてのデータを削除") },
            text = { Text("すべての曲データが削除されます。この操作は取り消せません。") },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.deleteAllSongs()
                        showDeleteAllConfirm = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("削除")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteAllConfirm = false }) {
                    Text("キャンセル")
                }
            }
        )
    }

    // エクスポートダイアログ
    if (showExportDialog) {
        AlertDialog(
            onDismissRequest = { showExportDialog = false },
            title = { Text("エクスポート") },
            text = { Text("データのエクスポート機能は今後実装予定です。") },
            confirmButton = {
                TextButton(onClick = { showExportDialog = false }) {
                    Text("OK")
                }
            }
        )
    }
}

@Composable
fun SettingsSection(
    title: String,
    content: @Composable () -> Unit
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            content()
        }
    }
}

@Composable
fun SettingsItem(
    title: String,
    description: String? = null,
    onClick: (() -> Unit)? = null,
    trailing: @Composable (() -> Unit)? = null,
    isDestructive: Boolean = false
) {
    val modifier = if (onClick != null) {
        Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    } else {
        Modifier.fillMaxWidth()
    }

    Row(
        modifier = modifier.padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = if (isDestructive) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
            )
            if (description != null) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        if (trailing != null) {
            trailing()
        }
    }
}

@Composable
fun StatRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium)
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}