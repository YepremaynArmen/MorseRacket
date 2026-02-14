package com.example.morseracket.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.morseracket.R
import com.example.morseracket.data.MorseData
import com.example.morseracket.ui.cards.MorseCard



@Composable
fun LearnLettersScreen(navController: NavController) {
    var isRussian by remember { mutableStateOf(false) }
    var keyPressed by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // 1. boxTop ПРИЖАТ ВВЕРХ - переключатель
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(24.dp)
        ) {
            Column(
                modifier = Modifier.align(Alignment.TopCenter),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("🎓 Изучение букв", fontSize = 24.sp)
                Spacer(Modifier.height(16.dp))
                Row(horizontalArrangement = Arrangement.Center) {
                    Text("🇺🇸 Латинский", fontSize = 16.sp)
                    Switch(checked = isRussian, onCheckedChange = { isRussian = it })
                    Text("🇷🇺 Русский", fontSize = 16.sp)
                }
            }
        }

        // 2+3. boxLeft + boxRight - основная область
        Row(
            modifier = Modifier.weight(1f)
        ) {
            // boxLeft СЛЕВА - БУКВЫ
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 24.dp, end = 16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                val letters = if (isRussian) MorseData.RUSSIAN_LETTERS else MorseData.LATIN_LETTERS
                items(letters) { letter ->
                    MorseCard(letter.first, letter.second)
                }
            }

            // boxRight СПРАВА
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(220.dp)
                    .padding(end = 24.dp)
            ) {
                // boxText - вся высота сверху
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "ПРАВАЯ\nПАНЕЛЬ",
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                }

                // boxKey ПРИЖАТ ВНИЗ
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                ) {
                    Image(
                        painter = painterResource(
                            if (keyPressed) R.drawable.tapper_down
                            else R.drawable.tapper_up
                        ),
                        contentDescription = "Телеграфный ключ",
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(16.dp)
                            .size(100.dp)
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onPress = {
                                        keyPressed = true
                                        tryAwaitRelease()
                                        keyPressed = false
                                    }
                                )
                            }
                    )
                }
            }
        }

        // 4. boxBottom СНИЗУ - кнопка возврата
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(24.dp)
        ) {
            Button(
                onClick = { navController.navigate("main") { popUpTo("main") { inclusive = true } } },
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                Text("🏠 В главное меню")
            }
        }
    }
}
