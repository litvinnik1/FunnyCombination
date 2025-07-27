package com.example.funnycombination.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HighScoreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(highScore: HighScoreEntity)

    @Query("SELECT * FROM high_scores ORDER BY score DESC, timestamp DESC")
    suspend fun getAll(): List<HighScoreEntity>

    @Query("SELECT * FROM high_scores ORDER BY id ASC")
    suspend fun getAllById(): List<HighScoreEntity>

    @Query("SELECT COUNT(*) FROM high_scores")
    suspend fun getCount(): Int

    @Query("DELETE FROM high_scores")
    suspend fun deleteAll()

    @Query("SELECT MAX(score) FROM high_scores")
    suspend fun getMaxScore(): Int?
} 