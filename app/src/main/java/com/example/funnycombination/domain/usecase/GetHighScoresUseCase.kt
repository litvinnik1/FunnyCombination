package com.example.funnycombination.domain.usecase

import com.example.funnycombination.domain.model.HighScore
import com.example.funnycombination.domain.repository.HighScoreRepository
import javax.inject.Inject

class GetHighScoresUseCase @Inject constructor(
    private val repository: HighScoreRepository
) {
    suspend operator fun invoke(): List<HighScore> {
        return repository.getAll()
    }
} 