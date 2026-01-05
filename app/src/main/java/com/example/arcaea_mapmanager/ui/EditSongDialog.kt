package com.example.arcaea_mapmanager.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.arcaea_mapmanager.data.SongEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditSongDialog(
    song: SongEntity,
    onDismiss: () -> Unit,
    onConfirm: (String, String, Double, Int, String) -> Unit,
    onDelete: () -> Unit
) {
    var title by remember { mutableStateOf(song.title) }
    var selectedDifficulty by remember { mutableStateOf(song.difficulty) }
    var constant by remember { mutableStateOf(song.constant.toString()) }
    var bpm by remember { mutableStateOf(song.bpm.toString()) }
    var memo by remember { mutableStateOf(song.memo) }
    var expandedDifficulty by remember { mutableStateOf(false) }
    var showDeleteConfirm by remember { mutableStateOf(false) }

    val difficulties = listOf("PAST", "PRESENT", "FUTURE", "BEYOND")

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("曲を編集") },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 500.dp)
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("曲名") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                ExposedDropdownMenuBox(
                    expanded = expandedDifficulty,
                    onExpandedChange = { expandedDifficulty = it }
                ) {
                    OutlinedTextField(
                        value = selectedDifficulty,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("難易度") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expandedDifficulty) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedDifficulty,
                        onDismissRequest = { expandedDifficulty = false }
                    ) {
                        difficulties.forEach { difficulty ->
                            DropdownMenuItem(
                                text = { Text(difficulty) },
                                onClick = {
                                    selectedDifficulty = difficulty
                                    expandedDifficulty = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = constant,
                    onValueChange = { constant = it },
                    label = { Text("譜面定数") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = bpm,
                    onValueChange = { bpm = it },
                    label = { Text("BPM") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = memo,
                    onValueChange = { memo = it },
                    label = { Text("メモ") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 削除ボタン
                OutlinedButton(
                    onClick = { showDeleteConfirm = true },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("この曲を削除")
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (title.isNotBlank() && constant.isNotBlank() && bpm.isNotBlank()) {
                        onConfirm(
                            title,
                            selectedDifficulty,
                            constant.toDoubleOrNull() ?: 0.0,
                            bpm.toIntOrNull() ?: 0,
                            memo
                        )
                    }
                }
            ) {
                Text("保存")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("キャンセル")
            }
        }
    )

    // 削除確認ダイアログ
    if (showDeleteConfirm) {
        AlertDialog(
            onDismissRequest = { showDeleteConfirm = false },
            title = { Text("削除の確認") },
            text = { Text("「${song.title}」を削除してもよろしいですか？") },
            confirmButton = {
                Button(
                    onClick = {
                        onDelete()
                        showDeleteConfirm = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("削除")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirm = false }) {
                    Text("キャンセル")
                }
            }
        )
    }
}