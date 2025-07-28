package com.example.funnycombination.domain.model

sealed class GameState {
    object ShowingSequence : GameState()
    object WaitingForInput : GameState()
    object GameOver : GameState()
} 