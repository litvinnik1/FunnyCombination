package com.example.funnycombination.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.funnycombination.data.HighScoreEntity
import androidx.compose.material3.MaterialTheme

@Composable
fun HighScoreScreen(
    highScores: List<HighScoreEntity>,
    onBack: () -> Unit,
    onClearScores: () -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "High Scores", modifier = Modifier.padding(bottom = 16.dp))
        Text(text = "Total scores: ${highScores.size}", modifier = Modifier.padding(bottom = 8.dp))
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Дата")
            Text(text = "Довжина")
        }
        val maxScore = highScores.maxOfOrNull { it.score }
        if (highScores.isEmpty()) {
            Text(text = "Рекордів ще немає", modifier = Modifier.padding(vertical = 16.dp))
        } else {
            highScores.forEach {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    val isTop = it.score == maxScore
                    Text(text = it.date)
                    Text(
                        text = it.score.toString(),
                        fontWeight = if (isTop) FontWeight.Bold else FontWeight.Normal,
                        color = if (isTop) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(onClick = onBack) {
                Text("Назад")
            }
            Button(onClick = onClearScores) {
                Text("Очистити")
            }
        }
    }
} 