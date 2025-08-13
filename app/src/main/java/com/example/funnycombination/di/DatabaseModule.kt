package com.example.funnycombination.di

import android.content.Context
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
        return AppDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideHighScoreDao(database: AppDatabase): HighScoreDao {
        return database.highScoreDao()
    }
} 