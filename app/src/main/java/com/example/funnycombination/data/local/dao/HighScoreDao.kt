package com.example.funnycombination.data.local.dao

import androidx.room.*
import com.example.funnycombination.data.local.entity.HighScoreEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HighScoreDao {
    @Query("SELECT * FROM high_scores ORDER BY score DESC")
    suspend fun getAll(): List<HighScoreEntity>

    @Query("SELECT * FROM high_scores ORDER BY id ASC")
    suspend fun getAllById(): List<HighScoreEntity>

    @Query("SELECT COUNT(*) FROM high_scores")
    suspend fun getCount(): Int

    @Query("SELECT MAX(score) FROM high_scores")
    suspend fun getMaxScore(): Int?

    @Query("SELECT * FROM high_scores WHERE score > :score LIMIT 1")
    suspend fun hasHigherScore(score: Int): HighScoreEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(highScore: HighScoreEntity)

    @Query("DELETE FROM high_scores")
    suspend fun clearAll()
} 