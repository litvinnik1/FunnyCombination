package com.example.funnycombination.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.example.funnycombination.presentation.state.GameOverState
import com.example.funnycombination.presentation.event.GameOverEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class GameOverViewModel @Inject constructor() : ViewModel() {
    
    // State
    private val _state = MutableStateFlow(GameOverState())
    val state: StateFlow<GameOverState> = _state.asStateFlow()

    fun onEvent(event: GameOverEvent) {
        when (event) {
            is GameOverEvent.ProcessScore -> processScore()
            is GameOverEvent.NavigateToMainMenu -> navigateToMainMenu()
            is GameOverEvent.NavigateToPlayAgain -> navigateToPlayAgain()
        }
    }
    
    fun setScore(score: Int) {
        _state.value = _state.value.copy(score = score)
    }
    
    fun setHighScore(isHighScore: Boolean) {
        _state.value = _state.value.copy(
            isHighScore = isHighScore,
            isProcessing = false
        )
    }
    
    private fun processScore() {
        // Логіка обробки рахунку
        _state.value = _state.value.copy(isProcessing = true)
    }
    
    private fun navigateToMainMenu() {
        // Логіка навігації до головного меню
    }
    
    private fun navigateToPlayAgain() {
        // Логіка навігації до гри знову
    }
} 