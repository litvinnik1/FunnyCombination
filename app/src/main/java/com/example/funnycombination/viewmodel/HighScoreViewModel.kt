package com.example.funnycombination.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.funnycombination.data.HighScoreEntity
import com.example.funnycombination.data.HighScoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HighScoreViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = HighScoreRepository(application)
    private val _highScores = MutableStateFlow<List<HighScoreEntity>>(emptyList())
    val highScores: StateFlow<List<HighScoreEntity>> = _highScores

    fun loadHighScores() {
        viewModelScope.launch {
            try {
                // Завантажуємо всі результати, але показуємо тільки топ-3
                val allScores = repository.getAll()
                val topScores = allScores.sortedByDescending { it.score }.take(3)
                _highScores.value = topScores
                Log.d("HighScoreVM", "Loaded ${allScores.size} scores, showing top 3")
                
                // Додаткова діагностика
                repository.getAllById()
                repository.getCount()
                repository.getMaxScore()
            } catch (e: Exception) {
                Log.e("HighScoreVM", "Error loading scores: ${e.message}")
            }
        }
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
        viewModelScope.launch {
            try {
                repository.clearAll()
                loadHighScores()
                Log.d("HighScoreVM", "Cleared all scores")
            } catch (e: Exception) {
                Log.e("HighScoreVM", "Error clearing scores: ${e.message}")
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
} 