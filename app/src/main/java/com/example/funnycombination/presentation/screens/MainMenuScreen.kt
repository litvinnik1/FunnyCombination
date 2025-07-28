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

@Composable
fun MainMenuScreen(
    onPlay: () -> Unit,
    onHighScore: () -> Unit,
    onPrivacyPolicy: () -> Unit,
    onExit: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "🎮 Funny Combination",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 32.dp)
        )
        
        Button(
            onClick = onPlay,
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            Text("🎯 Грати")
        }
        
        Button(
            onClick = onHighScore,
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            Text("🏆 High Score")
        }
        
        Button(
            onClick = onPrivacyPolicy,
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            Text("📋 Політика конфіденційності")
        }
        
        Button(
            onClick = onExit,
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            Text("🚪 Вихід")
        }
    }
} 