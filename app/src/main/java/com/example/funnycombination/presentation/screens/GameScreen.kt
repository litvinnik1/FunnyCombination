package com.example.funnycombination.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color

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
            color = Color.White,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        // Показуємо демо емодзі
        if (demoEmoji != null) {
            Text(
                text = demoEmoji,
                fontSize = 72.sp,
                color = Color.White,
                modifier = Modifier.padding(vertical = 32.dp)
            )
        }
        
        // Показуємо введені користувачем емодзі
        if (userInput.isNotEmpty()) {
            Text(
                text = "Ваші відповіді:",
                fontSize = 18.sp,
                color = Color.White,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = userInput.joinToString(" "),
                fontSize = 32.sp,
                color = Color.White,
                modifier = Modifier.padding(bottom = 24.dp)
            )
        }
        
        // Кнопки для введення
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            emojis.chunked(3).forEach { row ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.padding(vertical = 6.dp)
                ) {
                    row.forEach { emoji ->
                        Button(
                            onClick = { onEmojiClick(emoji) },
                            enabled = isInputEnabled,
                            modifier = Modifier.size(100.dp)
                        ) {
                            Text(
                                text = emoji,
                                fontSize = 36.sp,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
} 