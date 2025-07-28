package com.example.funnycombination.di

import android.util.Log
import com.example.funnycombination.data.repository.HighScoreRepositoryImpl
import com.example.funnycombination.domain.repository.HighScoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideHighScoreRepository(
        highScoreRepositoryImpl: HighScoreRepositoryImpl
    ): HighScoreRepository {
        Log.d("RepositoryModule", "Providing HighScoreRepository...")
        return highScoreRepositoryImpl
    }
} 