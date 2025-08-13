package com.example.funnycombination.presentation.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.funnycombination.R
import kotlinx.coroutines.delay

@Composable
fun MainMenuScreen(
    onPlay: () -> Unit,
    onHighScore: () -> Unit,
    onPrivacyPolicy: () -> Unit,
    onExit: () -> Unit,
) {
    var showContent by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        showContent = true
    }
    
    // Анімація появи контенту
    AnimatedVisibility(
        visible = showContent,
        enter = fadeIn(animationSpec = tween(1000)) + 
                slideInVertically(
                    initialOffsetY = { it / 2 },
                    animationSpec = tween(1000, easing = FastOutSlowInEasing)
                ),
        exit = fadeOut(animationSpec = tween(300))
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Анімований заголовок
            AnimatedTitle()
            
            Spacer(modifier = Modifier.height(48.dp))
            
            // Анімовані кнопки з затримкою
            AnimatedButton(
                text = "🎯 ${stringResource(R.string.play)}",
                onClick = onPlay,
                delay = 200
            )
            
            AnimatedButton(
                text = "🏆 ${stringResource(R.string.high_scores)}",
                onClick = onHighScore,
                delay = 400
            )
            
            AnimatedButton(
                text = "📋 ${stringResource(R.string.privacy_policy)}",
                onClick = onPrivacyPolicy,
                delay = 600
            )
            
            AnimatedButton(
                text = "🚪 ${stringResource(R.string.exit)}",
                onClick = onExit,
                delay = 800
            )
        }
    }
}

@Composable
fun AnimatedTitle() {
    val infiniteTransition = rememberInfiniteTransition(label = "title")
    
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )
    
    val rotation by infiniteTransition.animateFloat(
        initialValue = -2f,
        targetValue = 2f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "rotation"
    )
    
    Text(
        text = "🎮 ${stringResource(R.string.app_name)}",
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White,
        modifier = Modifier
            .scale(scale)
            .graphicsLayer(rotationZ = rotation)
    )
}

@Composable
fun AnimatedButton(
    text: String,
    onClick: () -> Unit,
    delay: Int
) {
    var showButton by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        delay(delay.toLong())
        showButton = true
    }
    
    AnimatedVisibility(
        visible = showButton,
        enter = fadeIn(animationSpec = tween(500)) + 
                slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(500, easing = FastOutSlowInEasing)
                ),
        exit = fadeOut(animationSpec = tween(300))
    ) {
        val buttonScale by animateFloatAsState(
            targetValue = 1f,
            animationSpec = tween(100),
            label = "buttonScale"
        )
        
        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .scale(buttonScale),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = Color(0xFF4CAF50)
            )
        ) {
            Text(
                text = text,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}