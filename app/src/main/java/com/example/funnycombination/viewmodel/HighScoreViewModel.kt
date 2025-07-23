package com.example.funnycombination.viewmodel

import android.app.Application
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
            _highScores.value = repository.getAll()
        }
    }

    fun addHighScoreIfBest(date: String, score: Int, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val isBest = repository.isNewHighScore(score)
            if (isBest) {
                repository.insert(HighScoreEntity(date = date, score = score))
                loadHighScores()
                onResult(true)
            } else {
                onResult(false)
            }
        }
    }

    suspend fun isNewHighScore(score: Int): Boolean {
        return repository.isNewHighScore(score)
    }
} 