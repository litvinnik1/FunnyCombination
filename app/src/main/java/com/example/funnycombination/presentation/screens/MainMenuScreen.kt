package com.example.funnycombination.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.funnycombination.R

@Composable
fun MainMenuScreen(
    onPlay: () -> Unit,
    onHighScore: () -> Unit,
    onPrivacyPolicy: () -> Unit,
    onExit: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "🎮 ${stringResource(R.string.app_name)}",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Button(
            onClick = onPlay,
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            Text("🎯 ${stringResource(R.string.play)}", color = Color.White)
        }

        Button(
            onClick = onHighScore,
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            Text("🏆 ${stringResource(R.string.high_scores)}", color = Color.White)
        }

        Button(
            onClick = onPrivacyPolicy,
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            Text("📋 ${stringResource(R.string.privacy_policy)}", color = Color.White)
        }

        Button(
            onClick = onExit,
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            Text("🚪 ${stringResource(R.string.exit)}", color = Color.White)
        }
    }
}