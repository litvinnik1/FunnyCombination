package com.example.funnycombination.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.random.Random

sealed class GameState {
    object ShowingSequence : GameState()
    object WaitingForInput : GameState()
    object GameOver : GameState()
}

class GameViewModel : ViewModel() {
    private val emojis = listOf("😀", "🐱", "🍕", "🚗", "⚽")
    private val _sequence = mutableListOf<String>()
    private val _userInput = mutableListOf<String>()
    var level by mutableStateOf(1)
        private set
    var gameState by mutableStateOf<GameState>(GameState.ShowingSequence)
        private set
    val sequence: List<String> get() = _sequence
    val userInput: List<String> get() = _userInput
    
    // Метод для отримання поточного рахунку (кількість успішно пройдених рівнів)
    val currentScore: Int get() = level - 1

    fun startGame() {
        _sequence.clear()
        _userInput.clear()
        level = 1
        addToSequence()
        gameState = GameState.ShowingSequence
    }

    fun addToSequence() {
        _sequence.add(emojis.random())
        _userInput.clear()
    }

    fun onSequenceShown() {
        gameState = GameState.WaitingForInput
    }

    fun onEmojiClick(emoji: String) {
        if (gameState != GameState.WaitingForInput) return
        if (_sequence.isEmpty()) return // Захист від порожньої послідовності
        _userInput.add(emoji)
        val idx = _userInput.size - 1
        if (idx >= _sequence.size || _sequence[idx] != emoji) {
            gameState = GameState.GameOver
            return
        }
        if (_userInput.size == _sequence.size) {
            level++
            addToSequence()
            gameState = GameState.ShowingSequence
        }
    }

    fun resetGame() {
        startGame()
    }
} 