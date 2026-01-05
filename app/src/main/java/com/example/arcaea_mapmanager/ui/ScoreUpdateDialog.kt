package com.example.arcaea_mapmanager.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.arcaea_mapmanager.data.ClearStatus
import com.example.arcaea_mapmanager.data.SongEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScoreUpdateDialog(
    song: SongEntity,
    onDismiss: () -> Unit,
    onConfirm: (Int, Int, Int, Int, ClearStatus) -> Unit
) {
    var score by remember { mutableStateOf(song.highScore.toString()) }
    var pure by remember { mutableStateOf(song.pure.toString()) }
    var far by remember { mutableStateOf(song.far.toString()) }
    var lost by remember { mutableStateOf(song.lost.toString()) }
    var selectedStatus by remember { mutableStateOf(song.status) }
    var expandedStatus by remember { mutableStateOf(false) }

    val statuses = ClearStatus.values().toList()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("${song.title} - スコア更新") },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = score,
                    onValueChange = { score = it },
                    label = { Text("スコア") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = pure,
                        onValueChange = { pure = it },
                        label = { Text("PURE") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = far,
                        onValueChange = { far = it },
                        label = { Text("FAR") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = lost,
                        onValueChange = { lost = it },
                        label = { Text("LOST") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                ExposedDropdownMenuBox(
                    expanded = expandedStatus,
                    onExpandedChange = { expandedStatus = it }
                ) {
                    OutlinedTextField(
                        value = getStatusText(selectedStatus),
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("クリア状況") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expandedStatus) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedStatus,
                        onDismissRequest = { expandedStatus = false }
                    ) {
                        statuses.forEach { status ->
                            DropdownMenuItem(
                                text = { Text(getStatusText(status)) },
                                onClick = {
                                    selectedStatus = status
                                    expandedStatus = false
                                }
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm(
                        score.toIntOrNull() ?: 0,
                        pure.toIntOrNull() ?: 0,
                        far.toIntOrNull() ?: 0,
                        lost.toIntOrNull() ?: 0,
                        selectedStatus
                    )
                }
            ) {
                Text("更新")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("キャンセル")
            }
        }
    )
}