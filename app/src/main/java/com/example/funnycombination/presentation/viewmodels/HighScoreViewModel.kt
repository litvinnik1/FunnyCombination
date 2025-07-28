package com.example.funnycombination.presentation.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.funnycombination.data.HighScoreEntity
import com.example.funnycombination.data.HighScoreRepository
import com.example.funnycombination.presentation.state.HighScoreState
import com.example.funnycombination.presentation.event.HighScoreEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HighScoreViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = HighScoreRepository(application)
    
    // State
    private val _state = MutableStateFlow(HighScoreState())
    val state: StateFlow<HighScoreState> = _state.asStateFlow()
    
    // Для зворотної сумісності
    private val _highScores = MutableStateFlow<List<HighScoreEntity>>(emptyList())
    val highScores: StateFlow<List<HighScoreEntity>> = _highScores

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
                
                // Завантажуємо всі результати, але показуємо тільки топ-3
                val allScores = repository.getAll()
                val topScores = allScores.sortedByDescending { it.score }.take(3)
                _highScores.value = topScores
                
                _state.value = _state.value.copy(
                    highScores = topScores,
                    isLoading = false
                )
                
                Log.d("HighScoreVM", "Loaded ${allScores.size} scores, showing top 3")
                
                // Додаткова діагностика
                repository.getAllById()
                repository.getCount()
                repository.getMaxScore()
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
                Log.d("HighScoreVM", "Adding score: $score on $date")
                val isBest = repository.isNewHighScore(score)
                Log.d("HighScoreVM", "Is best score: $isBest")
                
                if (score > 0 && isBest) {
                    val entity = HighScoreEntity(date = date, score = score)
                    repository.insert(entity)
                    loadHighScoresInternal() // Перезавантажуємо тільки топ-3
                    Log.d("HighScoreVM", "Score saved as new high score")
                } else {
                    Log.d("HighScoreVM", "Score not saved: score=$score, isBest=$isBest")
                }
            } catch (e: Exception) {
                Log.e("HighScoreVM", "Error adding high score: ${e.message}")
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

    suspend fun isNewHighScore(score: Int): Boolean {
        return repository.isNewHighScore(score)
    }
    
    // Метод для отримання тільки топ-3 результатів
    fun getTopScores(): List<HighScoreEntity> {
        return _highScores.value.sortedByDescending { it.score }.take(3)
    }
    
    // Методи для зворотної сумісності
    fun loadHighScores() {
        onEvent(HighScoreEvent.LoadHighScores)
    }
    
    fun addHighScoreIfBest(date: String, score: Int, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                Log.d("HighScoreVM", "Adding score: $score on $date")
                val isBest = repository.isNewHighScore(score)
                Log.d("HighScoreVM", "Is best score: $isBest")
                
                if (score > 0 && isBest) {
                    val entity = HighScoreEntity(date = date, score = score)
                    repository.insert(entity)
                    loadHighScores() // Перезавантажуємо тільки топ-3
                    onResult(true)
                    Log.d("HighScoreVM", "Score saved as new high score")
                } else {
                    Log.d("HighScoreVM", "Score not saved: score=$score, isBest=$isBest")
                    onResult(false)
                }
            } catch (e: Exception) {
                Log.e("HighScoreVM", "Error adding high score: ${e.message}")
                onResult(false)
            }
        }
    }
    
    fun clearAllScores() {
        onEvent(HighScoreEvent.ClearAllScores)
    }
} 