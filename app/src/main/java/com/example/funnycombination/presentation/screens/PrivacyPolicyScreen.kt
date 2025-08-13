package com.example.funnycombination.presentation.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.funnycombination.R
import kotlinx.coroutines.delay

@Composable
fun PrivacyPolicyScreen(onBack: () -> Unit) {
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
            // Анімований заголовок
            AnimatedPrivacyTitle()
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Анімований контент
            AnimatedPrivacyContent()
            
            Spacer(modifier = Modifier.weight(1f))
            
            // Анімована кнопка
            AnimatedBackButton(onBack)
        }
    }
}

@Composable
fun AnimatedPrivacyTitle() {
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
            text = stringResource(R.string.privacy_policy_title),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}

@Composable
fun AnimatedPrivacyContent() {
    var showContent by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        delay(400)
        showContent = true
    }
    
    AnimatedVisibility(
        visible = showContent,
        enter = fadeIn(animationSpec = tween(500)) + 
                slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(500, easing = FastOutSlowInEasing)
                ),
        exit = fadeOut(animationSpec = tween(300))
    ) {
        Text(
            text = stringResource(R.string.privacy_policy_content),
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}

@Composable
fun AnimatedBackButton(onBack: () -> Unit) {
    var showButton by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        delay(600)
        showButton = true
    }
    
    AnimatedVisibility(
        visible = showButton,
        enter = fadeIn(animationSpec = tween(500)) + 
                slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(500, easing = FastOutSlowInEasing)
                ),
        exit = fadeOut(animationSpec = tween(300))
    ) {
        Button(
            onClick = onBack,
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2196F3)
            )
        ) {
            Text(
                text = stringResource(R.string.back),
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
} 