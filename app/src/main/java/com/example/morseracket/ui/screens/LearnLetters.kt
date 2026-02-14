package com.example.morseracket.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.TextUnit
import androidx.navigation.NavController
import com.example.morseracket.data.MorseData

@Composable
fun LearnLettersScreen(navController: NavController) {
    var isRussian by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 1. ФИКСИРОВАННЫЙ ВЕРХ
        Text("🎓 Изучение букв", fontSize = 24.sp)
        Spacer(Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.Center) {
            Text("🇺🇸 Латинский", fontSize = 16.sp)
            Switch(checked = isRussian, onCheckedChange = { isRussian = it })
            Text("🇷🇺 Русский", fontSize = 16.sp)
        }

        // 2. СКРОЛЛ - ВСЕ ПРОСТРАНСТВО ДО КНОПКИ
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            val letters = if (isRussian) MorseData.RUSSIAN_LETTERS else MorseData.LATIN_LETTERS
            items(letters) { letter ->
                MorseCard(letter.first, letter.second, 18.sp)
            }
        }

        Spacer(Modifier.height(24.dp))
        Text("🔑", fontSize = 48.sp)
        Spacer(Modifier.height(16.dp))

        // 3. КНОПКА ВСЕГДА В НИЗУ
        Spacer(Modifier.height(24.dp))
        Button(
            onClick = { navController.navigate("main") { popUpTo("main") { inclusive = true } } },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("🏠 В главное меню", fontSize = 16.sp)
        }
    }
}

@Composable
private fun MorseCard(letter: String, morse: String, fontSize: TextUnit) {
    Card(
        modifier = Modifier
            .widthIn(max = 200.dp)
            .height(48.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Буква слева - обычная
            Text(
                text = letter,
                fontSize = fontSize,
                fontWeight = FontWeight.Bold
            )

            // МОРЗЕ - ЖИРНЫЙ + ЯРКИЙ!
            Text(
                text = morse,
                fontSize = fontSize,
                fontWeight = FontWeight.ExtraBold,  // ← СИЛЬНЕЕ!
                color = MaterialTheme.colorScheme.primary  // ← ЯРКИЙ цвет!
            )
        }
    }
}
