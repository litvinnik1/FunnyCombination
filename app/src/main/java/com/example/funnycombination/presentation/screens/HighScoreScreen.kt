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
import com.example.funnycombination.domain.model.HighScore
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.funnycombination.R

@Composable
fun HighScoreScreen(
    highScores: List<HighScore>,
    onBack: () -> Unit,
    onClearScores: () -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.high_scores_title),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
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
                    text = stringResource(R.string.no_scores),
                    fontSize = 18.sp,
                    color = Color.White
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
                    text = stringResource(R.string.place),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.White
                )
                Text(
                    text = stringResource(R.string.date),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.White
                )
                Text(
                    text = stringResource(R.string.result),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.White
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
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    
                    // Дата
                    Text(
                        text = score.date,
                        fontSize = 14.sp,
                        color = Color.White
                    )
                    
                    // Результат
                    Text(
                        text = score.score.toString(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
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
                Text(stringResource(R.string.back), color = Color.White)
            }
            Button(
                onClick = onClearScores,
                modifier = Modifier.weight(1f)
            ) {
                Text(stringResource(R.string.clear_all), color = Color.White)
            }
        }
    }
} 