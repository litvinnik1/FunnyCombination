package com.example.funnycombination.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HighScoreDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(highScore: HighScoreEntity)

    @Query("SELECT * FROM high_scores ORDER BY score DESC, date DESC")
    suspend fun getAll(): List<HighScoreEntity>
} 