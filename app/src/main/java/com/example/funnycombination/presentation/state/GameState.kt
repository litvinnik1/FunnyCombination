package com.example.funnycombination.presentation.state

import com.example.funnycombination.domain.model.HighScore

// State для гри
data class GameScreenState(
    val sequence: List<String> = emptyList(),
    val userInput: List<String> = emptyList(),
    val level: Int = 1,
    val gameState: com.example.funnycombination.domain.model.GameState = com.example.funnycombination.domain.model.GameState.ShowingSequence,
    val demoEmoji: String? = null,
    val isInputEnabled: Boolean = false,
    val navigateToGameOver: Boolean = false
)

// Видаляємо дублікат GameState, оскільки він тепер в domain layer

// State для Game Over екрану
data class GameOverState(
    val score: Int = 0,
    val isHighScore: Boolean = false,
    val isProcessing: Boolean = true
)

// State для High Score екрану
data class HighScoreState(
    val highScores: List<HighScore> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

// State для Main Menu
data class MainMenuState(
    val isLoading: Boolean = false
)

// State для Splash Screen
data class SplashState(
    val isLoading: Boolean = true
)

// State для Privacy Policy
data class PrivacyPolicyState(
    val isLoading: Boolean = false
) 