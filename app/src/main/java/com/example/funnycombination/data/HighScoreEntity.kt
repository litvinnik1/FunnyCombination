package com.example.funnycombination.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "high_scores")
data class HighScoreEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String,
    val score: Int,
    val timestamp: Long = System.currentTimeMillis()
) 