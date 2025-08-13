package com.example.funnycombination.presentation.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.funnycombination.R
import com.example.funnycombination.domain.model.HighScore
import kotlinx.coroutines.delay

@Composable
fun HighScoreScreen(
    highScores: List<HighScore>,
    onBack: () -> Unit,
    onClearScores: () -> Unit = {}
) {
    var showContent by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        showContent = true
    }
    
    AnimatedVisibility(
        visible = showContent,
        enter = fadeIn(animationSpec = tween(800)) + 
                slideInVertically(
                    initialOffsetY = { it / 3 },
                    animationSpec = tween(800, easing = FastOutSlowInEasing)
                ),
        exit = fadeOut(animationSpec = tween(300))
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
                AnimatedEmptyState()
            } else {
                // Заголовок таблиці
                AnimatedTableHeader()
                
                // Список результатів з анімацією
                topScores.forEachIndexed { index, score ->
                    AnimatedScoreRow(
                        score = score,
                        place = index + 1,
                        delay = index * 200
                    )
                    
                    if (index < topScores.size - 1) {
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            // Кнопки з анімацією
            AnimatedButtons(
                onBack = onBack,
                onClearScores = onClearScores
            )
        }
    }
}

@Composable
fun AnimatedEmptyState() {
    var showEmptyState by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        delay(300)
        showEmptyState = true
    }
    
    AnimatedVisibility(
        visible = showEmptyState,
        enter = fadeIn(animationSpec = tween(500)) + 
                scaleIn(
                    initialScale = 0.5f,
                    animationSpec = tween(500, easing = FastOutSlowInEasing)
                ),
        exit = fadeOut(animationSpec = tween(300))
    ) {
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
    }
}

@Composable
fun AnimatedTableHeader() {
    var showHeader by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        delay(500)
        showHeader = true
    }
    
    AnimatedVisibility(
        visible = showHeader,
        enter = fadeIn(animationSpec = tween(500)) + 
                slideInHorizontally(
                    initialOffsetX = { -it },
                    animationSpec = tween(500, easing = FastOutSlowInEasing)
                ),
        exit = fadeOut(animationSpec = tween(300))
    ) {
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
    }
}

@Composable
fun AnimatedScoreRow(
    score: HighScore,
    place: Int,
    delay: Int
) {
    var showRow by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        delay(delay.toLong())
        showRow = true
    }
    
    AnimatedVisibility(
        visible = showRow,
        enter = fadeIn(animationSpec = tween(500)) + 
                slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(500, easing = FastOutSlowInEasing)
                ),
        exit = fadeOut(animationSpec = tween(300))
    ) {
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
    }
}

@Composable
fun AnimatedButtons(
    onBack: () -> Unit,
    onClearScores: () -> Unit
) {
    var showButtons by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        delay(1000)
        showButtons = true
    }
    
    AnimatedVisibility(
        visible = showButtons,
        enter = fadeIn(animationSpec = tween(500)) + 
                slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(500, easing = FastOutSlowInEasing)
                ),
        exit = fadeOut(animationSpec = tween(300))
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(top = 24.dp)
        ) {
            Button(
                onClick = onBack,
                modifier = Modifier.weight(1f),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2196F3)
                )
            ) {
                Text(stringResource(R.string.back), color = Color.White)
            }
            Button(
                onClick = onClearScores,
                modifier = Modifier.weight(1f),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF44336)
                )
            ) {
                Text(stringResource(R.string.clear_all), color = Color.White)
            }
        }
    }
} 