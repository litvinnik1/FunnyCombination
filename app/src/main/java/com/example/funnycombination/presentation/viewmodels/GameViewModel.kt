package com.example.funnycombination.presentation.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.funnycombination.presentation.state.GameScreenState
import com.example.funnycombination.domain.model.GameState
import com.example.funnycombination.presentation.event.GameEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.random.Random
import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class GameViewModel @Inject constructor() : ViewModel() {
    private val emojis = listOf("😀", "🐱", "🍕", "🚗", "⚽")
    private val _sequence = mutableListOf<String>()
    private val _userInput = mutableListOf<String>()
    
    // State
    private val _state = MutableStateFlow(GameScreenState())
    val state: StateFlow<GameScreenState> = _state.asStateFlow()
    
    // Публічні властивості для зворотної сумісності
    var level by mutableStateOf(1)
        private set
    var gameState by mutableStateOf<GameState>(GameState.ShowingSequence)
        private set
    val sequence: List<String> get() = _sequence
    val userInput: List<String> get() = _userInput
    
    // Метод для отримання поточного рахунку (кількість успішно пройдених рівнів)
    val currentScore: Int get() = level - 1

    fun onEvent(event: GameEvent) {
        when (event) {
            is GameEvent.StartGame -> startGameInternal()
            is GameEvent.OnSequenceShown -> onSequenceShownInternal()
            is GameEvent.OnEmojiClick -> onEmojiClickInternal(event.emoji)
            is GameEvent.ResetGame -> resetGameInternal()
        }
    }

    // Публічні методи для зворотної сумісності
    fun startGame() {
        onEvent(GameEvent.StartGame)
    }
    
    fun onSequenceShown() {
        onEvent(GameEvent.OnSequenceShown)
    }
    
    fun onEmojiClick(emoji: String) {
        onEvent(GameEvent.OnEmojiClick(emoji))
    }
    
    fun resetGame() {
        onEvent(GameEvent.ResetGame)
    }

    private fun startGameInternal() {
        _sequence.clear()
        _userInput.clear()
        level = 1
        addToSequence()
        gameState = GameState.ShowingSequence
        updateState()
    }

    private fun addToSequence() {
        _sequence.add(emojis.random())
        _userInput.clear()
        updateState()
    }

    private fun onSequenceShownInternal() {
        gameState = GameState.WaitingForInput
        updateState()
    }

    private fun onEmojiClickInternal(emoji: String) {
        if (gameState != GameState.WaitingForInput) return
        if (_sequence.isEmpty()) return // Захист від порожньої послідовності
        _userInput.add(emoji)
        val idx = _userInput.size - 1
        if (idx >= _sequence.size || _sequence[idx] != emoji) {
            gameState = GameState.GameOver
            updateState()
            return
        }
        if (_userInput.size == _sequence.size) {
            level++
            addToSequence()
            gameState = GameState.ShowingSequence
            updateState()
        }
    }

    private fun resetGameInternal() {
        startGameInternal()
    }
    
    private fun updateState() {
        _state.value = GameScreenState(
            sequence = _sequence,
            userInput = _userInput,
            level = level,
            gameState = gameState,
            isInputEnabled = gameState is GameState.WaitingForInput,
            navigateToGameOver = gameState is GameState.GameOver
        )
    }
} 