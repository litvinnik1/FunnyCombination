package com.example.funnycombination.data.repository

import com.example.funnycombination.data.local.dao.HighScoreDao
import com.example.funnycombination.data.local.entity.HighScoreEntity
import com.example.funnycombination.domain.model.HighScore
import com.example.funnycombination.domain.repository.HighScoreRepository
import javax.inject.Inject
import android.util.Log

class HighScoreRepositoryImpl @Inject constructor(
    private val dao: HighScoreDao
) : HighScoreRepository {

    override suspend fun getAll(): List<HighScore> {
        Log.d("Repository", "Getting all scores on thread: ${Thread.currentThread().name}")
        val scores = dao.getAll().map { it.toDomain() }
        Log.d("Repository", "Getting all scores: ${scores.size} scores")
        return scores
    }

    override suspend fun getAllById(): List<HighScore> {
        Log.d("Repository", "Getting all scores by ID on thread: ${Thread.currentThread().name}")
        val scores = dao.getAllById().map { it.toDomain() }
        Log.d("Repository", "Getting all scores by ID: ${scores.size} scores")
        return scores
    }

    override suspend fun getCount(): Int {
        Log.d("Repository", "Getting count on thread: ${Thread.currentThread().name}")
        val count = dao.getCount()
        Log.d("Repository", "Getting count: $count")
        return count
    }

    override suspend fun getMaxScore(): Int? {
        Log.d("Repository", "Getting max score on thread: ${Thread.currentThread().name}")
        val maxScore = dao.getMaxScore()
        Log.d("Repository", "Getting max score: $maxScore")
        return maxScore
    }

    override suspend fun hasHigherScore(score: Int): HighScore? {
        Log.d("Repository", "Checking for higher score than $score on thread: ${Thread.currentThread().name}")
        val higherScore = dao.hasHigherScore(score)?.toDomain()
        Log.d("Repository", "Checking for higher score than $score: ${higherScore != null}")
        return higherScore
    }

    override suspend fun insert(highScore: HighScore) {
        Log.d("Repository", "Inserting high score: ${highScore.score} on ${highScore.date} on thread: ${Thread.currentThread().name}")
        dao.insert(highScore.toEntity())
        Log.d("Repository", "High score inserted successfully")
    }

    override suspend fun clearAll() {
        Log.d("Repository", "Clearing all scores on thread: ${Thread.currentThread().name}")
        dao.clearAll()
        Log.d("Repository", "All scores cleared")
    }

    override suspend fun isNewHighScore(score: Int): Boolean {
        // Якщо немає результатів більших за поточний рахунок, то це новий рекорд
        val higherScore = dao.hasHigherScore(score)
        val isNew = higherScore == null
        Log.d("Repository", "Checking if score $score is new high score. Higher score exists: ${higherScore != null}, isNew: $isNew")
        return isNew
    }

    override suspend fun isTiedHighScore(score: Int): Boolean {
        // Перевіряємо, чи є результат з таким самим рахунком
        val maxScore = dao.getMaxScore()
        val isTied = maxScore != null && maxScore == score
        Log.d("Repository", "Checking if score $score is tied high score. Max score: $maxScore, isTied: $isTied")
        return isTied
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