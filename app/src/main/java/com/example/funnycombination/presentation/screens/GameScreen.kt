package com.example.funnycombination.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
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
    val emojis = listOf("😀", "🐱", "🍕", "🚗", "⚽")
    
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Рівень: $level",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        // Показуємо демо емодзі
        if (demoEmoji != null) {
            Text(
                text = demoEmoji,
                fontSize = 48.sp,
                modifier = Modifier.padding(vertical = 32.dp)
            )
        }
        
        // Показуємо введені користувачем емодзі
        if (userInput.isNotEmpty()) {
            Text(
                text = "Ваші відповіді:",
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = userInput.joinToString(" "),
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 24.dp)
            )
        }
        
        // Кнопки для введення
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            emojis.chunked(3).forEach { row ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    row.forEach { emoji ->
                        Button(
                            onClick = { onEmojiClick(emoji) },
                            enabled = isInputEnabled,
                            modifier = Modifier.size(80.dp)
                        ) {
                            Text(
                                text = emoji,
                                fontSize = 24.sp
                            )
                        }
                    }
                }
            }
        }
    }
} 