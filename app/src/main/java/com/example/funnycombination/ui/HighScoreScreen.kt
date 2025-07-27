package com.example.funnycombination.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.funnycombination.data.HighScoreEntity
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color

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
        Text(
            text = "🏆 High Scores",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        
        // Показуємо тільки 3 найкращих результати (вже відсортовані в ViewModel)
        val topScores = highScores
        
        if (topScores.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Рекордів ще немає",
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            // Заголовок таблиці
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Місце",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = "Дата",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = "Результат",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
            
            // Список результатів
            topScores.forEachIndexed { index, score ->
                val place = index + 1
                val backgroundColor = when (place) {
                    1 -> Color(0xFFFFD700) // Золото
                    2 -> Color(0xFFC0C0C0) // Срібло
                    3 -> Color(0xFFCD7F32) // Бронза
                    else -> MaterialTheme.colorScheme.surfaceVariant
                }
                
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(backgroundColor.copy(alpha = 0.3f))
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Місце з емодзі
                    val placeEmoji = when (place) {
                        1 -> "🥇"
                        2 -> "🥈"
                        3 -> "🥉"
                        else -> "$place."
                    }
                    Text(
                        text = placeEmoji,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    
                    // Дата
                    Text(
                        text = score.date,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    // Результат
                    Text(
                        text = score.score.toString(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = when (place) {
                            1 -> MaterialTheme.colorScheme.primary
                            else -> MaterialTheme.colorScheme.onSurface
                        }
                    )
                }
                
                if (index < topScores.size - 1) {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
        
        Spacer(modifier = Modifier.weight(1f))
        
        // Кнопки
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(top = 24.dp)
        ) {
            Button(
                onClick = onBack,
                modifier = Modifier.weight(1f)
            ) {
                Text("Назад")
            }
            Button(
                onClick = onClearScores,
                modifier = Modifier.weight(1f)
            ) {
                Text("Очистити")
            }
        }
    }
} 