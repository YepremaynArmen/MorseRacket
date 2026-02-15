package com.example.morseracket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.morseracket.ui.screens.LearnLettersScreen
import com.example.morseracket.ui.screens.MainScreen
import com.example.morseracket.ui.theme.MorseRacketTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MorseRacketTheme {
                MorseRacketApp()
            }
        }
    }
}
@Composable
fun MorseRacketApp() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        //startDestination = "main"
        startDestination = "learnLetters"
    ) {
        composable("main") { MainScreen(navController) }
        composable("learnLetters") { LearnLettersScreen(navController) }

    }
}