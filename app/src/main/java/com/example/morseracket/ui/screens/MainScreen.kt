package com.example.morseracket.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController

@Composable
fun MainScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("MorseRacket", fontSize = 48.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(64.dp))

        Button(
            onClick = { navController.navigate("learnLetters") },
            modifier = Modifier.fillMaxWidth().height(72.dp)
        ) {
            Text("📝 Изучать буквы", fontSize = 24.sp)
        }
        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = { navController.navigate("tapText") },
            modifier = Modifier.fillMaxWidth().height(72.dp)) {
            Text("🔤 Текст → Морзе", fontSize = 24.sp)
        }
        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = { navController.navigate("talks") },
            modifier = Modifier.fillMaxWidth().height(72.dp)) {
            Text("📡 Общение", fontSize = 24.sp)
        }
    }
}