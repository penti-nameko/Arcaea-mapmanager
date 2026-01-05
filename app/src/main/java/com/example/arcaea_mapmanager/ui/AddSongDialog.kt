package com.example.arcaea_mapmanager.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSongDialog(
    onDismiss: () -> Unit,
    onConfirm: (String, String, Double, Int) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var selectedDifficulty by remember { mutableStateOf("FUTURE") }
    var constant by remember { mutableStateOf("") }
    var bpm by remember { mutableStateOf("") }
    var expandedDifficulty by remember { mutableStateOf(false) }

    val difficulties = listOf("PAST", "PRESENT", "FUTURE", "BEYOND")

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("曲を追加") },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
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
                            bpm.toIntOrNull() ?: 0
                        )
                    }
                }
            ) {
                Text("追加")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("キャンセル")
            }
        }
    )
}