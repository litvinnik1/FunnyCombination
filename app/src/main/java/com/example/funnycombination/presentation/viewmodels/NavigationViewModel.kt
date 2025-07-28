package com.example.funnycombination.presentation.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel

sealed class NavigationEvent {
    object NavigateToSplash : NavigationEvent()
    object NavigateToMainMenu : NavigationEvent()
    object NavigateToGame : NavigationEvent()
    object NavigateToHighScore : NavigationEvent()
    object NavigateToPrivacyPolicy : NavigationEvent()
    data class NavigateToGameOver(val score: Int) : NavigationEvent()
    object NavigateBack : NavigationEvent()
    object ExitApp : NavigationEvent()
}

data class NavigationState(
    val currentRoute: String = "splash",
    val previousRoute: String? = null,
    val isNavigating: Boolean = false
)

@HiltViewModel
class NavigationViewModel @Inject constructor() : ViewModel() {
    
    private val _state = MutableStateFlow(NavigationState())
    val state: StateFlow<NavigationState> = _state.asStateFlow()
    
    fun onEvent(event: NavigationEvent) {
        when (event) {
            is NavigationEvent.NavigateToSplash -> navigateToSplash()
            is NavigationEvent.NavigateToMainMenu -> navigateToMainMenu()
            is NavigationEvent.NavigateToGame -> navigateToGame()
            is NavigationEvent.NavigateToHighScore -> navigateToHighScore()
            is NavigationEvent.NavigateToPrivacyPolicy -> navigateToPrivacyPolicy()
            is NavigationEvent.NavigateToGameOver -> navigateToGameOver(event.score)
            is NavigationEvent.NavigateBack -> navigateBack()
            is NavigationEvent.ExitApp -> exitApp()
        }
    }
    
    private fun navigateToSplash() {
        _state.value = _state.value.copy(
            previousRoute = _state.value.currentRoute,
            currentRoute = "splash",
            isNavigating = true
        )
    }
    
    private fun navigateToMainMenu() {
        _state.value = _state.value.copy(
            previousRoute = _state.value.currentRoute,
            currentRoute = "main_menu",
            isNavigating = true
        )
    }
    
    private fun navigateToGame() {
        _state.value = _state.value.copy(
            previousRoute = _state.value.currentRoute,
            currentRoute = "game",
            isNavigating = true
        )
    }
    
    private fun navigateToHighScore() {
        _state.value = _state.value.copy(
            previousRoute = _state.value.currentRoute,
            currentRoute = "high_score",
            isNavigating = true
        )
    }
    
    private fun navigateToPrivacyPolicy() {
        _state.value = _state.value.copy(
            previousRoute = _state.value.currentRoute,
            currentRoute = "privacy_policy",
            isNavigating = true
        )
    }
    
    private fun navigateToGameOver(score: Int) {
        _state.value = _state.value.copy(
            previousRoute = _state.value.currentRoute,
            currentRoute = "game_over/$score",
            isNavigating = true
        )
    }
    
    private fun navigateBack() {
        val previousRoute = _state.value.previousRoute ?: "main_menu"
        _state.value = _state.value.copy(
            previousRoute = _state.value.currentRoute,
            currentRoute = previousRoute,
            isNavigating = true
        )
    }
    
    private fun exitApp() {
        // Логіка виходу з додатку
    }
    
    fun onNavigationComplete() {
        _state.value = _state.value.copy(isNavigating = false)
    }
} 