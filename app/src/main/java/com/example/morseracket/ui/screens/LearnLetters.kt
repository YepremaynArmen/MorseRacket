package com.example.morseracket.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import com.example.morseracket.ui.components.MorseTapeCanvas
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlin.coroutines.cancellation.CancellationException
import com.example.morseracket.ui.Vars
import com.example.morseracket.ui.components.MorseDecodeCanvas

@Composable
fun LearnLettersScreen(navController: NavController) {
    var isRussian by remember { mutableStateOf(false) }
    val CONTAINER_WIDTH = 280

    val letterController = remember { LetterController() }
    val controller = remember { MorseController(letterController) }
    val coroutineScope = rememberCoroutineScope()
    var repeatJob by remember { mutableStateOf<Job?>(null) }

    val currentLetter by letterController.currentLetter.collectAsState()


    LaunchedEffect(isRussian) {
        letterController.updateLanguage(isRussian)
    }


    LaunchedEffect(controller.isKeyPressed, controller.spaceCount) {
        while (controller.isKeyPressed
            || (controller.spaceCount > 0 && controller.spaceCount < 4)) {
            controller.updateTape()
            delay(Vars.moveDelay)
        }
    }



    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.weight(1f)) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),  // Убрали padding(start/end)
                verticalArrangement = Arrangement.spacedBy(8.dp)
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
            )

            {

                // ✅ БОБИНА ТЕЛЕГРАФНОЙ ЛЕНТЫ 📜 (60dp высота)
                Box(modifier = Modifier.weight(1f)) {
                    currentLetter?.let { letter ->
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // 🔥 ТАБЛО ПОПАДАНИЙ
                            Box(
                                modifier = Modifier
                                    .background(Color.Black.copy(alpha = 0.8f), RoundedCornerShape(12.dp))
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                            ) {
                                Text(
                                    text = "${controller.hitCount}/${Vars.MAX_HITS}",
                                    fontSize = 20.sp,
                                    color = if (controller.hitCount >= Vars.MAX_HITS) Color.Green else Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            // Целевая буква
                            Text(
                                text = letter.first,
                                fontSize = 48.sp,
                                fontWeight = FontWeight.Black
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            // Морская лента
                            MorseTapeCanvas(
                                controller = controller,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(60.dp)
                                    .background(Color.Gray.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
                            )

                            Spacer(modifier = Modifier.height(10.dp))

                            // Декодировка
                            MorseDecodeCanvas(
                                controller = controller,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)
                                    .background(Color(0xFF1A1A1A), RoundedCornerShape(8.dp))
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
                    Column {
                        Text(
                            text = "🔄",
                            modifier = Modifier
                                .padding(16.dp)
                                .background(Color.LightGray)
                                .clickable { controller.restart() }
                        )

                        Text(
                            text = "лента",
                            modifier = Modifier
                                .padding(16.dp)
                                .background(Color.LightGray)
                                .clickable { controller.moveTape(1) }
                        )
                    }

                    Image(
                        painter = painterResource(
                            if (controller.isKeyPressed) R.drawable.tapper_down
                            else R.drawable.tapper_up
                        ),
                        contentDescription = "Ключ Морзе",
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(16.dp)
                            .size(100.dp)
                            .graphicsLayer(alpha = 0.92f)  // 🔥 Белый фон исчезает!
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onPress = {
                                        controller.onKeyPress()
                                        tryAwaitRelease()
                                        controller.onKeyRelease()
                                    }
                                )
                            },
                        contentScale = ContentScale.Fit  // Сохраняет пропорции + прозрачность
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
