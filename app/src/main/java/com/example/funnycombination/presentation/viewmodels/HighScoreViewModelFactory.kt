package com.example.funnycombination.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class HighScoreViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HighScoreViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HighScoreViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
} 