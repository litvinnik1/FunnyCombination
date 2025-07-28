package com.example.funnycombination.domain.usecase

import com.example.funnycombination.domain.model.HighScore
import com.example.funnycombination.domain.repository.HighScoreRepository
import javax.inject.Inject

class AddHighScoreUseCase @Inject constructor(
    private val repository: HighScoreRepository
) {
    suspend operator fun invoke(date: String, score: Int): Boolean {
        return if (score > 0 && repository.isNewHighScore(score)) {
            repository.insert(HighScore(date = date, score = score))
            true
        } else {
            false
        }
    }
} 