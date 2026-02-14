package com.example.morseracket.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.morseracket.R
import com.example.morseracket.data.MorseData

@Composable
fun LearnLettersScreen(navController: NavController) {
    var isRussian by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("🎓 Изучение букв", fontSize = 24.sp)
        Spacer(Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.Center) {
            Text("🇺🇸 Латинский", fontSize = 16.sp)
            Switch(checked = isRussian, onCheckedChange = { isRussian = it })
            Text("🇷🇺 Русский", fontSize = 16.sp)
        }

        // ✅ Row: Буквы СЛЕВА + Ключ СПРАВА
        Row(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                val letters = if (isRussian) MorseData.RUSSIAN_LETTERS else MorseData.LATIN_LETTERS
                items(letters) { letter ->
                    MorseCard(letter.first, letter.second)
                }
            }

            Spacer(Modifier.width(16.dp))

            Image(
                painter = painterResource(R.drawable.tapper_up),
                contentDescription = "Телеграфный ключ",
                modifier = Modifier.size(120.dp)
            )
        }

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
private fun MorseCard(letter: String, morse: String) {
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
            Text(
                text = letter,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = morse,
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
