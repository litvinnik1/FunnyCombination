package com.example.funnycombination.presentation.state

import com.example.funnycombination.data.HighScoreEntity

// State для гри
data class GameScreenState(
    val sequence: List<String> = emptyList(),
    val userInput: List<String> = emptyList(),
    val level: Int = 1,
    val gameState: GameState = GameState.ShowingSequence,
    val demoEmoji: String? = null,
    val isInputEnabled: Boolean = false,
    val navigateToGameOver: Boolean = false
)

sealed class GameState {
    object ShowingSequence : GameState()
    object WaitingForInput : GameState()
    object GameOver : GameState()
}

// State для Game Over екрану
data class GameOverState(
    val score: Int = 0,
    val isHighScore: Boolean = false,
    val isProcessing: Boolean = true
)

// State для High Score екрану
data class HighScoreState(
    val highScores: List<HighScoreEntity> = emptyList(),
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