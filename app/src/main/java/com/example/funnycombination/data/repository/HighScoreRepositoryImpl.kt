package com.example.funnycombination.data.repository

import com.example.funnycombination.data.local.dao.HighScoreDao
import com.example.funnycombination.data.local.entity.HighScoreEntity
import com.example.funnycombination.domain.model.HighScore
import com.example.funnycombination.domain.repository.HighScoreRepository
import javax.inject.Inject

class HighScoreRepositoryImpl @Inject constructor(
    private val dao: HighScoreDao
) : HighScoreRepository {

    override suspend fun getAll(): List<HighScore> {
        return dao.getAll().map { it.toDomain() }
    }

    override suspend fun getMaxScore(): Int? {
        return dao.getMaxScore()
    }

    override suspend fun hasHigherScore(score: Int): HighScore? {
        return dao.hasHigherScore(score)?.toDomain()
    }

    override suspend fun insert(highScore: HighScore) {
        dao.insert(highScore.toEntity())
    }

    override suspend fun clearAll() {
        dao.clearAll()
    }

    override suspend fun isNewHighScore(score: Int): Boolean {
        // Якщо немає результатів більших за поточний рахунок, то це новий рекорд
        val higherScore = dao.hasHigherScore(score)
        return higherScore == null
    }

    override suspend fun isTiedHighScore(score: Int): Boolean {
        // Перевіряємо, чи є результат з таким самим рахунком
        val maxScore = dao.getMaxScore()
        return maxScore != null && maxScore == score
    }

    private fun HighScoreEntity.toDomain(): HighScore {
        return HighScore(
            id = id,
            date = date,
            score = score
        )
    }

    private fun HighScore.toEntity(): HighScoreEntity {
        return HighScoreEntity(
            id = id,
            date = date,
            score = score
        )
    }
} 