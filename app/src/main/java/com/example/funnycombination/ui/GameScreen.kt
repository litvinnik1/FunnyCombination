package com.example.funnycombination.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun GameScreen(
    demoEmoji: String?,
    userInput: List<String>,
    level: Int,
    isInputEnabled: Boolean,
    onEmojiClick: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Рівень: $level", modifier = Modifier.padding(bottom = 16.dp))
        // Поле для демонстрації або введення
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 32.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            if (demoEmoji != null) {
                Text(
                    text = demoEmoji,
                    modifier = Modifier.padding(8.dp),
                    fontSize = 40.sp
                )
            } else {
                userInput.forEach { emoji ->
                    Text(
                        text = emoji,
                        modifier = Modifier.padding(8.dp),
                        fontSize = 40.sp
                    )
                }
            }
        }
        // Панель з кнопками-емодзі (2 рядки)
        val emojis = listOf("😀", "🐱", "🍕", "🚗", "⚽")
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                emojis.take(3).forEach { emoji ->
                    Button(
                        onClick = { onEmojiClick(emoji) },
                        enabled = isInputEnabled
                    ) {
                        Text(emoji, fontSize = 32.sp)
                    }
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                emojis.drop(3).forEach { emoji ->
                    Button(
                        onClick = { onEmojiClick(emoji) },
                        enabled = isInputEnabled
                    ) {
                        Text(emoji, fontSize = 32.sp)
                    }
                }
            }
        }
    }
} 