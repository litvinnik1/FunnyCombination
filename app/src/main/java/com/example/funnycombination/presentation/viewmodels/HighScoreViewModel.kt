package com.example.funnycombination.presentation.viewmodels

import android.util.Log
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
                Log.d("HighScoreVM", "Loading high scores on thread: ${Thread.currentThread().name}")
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
                
                Log.d("HighScoreVM", "Loaded ${scores.size} scores, showing top 3")
            } catch (e: Exception) {
                Log.e("HighScoreVM", "Error loading scores: ${e.message}")
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
                Log.d("HighScoreVM", "=== ADDING HIGH SCORE ===")
                Log.d("HighScoreVM", "Score: $score, Date: $date on thread: ${Thread.currentThread().name}")
                
                val isBest = repository.isNewHighScore(score)
                val isTied = repository.isTiedHighScore(score)
                Log.d("HighScoreVM", "Is best score: $isBest, Is tied score: $isTied")
                
                if (score > 0 && isBest && !isTied) {
                    Log.d("HighScoreVM", "Saving new high score...")
                    repository.insert(HighScore(date = date, score = score))
                    Log.d("HighScoreVM", "High score saved successfully")
                    loadHighScoresInternal() // Перезавантажуємо тільки топ-3
                    Log.d("HighScoreVM", "High scores reloaded")
                } else {
                    Log.d("HighScoreVM", "Score not saved: score=$score, isBest=$isBest, isTied=$isTied")
                }
            } catch (e: Exception) {
                Log.e("HighScoreVM", "Error adding high score: ${e.message}")
                e.printStackTrace()
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
                Log.d("HighScoreVM", "Cleared all scores")
            } catch (e: Exception) {
                Log.e("HighScoreVM", "Error clearing scores: ${e.message}")
                _state.value = _state.value.copy(
                    error = e.message ?: "Unknown error"
                )
            }
        }
    }
    
    // Метод для отримання тільки топ-3 результатів
    fun getTopScores(): List<HighScore> {
        return _highScores.value.sortedByDescending { it.score }.take(3)
    }
    
    // Методи для зворотної сумісності
    fun loadHighScores() {
        onEvent(HighScoreEvent.LoadHighScores)
    }
    
    fun addHighScoreIfBest(date: String, score: Int, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                Log.d("HighScoreVM", "=== ADDING HIGH SCORE (CALLBACK) ===")
                Log.d("HighScoreVM", "Score: $score, Date: $date on thread: ${Thread.currentThread().name}")
                
                val isBest = repository.isNewHighScore(score)
                val isTied = repository.isTiedHighScore(score)
                Log.d("HighScoreVM", "Is best score: $isBest, Is tied score: $isTied")
                
                if (score > 0 && isBest && !isTied) {
                    Log.d("HighScoreVM", "Saving new high score...")
                    repository.insert(HighScore(date = date, score = score))
                    Log.d("HighScoreVM", "High score saved successfully")
                    
                    // Перезавантажуємо high scores
                    loadHighScoresInternal()
                    
                    onResult(true)
                    Log.d("HighScoreVM", "Callback: true")
                } else {
                    Log.d("HighScoreVM", "Score not saved: score=$score, isBest=$isBest, isTied=$isTied")
                    onResult(false)
                    Log.d("HighScoreVM", "Callback: false")
                }
            } catch (e: Exception) {
                Log.e("HighScoreVM", "Error adding high score: ${e.message}")
                e.printStackTrace()
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
                Log.d("HighScoreVM", "=== CHECKING TIED SCORE ===")
                Log.d("HighScoreVM", "Score: $score on thread: ${Thread.currentThread().name}")
                
                val isTied = repository.isTiedHighScore(score)
                Log.d("HighScoreVM", "Is tied score: $isTied")
                
                onResult(isTied)
            } catch (e: Exception) {
                Log.e("HighScoreVM", "Error checking tied score: ${e.message}")
                e.printStackTrace()
                onResult(false)
            }
        }
    }
} 