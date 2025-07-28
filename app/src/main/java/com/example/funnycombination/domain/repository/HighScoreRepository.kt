package com.example.funnycombination.domain.repository

import com.example.funnycombination.domain.model.HighScore
import kotlinx.coroutines.flow.Flow

interface HighScoreRepository {
    suspend fun getAll(): List<HighScore>
    suspend fun getAllById(): List<HighScore>
    suspend fun getCount(): Int
    suspend fun getMaxScore(): Int?
    suspend fun hasHigherScore(score: Int): HighScore?
    suspend fun insert(highScore: HighScore)
    suspend fun clearAll()
    suspend fun isNewHighScore(score: Int): Boolean
    suspend fun isTiedHighScore(score: Int): Boolean
} 