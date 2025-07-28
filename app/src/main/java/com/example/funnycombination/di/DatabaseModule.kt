package com.example.funnycombination.di

import android.content.Context
import android.util.Log
import com.example.funnycombination.data.local.AppDatabase
import com.example.funnycombination.data.local.dao.HighScoreDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        Log.d("DatabaseModule", "Creating database...")
        val database = AppDatabase.getDatabase(context)
        Log.d("DatabaseModule", "Database created successfully")
        return database
    }

    @Provides
    @Singleton
    fun provideHighScoreDao(database: AppDatabase): HighScoreDao {
        Log.d("DatabaseModule", "Creating HighScoreDao...")
        val dao = database.highScoreDao()
        Log.d("DatabaseModule", "HighScoreDao created successfully")
        return dao
    }
} 