package com.example.morseracket.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.morseracket.R
import com.example.morseracket.data.MorseData
import com.example.morseracket.ui.cards.MorseCard
import com.example.morseracket.ui.controllers.LetterController
import com.example.morseracket.ui.controllers.MorseController
import androidx.compose.runtime.collectAsState
import com.example.morseracket.ui.components.MorseTape
import com.example.morseracket.ui.controllers.Signal
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.cancellation.CancellationException

@Composable
fun LearnLettersScreen(navController: NavController) {
    var isRussian by remember { mutableStateOf(false) }
    val CONTAINER_WIDTH = 280

    val letterController = remember { LetterController() }
    val controller = remember { MorseController() }
    val coroutineScope = rememberCoroutineScope()
    var repeatJob by remember { mutableStateOf<Job?>(null) }

    val currentLetter by letterController.currentLetter.collectAsState()
    val isKeyPressed by controller::isKeyPressed

    LaunchedEffect(isRussian) {
        letterController.updateLanguage(isRussian)
    }
    LaunchedEffect(Unit) {
        controller.restart()
    }

    // ✅ АНИМАЦИЯ ширины
    LaunchedEffect(controller.isDrawing) {
        while (controller.isDrawing) {
            controller.update()
            delay(16L)
        }
    }
    LaunchedEffect(controller.shouldMoveTape) {
        while (controller.shouldMoveTape) {
            controller.signals.forEach { signal ->
                signal.currentX -= 15f  // ✅ ВСЕ currentX сдвигаются!
            }
            delay(150L)
        }
    }

    // ✅ УДЕРЖАНИЕ КЛАВИШИ - НОВОЕ!
    LaunchedEffect(controller.isKeyPressed) {
        if (controller.isKeyPressed) {
            repeatJob?.cancel()
            repeatJob = coroutineScope.launch {
                delay(250L) // Пауза перед повтором
                while (controller.isKeyPressed) {
                    controller.tapeOffset -= 20f
                    controller.lineOffset -= 20f

                    val newSignal = Signal(startX = 350f, width = 0f, height = 40f)
                    controller.addSignal(newSignal)

                    delay(120L) // Новая полоска каждые 120мс
                }
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
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
                            MorseTape(
                                controller = controller,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(60.dp)
                                    .background(Color.Gray.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
                            )
                        }
                    }
                }

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

                // ✅ КНОПКА с УДЕРЖАНИЕМ
                Box(modifier = Modifier.fillMaxWidth().height(120.dp)) {
                    Text(
                        text = "🔄",
                        modifier = Modifier
                            .padding(16.dp)
                            .background(Color.LightGray)
                            .clickable { controller.restart() }
                    )
                    Image(
                        painter = painterResource(
                            if (isKeyPressed) R.drawable.tapper_down
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
                                        controller.onKeyPress()
                                        repeatJob?.cancel()

                                        // ✅ Ждем отпускания БЕЗ onRelease
                                        try {
                                            tryAwaitRelease()  // БЛОКИРУЕТ до отпускания
                                        } catch (e: CancellationException) {
                                            // Отмена жеста
                                        }

                                        controller.onKeyRelease()
                                    }
                                )
                            }

                    )
                }
            }
        }

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
