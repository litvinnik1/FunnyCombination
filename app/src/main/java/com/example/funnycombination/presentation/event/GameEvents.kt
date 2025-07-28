package com.example.funnycombination.presentation.event

// Events для гри
sealed class GameEvent {
    object StartGame : GameEvent()
    object OnSequenceShown : GameEvent()
    data class OnEmojiClick(val emoji: String) : GameEvent()
    object ResetGame : GameEvent()
}

// Events для High Score
sealed class HighScoreEvent {
    object LoadHighScores : HighScoreEvent()
    data class AddHighScore(val date: String, val score: Int) : HighScoreEvent()
    object ClearAllScores : HighScoreEvent()
}

// Events для Game Over
sealed class GameOverEvent {
    object ProcessScore : GameOverEvent()
    object NavigateToMainMenu : GameOverEvent()
    object NavigateToPlayAgain : GameOverEvent()
}

// Events для Main Menu
sealed class MainMenuEvent {
    object NavigateToGame : MainMenuEvent()
    object NavigateToHighScore : MainMenuEvent()
    object NavigateToPrivacyPolicy : MainMenuEvent()
    object ExitApp : MainMenuEvent()
}

// Events для Splash Screen
sealed class SplashEvent {
    object OnTimeout : SplashEvent()
}

// Events для Privacy Policy
sealed class PrivacyPolicyEvent {
    object NavigateBack : PrivacyPolicyEvent()
} 