package com.example.funnycombination.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GameOverScreen(
    score: Int,
    isHighScore: Boolean,
    onMainMenu: () -> Unit,
    onPlayAgain: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(text = "Game Over!", modifier = Modifier.padding(bottom = 8.dp))
            Text(text = "Результат: $score")
            if (isHighScore) {
                Text(text = "Новий рекорд!", modifier = Modifier.padding(top = 8.dp))
            }
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Button(onClick = onMainMenu) {
                    Text("Головне меню")
                }
                Button(onClick = onPlayAgain) {
                    Text("Грати знову")
                }
            }
        }
    }
} 