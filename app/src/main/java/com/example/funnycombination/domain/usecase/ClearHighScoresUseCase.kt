package com.example.funnycombination.domain.usecase

import com.example.funnycombination.domain.repository.HighScoreRepository
import javax.inject.Inject

class ClearHighScoresUseCase @Inject constructor(
    private val repository: HighScoreRepository
) {
    suspend operator fun invoke() {
        repository.clearAll()
    }
} 