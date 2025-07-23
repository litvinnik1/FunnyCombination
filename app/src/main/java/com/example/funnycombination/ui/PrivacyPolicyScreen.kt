package com.example.funnycombination.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PrivacyPolicyScreen(onBack: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Політика конфіденційності", modifier = Modifier.padding(bottom = 16.dp))
        Text(text = "Тут буде текст політики конфіденційності...", modifier = Modifier.padding(bottom = 32.dp))
        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = onBack) {
            Text("Назад")
        }
    }
} 