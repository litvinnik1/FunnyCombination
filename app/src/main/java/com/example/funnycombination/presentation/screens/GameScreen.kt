package com.example.funnycombination.presentation.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.offset
import androidx.compose.animation.*
import com.example.funnycombination.R
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

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
            text = "${stringResource(R.string.level)}: $level",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        // Показуємо демо емодзі з фізикою
        if (demoEmoji != null) {
            AnimatedEmoji(
                emoji = demoEmoji,
                modifier = Modifier.padding(vertical = 32.dp)
            )
            
            // Показуємо простий індикатор повторюваних емодзі
            ShowRepeatedEmojisIndicator(userInput)
        }
        
        // Показуємо введені користувачем емодзі
        if (userInput.isNotEmpty()) {
            Text(
                text = stringResource(R.string.your_answers),
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
                        AnimatedEmojiButton(
                            emoji = emoji,
                            onClick = { onEmojiClick(emoji) },
                            enabled = isInputEnabled
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AnimatedEmoji(
    emoji: String,
    modifier: Modifier = Modifier
) {
    val density = LocalDensity.current
    
    // Фізична модель: позиція та швидкість
    var positionY by remember { mutableStateOf(0f) }
    var velocityY by remember { mutableStateOf(0f) }
    var isAnimating by remember { mutableStateOf(true) }
    
    // Константи фізики
    val gravity = 0.8f
    val groundLevel = 0f
    val bounceDamping = 0.7f
    
    // Анімація обертання
    val rotation by rememberInfiniteTransition(label = "rotation").animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )
    
    // Анімація масштабування
    val scale by rememberInfiniteTransition(label = "scale").animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )
    
    LaunchedEffect(emoji) {
        // Скидаємо фізику для нового емодзі
        positionY = -100f
        velocityY = 0f
        isAnimating = true
        
        // Фізичний цикл
        while (isAnimating) {
            delay(16) // ~60 FPS
            
            // Оновлюємо позицію та швидкість
            positionY += velocityY
            velocityY += gravity
            
            // Перевіряємо зіткнення з землею
            if (positionY >= groundLevel) {
                positionY = groundLevel
                velocityY = -velocityY * bounceDamping
                
                // Зупиняємо анімацію після кількох відскоків
                if (kotlin.math.abs(velocityY) < 1f) {
                    isAnimating = false
                }
            }
        }
    }
    
    Text(
        text = emoji,
        fontSize = 72.sp,
        color = Color.White,
        modifier = modifier
            .offset(y = with(density) { positionY.toDp() })
            .rotate(rotation)
            .scale(scale)
    )
}

@Composable
fun ShowRepeatedEmojisIndicator(sequence: List<String>) {
    if (sequence.size < 2) return
    
    // Перевіряємо, чи є повторювані емодзі підряд
    val hasRepeatedEmojis = sequence.windowed(2).any { it[0] == it[1] }
    
    if (hasRepeatedEmojis) {
        var showIndicator by remember { mutableStateOf(false) }
        
        LaunchedEffect(sequence) {
            delay(300)
            showIndicator = true
        }
        
        AnimatedVisibility(
            visible = showIndicator,
            enter = fadeIn(animationSpec = tween(500)),
            exit = fadeOut(animationSpec = tween(300))
        ) {
            Text(
                text = "⚠️ Повторювані емодзі!",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFF9800),
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}

@Composable
fun AnimatedEmojiButton(
    emoji: String,
    onClick: () -> Unit,
    enabled: Boolean
) {
    var isPressed by remember { mutableStateOf(false) }
    
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.9f else 1f,
        animationSpec = tween(100),
        label = "buttonScale"
    )
    
    val rotation by rememberInfiniteTransition(label = "buttonRotation").animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(10000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )
    
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier
            .size(100.dp)
            .scale(scale)
            .rotate(if (enabled) rotation else 0f),
        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
            containerColor = if (enabled) Color(0xFF2196F3) else Color(0xFF9E9E9E)
        )
    ) {
        Text(
            text = emoji,
            fontSize = 36.sp,
            color = Color.White
        )
    }
}