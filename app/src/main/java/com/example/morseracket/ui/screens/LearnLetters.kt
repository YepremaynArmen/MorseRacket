package com.example.morseracket.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.morseracket.ui.controllers.LetterController
import com.example.morseracket.ui.controllers.MorseController
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset

@Composable
fun LearnLettersScreen(navController: NavController) {
    var isRussian by remember { mutableStateOf(false) }
    var isKeyPressedLocal by remember { mutableStateOf(false) }

    // ✅ КОНСТАНТЫ для линии Морзе
    val CONTAINER_WIDTH = 280
    val DOT_WIDTH = 25
    val GAP = 0.dp  // расстояние между точками

    val letterController = remember { LetterController() }
    val morseController = remember { MorseController() }

    // ✅ Состояние для НАКОПЛЕНИЯ точек (удержание ключа)
    var dotCount by remember { mutableStateOf(0) }

    val currentLetter by letterController.currentLetter.collectAsState()
    val controller = remember { MorseController() }
    var isKeyPressed by controller::isKeyPressed
    var lineOffset by controller::lineOffset

    LaunchedEffect(isRussian) {
        letterController.updateLanguage(isRussian)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // ... boxTop без изменений ...

        Row(modifier = Modifier.weight(1f)) {
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

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(CONTAINER_WIDTH.dp)
                    .padding(end = 24.dp)
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    currentLetter?.let { letter ->
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = letter.first,
                                fontSize = 48.sp,
                                fontWeight = FontWeight.Black
                            )
                            Spacer(modifier = Modifier.height(24.dp))

                            // ✅ ДИНАМИЧЕСКАЯ ЛИНИЯ МОРЗЕ










// ✅ ДИНАМИЧЕСКАЯ ЛИНИЯ МОРЗЕ БЕЗ ЗАЗОРОВ
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(60.dp)
                                    .background(Color.Gray.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
                            ) {
                                // ✅ ЦЕНТРАЛЬНАЯ ТОЧКА (всегда)
                                Box(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .width(DOT_WIDTH.dp)
                                        .align(Alignment.Center)
                                        .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(0.dp))
                                )

                                // ✅ СПЛОШНАЯ ЛИНИЯ СЛЕВА (каждая точка примыкает вплотную)
                                repeat(dotCount) { index ->
                                    val pixelsPerDot = DOT_WIDTH * LocalDensity.current.density.toInt()
                                    val totalPixels = pixelsPerDot * (index + 1)

                                    // Ограничение: не больше ширины контейнера (280dp)
                                    val clampedPixels = minOf(totalPixels, CONTAINER_WIDTH/2 * LocalDensity.current.density.toInt())

                                    Box(
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .width(DOT_WIDTH.dp)
                                            .offset { IntOffset(x = -clampedPixels, y = 0) }
                                            .align(Alignment.Center)
                                            .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(0.dp))
                                    )
                                }

















                            }
                        }
                    } ?: Text(
                        text = "ПРАВАЯ\nПАНЕЛЬ",
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                // Кнопки переключения букв (без изменений)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    IconButton(onClick = { letterController.prevLetter(isRussian) }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Предыдущая")
                    }
                    IconButton(onClick = { letterController.nextLetter(isRussian) }) {
                        Icon(Icons.Default.ArrowForward, contentDescription = "Следующая")
                    }
                }

                // ✅ КНОПКА КЛЮЧА - растёт линия!
                Box(modifier = Modifier.fillMaxWidth().height(120.dp)) {
                    Image(
                        painter = painterResource(
                            if (isKeyPressedLocal) R.drawable.tapper_down
                            else R.drawable.tapper_up
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(16.dp)
                            .size(100.dp)
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onPress = {
                                        isKeyPressedLocal = true
                                        controller.onKeyPress()

                                        // ✅ УДЕРЖИВАНИЕ = НОВАЯ ТОЧКА!
                                        dotCount++

                                        tryAwaitRelease()
                                        isKeyPressedLocal = false
                                        controller.onKeyRelease()
                                    }
                                )
                            }
                    )
                }
            }
        }

        // boxBottom (без изменений)
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
