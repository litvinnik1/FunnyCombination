package com.example.funnycombination.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import com.example.funnycombination.domain.model.HighScore
import com.example.funnycombination.presentation.state.HighScoreState
import com.example.funnycombination.presentation.event.HighScoreEvent
import com.example.funnycombination.domain.repository.HighScoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class HighScoreViewModel @Inject constructor(
    private val repository: HighScoreRepository // Temporarily reverted to repository for debugging
    // private val getHighScoresUseCase: GetHighScoresUseCase, // Original Clean Arch
    // private val addHighScoreUseCase: AddHighScoreUseCase,   // Original Clean Arch
    // private val clearHighScoresUseCase: ClearHighScoresUseCase // Original Clean Arch
) : androidx.lifecycle.ViewModel() {
    
    private val _state = MutableStateFlow(HighScoreState())
    val state: StateFlow<HighScoreState> = _state.asStateFlow()
    
    private val _highScores = MutableStateFlow<List<HighScore>>(emptyList())
    val highScores: StateFlow<List<HighScore>> = _highScores.asStateFlow()
    
    init {
        // Завантажуємо high scores при ініціалізації
        loadHighScoresInternal()
    }

    fun onEvent(event: HighScoreEvent) {
        when (event) {
            is HighScoreEvent.LoadHighScores -> loadHighScoresInternal()
            is HighScoreEvent.AddHighScore -> addHighScoreIfBestInternal(event.date, event.score)
            is HighScoreEvent.ClearAllScores -> clearAllScoresInternal()
        }
    }

    private fun loadHighScoresInternal() {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isLoading = true, error = null)
                
                val scores = repository.getAll()
                val topScores = scores.sortedByDescending { it.score }.take(3)
                
                // Безпечно оновлюємо StateFlow
                _highScores.value = topScores
                _state.value = _state.value.copy(
                    highScores = topScores,
                    isLoading = false,
                    error = null
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Unknown error"
                )
            }
        }
    }

    private fun addHighScoreIfBestInternal(date: String, score: Int) {
        viewModelScope.launch {
            try {
                val isBest = repository.isNewHighScore(score)
                val isTied = repository.isTiedHighScore(score)
                
                if (score > 0 && isBest && !isTied) {
                    repository.insert(HighScore(date = date, score = score))
                    loadHighScoresInternal() // Перезавантажуємо тільки топ-3
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = e.message ?: "Unknown error"
                )
            }
        }
    }

    private fun clearAllScoresInternal() {
        viewModelScope.launch {
            try {
                repository.clearAll()
                loadHighScoresInternal()
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = e.message ?: "Unknown error"
                )
            }
        }
    }
    
    // Методи для зворотної сумісності
    fun loadHighScores() {
        onEvent(HighScoreEvent.LoadHighScores)
    }
    
    fun addHighScoreIfBest(date: String, score: Int, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val isBest = repository.isNewHighScore(score)
                val isTied = repository.isTiedHighScore(score)
                
                if (score > 0 && isBest && !isTied) {
                    repository.insert(HighScore(date = date, score = score))
                    
                    // Перезавантажуємо high scores
                    loadHighScoresInternal()
                    
                    onResult(true)
                } else {
                    onResult(false)
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = e.message ?: "Unknown error"
                )
                onResult(false)
            }
        }
    }
    
    fun clearAllScores() {
        onEvent(HighScoreEvent.ClearAllScores)
    }
    
    fun checkIfTiedScore(score: Int, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                val isTied = repository.isTiedHighScore(score)
                onResult(isTied)
            } catch (e: Exception) {
                onResult(false)
            }
        }
    }
} 