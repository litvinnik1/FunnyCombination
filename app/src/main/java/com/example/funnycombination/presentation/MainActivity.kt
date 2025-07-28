package com.example.funnycombination.presentation

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.funnycombination.presentation.screens.SplashScreen
import com.example.funnycombination.presentation.theme.FunnyCombinationTheme
import com.example.funnycombination.presentation.screens.MainMenuScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.funnycombination.presentation.viewmodels.GameViewModel
import com.example.funnycombination.domain.model.GameState
import kotlinx.coroutines.delay
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import com.example.funnycombination.presentation.screens.GameScreen
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import com.example.funnycombination.presentation.screens.GameOverScreen
import com.example.funnycombination.presentation.viewmodels.HighScoreViewModel
import com.example.funnycombination.domain.model.HighScore
import com.example.funnycombination.presentation.screens.HighScoreScreen
import com.example.funnycombination.presentation.screens.PrivacyPolicyScreen
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.runtime.collectAsState
import android.util.Log
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FunnyCombinationTheme {
                val navController = rememberNavController()
                Scaffold(
                    containerColor = androidx.compose.ui.graphics.Color(0xFF0A1428)
                ) { paddingValues ->
                    NavHost(
                        navController = navController,
                        startDestination = "splash",
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        composable("splash") {
                            SplashScreen(onTimeout = {
                                navController.navigate("main_menu") {
                                    popUpTo("splash") { inclusive = true }
                                }
                            })
                        }
                        composable("main_menu") {
                            val context = LocalContext.current
                            MainMenuScreen(
                                onPlay = { navController.navigate("game") },
                                onHighScore = { navController.navigate("high_score") },
                                onPrivacyPolicy = { navController.navigate("privacy_policy") },
                                onExit = {
                                    (context as? Activity)?.finish()
                                }
                            )
                        }
                        composable("game") {
                            val gameViewModel: GameViewModel = hiltViewModel()
                            LaunchedEffect(Unit) {
                                gameViewModel.startGame()
                            }
                            val sequence = gameViewModel.sequence
                            val userInput = gameViewModel.userInput
                            val level = gameViewModel.level
                            val gameState = gameViewModel.gameState
                            var currentDemoIndex by remember { mutableStateOf(-1) }
                            var demoEmoji: String? by remember { mutableStateOf(null) }
                            var navigateToGameOver by remember { mutableStateOf(false) }
                            LaunchedEffect(gameState, sequence) {
                                if (gameState is GameState.ShowingSequence) {
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
                                if (gameState is GameState.GameOver) {
                                    navigateToGameOver = true
                                }
                            }
                            if (navigateToGameOver) {
                                // Переходимо на GameOverScreen
                                LaunchedEffect(Unit) {
                                    val score = gameViewModel.currentScore
                                    Log.d("HighScore", "=== GAME OVER ===")
                                    Log.d("HighScore", "Current level: ${gameViewModel.level}")
                                    Log.d("HighScore", "Calculated score: $score")
                                    Log.d("HighScore", "Game state: ${gameViewModel.gameState}")
                                    Log.d("HighScore", "Navigating to game_over with score: $score")
                                    navController.navigate("game_over/$score") {
                                        popUpTo("game") { inclusive = true }
                                    }
                                }
                            } else {
                                GameScreen(
                                    demoEmoji = demoEmoji,
                                    userInput = userInput,
                                    level = level,
                                    isInputEnabled = gameState is GameState.WaitingForInput,
                                    onEmojiClick = { gameViewModel.onEmojiClick(it) }
                                )
                            }
                        }
                        composable("game_over/{score}") { backStackEntry ->
                            val score = backStackEntry.arguments?.getString("score")?.toIntOrNull() ?: 0
                            Log.d("HighScore", "=== GAME OVER SCREEN ===")
                            Log.d("HighScore", "Received score in game_over: $score")
                            Log.d("HighScore", "Score type: ${score::class.java.simpleName}")
                            Log.d("HighScore", "Score > 0: ${score > 0}")
                            
                            val highScoreViewModel: HighScoreViewModel = hiltViewModel()
                            val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                            Log.d("HighScore", "Current date: $date")
                            var isHighScore by remember { mutableStateOf(false) }
                            var isTiedScore by remember { mutableStateOf(false) }
                            var isProcessing by remember { mutableStateOf(true) }
                            
                            LaunchedEffect(score) {
                                Log.d("HighScore", "=== PROCESSING SCORE ===")
                                Log.d("HighScore", "Processing score: $score")
                                if (score > 0) {
                                    Log.d("HighScore", "Score > 0, calling addHighScoreIfBest")
                                    highScoreViewModel.addHighScoreIfBest(date, score) { isBest ->
                                        Log.d("HighScore", "=== CALLBACK RECEIVED ===")
                                        Log.d("HighScore", "Is best score: $isBest")
                                        isHighScore = isBest
                                        
                                        // Перевіряємо, чи це повторення рекорду
                                        if (!isBest && score > 0) {
                                            // Якщо не новий рекорд, перевіряємо чи це повторення
                                            highScoreViewModel.checkIfTiedScore(score) { isTied ->
                                                Log.d("HighScore", "Is tied score: $isTied")
                                                isTiedScore = isTied
                                                isProcessing = false
                                            }
                                        } else {
                                            isProcessing = false
                                        }
                                    }
                                } else {
                                    Log.d("HighScore", "Score is 0 or negative, not saving")
                                    isProcessing = false
                                }
                            }
                            
                            if (!isProcessing) {
                                GameOverScreen(
                                    score = score,
                                    isHighScore = isHighScore,
                                    isTiedScore = isTiedScore,
                                    onMainMenu = {
                                        navController.navigate("main_menu") {
                                            popUpTo("game_over/{score}") { inclusive = true }
                                        }
                                    },
                                    onPlayAgain = {
                                        navController.navigate("game") {
                                            popUpTo("game_over/{score}") { inclusive = true }
                                        }
                                    }
                                )
                            }
                        }
                        composable("high_score") {
                            val highScoreViewModel: HighScoreViewModel = hiltViewModel()
                            val highScores by highScoreViewModel.highScores.collectAsState()
                            LaunchedEffect(Unit) {
                                Log.d("HighScore", "=== HIGH SCORE SCREEN ===")
                                Log.d("HighScore", "Loading high scores...")
                                highScoreViewModel.loadHighScores()
                                Log.d("HighScore", "Loaded ${highScores.size} scores")
                                highScores.forEach { score ->
                                    Log.d("HighScore", "Score: ${score.score}, Date: ${score.date}")
                                }
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
                        composable("privacy_policy") {
                            PrivacyPolicyScreen(onBack = { navController.popBackStack() })
                        }
                    }
                }
            }
        }
    }
} 