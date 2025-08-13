package com.example.funnycombination.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.animation.core.*
import androidx.compose.animation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import com.example.funnycombination.R
import kotlinx.coroutines.delay

@Composable
fun GameOverScreen(
    score: Int,
    isHighScore: Boolean,
    isTiedScore: Boolean = false,
    onMainMenu: () -> Unit,
    onPlayAgain: () -> Unit
) {
    var showContent by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        showContent = true
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.surface
            ),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = showContent,
            enter = fadeIn(animationSpec = tween(800)) + 
                    slideInVertically(
                        animationSpec = tween(800, easing = FastOutSlowInEasing),
                        initialOffsetY = { it / 2 }
                    ),
            exit = fadeOut(animationSpec = tween(300))
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(32.dp)
                ) {
                    // Анімований заголовок
                    AnimatedGameOverTitle()
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Анімований результат
                    AnimatedScoreCard(score, isHighScore)
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Анімовані повідомлення
                    AnimatedMessages(isHighScore, isTiedScore)
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Анімовані кнопки
                    AnimatedGameOverButtons(onMainMenu, onPlayAgain)
                }
            }
        }
    }
}

@Composable
fun AnimatedGameOverTitle() {
    var showTitle by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        delay(200)
        showTitle = true
    }
    
    AnimatedVisibility(
        visible = showTitle,
        enter = fadeIn(animationSpec = tween(500)) + 
                scaleIn(
                    initialScale = 0.5f,
                    animationSpec = tween(500, easing = FastOutSlowInEasing)
                ),
        exit = fadeOut(animationSpec = tween(300))
    ) {
        Text(
            text = stringResource(R.string.game_over_title),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}

@Composable
fun AnimatedScoreCard(score: Int, isHighScore: Boolean) {
    var showScoreCard by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        delay(400)
        showScoreCard = true
    }
    
    AnimatedVisibility(
        visible = showScoreCard,
        enter = fadeIn(animationSpec = tween(500)) + 
                slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(500, easing = FastOutSlowInEasing)
                ),
        exit = fadeOut(animationSpec = tween(300))
    ) {
        Card(
            modifier = Modifier
                .widthIn(max = 300.dp)
                .padding(vertical = 16.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (isHighScore) 
                    Color(0xFFFFD700).copy(alpha = 0.2f) 
                else 
                    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(24.dp)
            ) {
                Text(
                    text = stringResource(R.string.your_result),
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = score.toString(),
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isHighScore) 
                        MaterialTheme.colorScheme.primary 
                    else 
                        MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = stringResource(R.string.level),
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun AnimatedMessages(isHighScore: Boolean, isTiedScore: Boolean) {
    var showMessages by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        delay(600)
        showMessages = true
    }
    
    AnimatedVisibility(
        visible = showMessages,
        enter = fadeIn(animationSpec = tween(500)) + 
                slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(500, easing = FastOutSlowInEasing)
                ),
        exit = fadeOut(animationSpec = tween(300))
    ) {
        when {
            isHighScore && !isTiedScore -> {
                AnimatedHighScoreMessage()
            }
            isTiedScore -> {
                AnimatedTiedScoreMessage()
            }
            else -> {
                AnimatedTryAgainMessage()
            }
        }
    }
}

@Composable
fun AnimatedHighScoreMessage() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFD700).copy(alpha = 0.3f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "🏆 ",
                fontSize = 24.sp
            )
            Text(
                text = stringResource(R.string.new_record),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = " 🏆",
                fontSize = 24.sp
            )
        }
    }
}

@Composable
fun AnimatedTiedScoreMessage() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF87CEEB).copy(alpha = 0.3f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "🔄 ",
                fontSize = 24.sp
            )
            Text(
                text = stringResource(R.string.tied_record),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary
            )
            Text(
                text = " 🔄",
                fontSize = 24.sp
            )
        }
    }
}

@Composable
fun AnimatedTryAgainMessage() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "💪 ",
                fontSize = 20.sp
            )
            Text(
                text = stringResource(R.string.try_again),
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = " 💪",
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun AnimatedGameOverButtons(
    onMainMenu: () -> Unit,
    onPlayAgain: () -> Unit
) {
    var showButtons by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        delay(800)
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
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = onPlayAgain,
                modifier = Modifier.fillMaxWidth(),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = stringResource(R.string.play_again),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            
            Button(
                onClick = onMainMenu,
                modifier = Modifier.fillMaxWidth(),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text(
                    text = stringResource(R.string.main_menu),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
} 