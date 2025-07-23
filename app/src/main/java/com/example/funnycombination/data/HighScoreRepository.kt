package com.example.funnycombination.data

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HighScoreRepository(context: Context) {
    private val db = AppDatabase.getDatabase(context)
    private val dao = db.highScoreDao()

    suspend fun insert(highScore: HighScoreEntity) = withContext(Dispatchers.IO) {
        dao.insert(highScore)
    }

    suspend fun getAll(): List<HighScoreEntity> = withContext(Dispatchers.IO) {
        dao.getAll()
    }

    suspend fun isNewHighScore(score: Int): Boolean = withContext(Dispatchers.IO) {
        val all = dao.getAll()
        return@withContext all.isEmpty() || score > all.maxOf { it.score }
    }
} 