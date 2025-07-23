package com.example.funnycombination.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MainMenuScreen(
    onPlay: () -> Unit,
    onHighScore: () -> Unit,
    onPrivacyPolicy: () -> Unit,
    onExit: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                text = "Funny Combination",
                style = MaterialTheme.typography.headlineLarge
            )
            Button(onClick = onPlay, modifier = Modifier.fillMaxWidth(0.7f)) {
                Text("Грати")
            }
            Button(onClick = onHighScore, modifier = Modifier.fillMaxWidth(0.7f)) {
                Text("High Score")
            }
            Button(onClick = onPrivacyPolicy, modifier = Modifier.fillMaxWidth(0.7f)) {
                Text("Privacy Policy")
            }
            Button(onClick = onExit, modifier = Modifier.fillMaxWidth(0.7f)) {
                Text("Вихід")
            }
        }
    }
} 