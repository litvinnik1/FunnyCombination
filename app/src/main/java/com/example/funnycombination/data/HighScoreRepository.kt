package com.example.funnycombination.data

import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HighScoreRepository(context: Context) {
    private val db = AppDatabase.getDatabase(context)
    private val dao = db.highScoreDao()

    suspend fun insert(highScore: HighScoreEntity) = withContext(Dispatchers.IO) {
        try {
            Log.d("HighScoreRepo", "=== INSERTING SCORE ===")
            Log.d("HighScoreRepo", "Inserting: ${highScore.score} on ${highScore.date}")
            val currentMax = dao.getMaxScore() ?: 0
            Log.d("HighScoreRepo", "Current max score before insert: $currentMax")
            dao.insert(highScore)
            val newMax = dao.getMaxScore() ?: 0
            Log.d("HighScoreRepo", "New max score after insert: $newMax")
            Log.d("HighScoreRepo", "Insert successful")
        } catch (e: Exception) {
            Log.e("HighScoreRepo", "Error inserting: ${e.message}")
            throw e
        }
    }

    suspend fun getAll(): List<HighScoreEntity> = withContext(Dispatchers.IO) {
        try {
            val result = dao.getAll()
            Log.d("HighScoreRepo", "Got ${result.size} scores: ${result.map { it.score }}")
            result
        } catch (e: Exception) {
            Log.e("HighScoreRepo", "Error getting all: ${e.message}")
            emptyList()
        }
    }

    suspend fun getAllById(): List<HighScoreEntity> = withContext(Dispatchers.IO) {
        try {
            val result = dao.getAllById()
            Log.d("HighScoreRepo", "Got ${result.size} scores by ID: ${result.map { "ID:${it.id}, Score:${it.score}, Date:${it.date}" }}")
            result
        } catch (e: Exception) {
            Log.e("HighScoreRepo", "Error getting all by ID: ${e.message}")
            emptyList()
        }
    }

    suspend fun getCount(): Int = withContext(Dispatchers.IO) {
        try {
            val count = dao.getCount()
            Log.d("HighScoreRepo", "Database count: $count")
            count
        } catch (e: Exception) {
            Log.e("HighScoreRepo", "Error getting count: ${e.message}")
            0
        }
    }

    suspend fun getMaxScore(): Int = withContext(Dispatchers.IO) {
        try {
            val maxScore = dao.getMaxScore() ?: 0
            Log.d("HighScoreRepo", "Current max score: $maxScore")
            maxScore
        } catch (e: Exception) {
            Log.e("HighScoreRepo", "Error getting max score: ${e.message}")
            0
        }
    }

    suspend fun isNewHighScore(score: Int): Boolean = withContext(Dispatchers.IO) {
        try {
            Log.d("HighScoreRepo", "=== CHECKING NEW HIGH SCORE ===")
            val maxScore = dao.getMaxScore() ?: 0
            Log.d("HighScoreRepo", "Checking score: $score against max: $maxScore")
            
            val isNew = if (maxScore == 0) {
                score > 0 // Якщо немає рекордів, то будь-який результат > 0 є новим
            } else {
                score > maxScore // Інакше результат має бути більший за максимальний
            }
            Log.d("HighScoreRepo", "Is new high score: $isNew")
            isNew
        } catch (e: Exception) {
            Log.e("HighScoreRepo", "Error checking if new high score: ${e.message}")
            score > 0 // Якщо помилка, вважаємо новим якщо > 0
        }
    }

    suspend fun clearAll() = withContext(Dispatchers.IO) {
        try {
            dao.deleteAll()
            Log.d("HighScoreRepo", "Cleared all scores")
        } catch (e: Exception) {
            Log.e("HighScoreRepo", "Error clearing: ${e.message}")
        }
    }
} 