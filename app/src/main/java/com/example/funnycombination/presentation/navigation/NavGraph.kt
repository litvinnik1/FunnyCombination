package com.example.funnycombination.presentation.navigation

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import android.app.Activity
import com.example.funnycombination.presentation.screens.*
import com.example.funnycombination.presentation.viewmodels.*
import com.example.funnycombination.domain.model.GameState
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object NavRoutes {
    const val SPLASH = "splash"
    const val MAIN_MENU = "main_menu"
    const val GAME = "game"
    const val GAME_OVER = "game_over/{score}"
    const val HIGH_SCORE = "high_score"
    const val PRIVACY_POLICY = "privacy_policy"
}

@Composable
fun AppNavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.SPLASH,
        modifier = modifier
    ) {
        composable(route = NavRoutes.SPLASH) {
            SplashScreen(onTimeout = {
                navController.navigate(NavRoutes.MAIN_MENU) {
                    popUpTo(NavRoutes.SPLASH) { inclusive = true }
                }
            })
        }
        
        composable(route = NavRoutes.MAIN_MENU) {
            val context = LocalContext.current
            MainMenuScreen(
                onPlay = { 
                    navController.navigate(NavRoutes.GAME) {
                        popUpTo(NavRoutes.MAIN_MENU) { inclusive = false }
                        launchSingleTop = true
                    }
                },
                onHighScore = { 
                    navController.navigate(NavRoutes.HIGH_SCORE) {
                        launchSingleTop = true
                    }
                },
                onPrivacyPolicy = { 
                    navController.navigate(NavRoutes.PRIVACY_POLICY) {
                        launchSingleTop = true
                    }
                },
                onExit = {
                    (context as? Activity)?.finish()
                }
            )
        }
        
        composable(route = NavRoutes.GAME) {
            val gameViewModel: GameViewModel = hiltViewModel()
            val gameState by gameViewModel.state.collectAsState()
            
            // Запускаємо гру тільки якщо вона ще не запущена
            LaunchedEffect(Unit) {
                if (gameState.gameState == GameState.ShowingSequence && gameState.sequence.isEmpty()) {
                    gameViewModel.startGame()
                }
            }
            
            val sequence = gameState.sequence
            val userInput = gameState.userInput
            val level = gameState.level
            val currentGameState = gameState.gameState
            var currentDemoIndex by remember { mutableStateOf(-1) }
            var demoEmoji: String? by remember { mutableStateOf(null) }
            var navigateToGameOver by remember { mutableStateOf(false) }
            
            LaunchedEffect(currentGameState, sequence) {
                if (currentGameState is GameState.ShowingSequence && sequence.isNotEmpty()) {
                    currentDemoIndex = -1
                    demoEmoji = null
                    for (i in sequence.indices) {
                        currentDemoIndex = i
                        demoEmoji = sequence[i]
                        delay(1000)
                    }
                    demoEmoji = null
                    gameViewModel.onSequenceShown()
                }
                if (currentGameState is GameState.GameOver) {
                    navigateToGameOver = true
                }
            }
            
            if (navigateToGameOver) {
                LaunchedEffect(Unit) {
                    val score = gameState.level - 1
                    navController.navigate("${NavRoutes.GAME_OVER.replace("{score}", score.toString())}") {
                        popUpTo(NavRoutes.GAME) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            } else {
                GameScreen(
                    demoEmoji = demoEmoji,
                    userInput = userInput,
                    level = level,
                    isInputEnabled = currentGameState is GameState.WaitingForInput,
                    onEmojiClick = { gameViewModel.onEmojiClick(it) }
                )
            }
        }
        
        composable(route = NavRoutes.GAME_OVER) { backStackEntry ->
            val score = backStackEntry.arguments?.getString("score")?.toIntOrNull() ?: 0
            
            val highScoreViewModel: HighScoreViewModel = hiltViewModel()
            val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            var isHighScore by remember { mutableStateOf(false) }
            var isTiedScore by remember { mutableStateOf(false) }
            var isProcessing by remember { mutableStateOf(true) }
            
            LaunchedEffect(score) {
                if (score > 0) {
                    highScoreViewModel.addHighScoreIfBest(date, score) { isBest ->
                        isHighScore = isBest
                        
                        if (!isBest && score > 0) {
                            highScoreViewModel.checkIfTiedScore(score) { isTied ->
                                isTiedScore = isTied
                                isProcessing = false
                            }
                        } else {
                            isProcessing = false
                        }
                    }
                } else {
                    isProcessing = false
                }
            }
            
            if (!isProcessing) {
                GameOverScreen(
                    score = score,
                    isHighScore = isHighScore,
                    isTiedScore = isTiedScore,
                    onMainMenu = {
                        navController.navigate(NavRoutes.MAIN_MENU) {
                            popUpTo(NavRoutes.GAME_OVER) { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                    onPlayAgain = {
                        navController.navigate(NavRoutes.GAME) {
                            popUpTo(NavRoutes.GAME_OVER) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
        
        composable(route = NavRoutes.HIGH_SCORE) {
            val highScoreViewModel: HighScoreViewModel = hiltViewModel()
            val highScores by highScoreViewModel.highScores.collectAsState()
            
            LaunchedEffect(Unit) {
                highScoreViewModel.loadHighScores()
            }
            
            HighScoreScreen(
                highScores = highScores,
                onBack = {
                    navController.popBackStack()
                },
                onClearScores = {
                    highScoreViewModel.clearAllScores()
                }
            )
        }
        
        composable(route = NavRoutes.PRIVACY_POLICY) {
            PrivacyPolicyScreen(onBack = { 
                navController.popBackStack()
            })
        }
    }
} 