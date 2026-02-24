package com.example.morseracket.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.drawText
import com.example.morseracket.ui.Vars
import com.example.morseracket.ui.controllers.MorseController

import androidx.compose.ui.text.TextStyle

import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.morseracket.ui.cards.Decoder

@Composable
fun MorseDecodeCanvas(controller: MorseController, modifier: Modifier = Modifier) {
    val textMeasurer = rememberTextMeasurer()
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Color(0xFF1A1A1A))
    ) {
        Decoder.decodeTape(controller)  // ✅ Только вызов — очистка внутри!

        var currentX = 20f
        val symbolSpacing = 10f
        val screenWidth = size.width
        val lineWidth = 2f

        Decoder.decodedLetters.forEachIndexed { index, letter ->
            if (currentX > screenWidth - 60f) {
                currentX = 20f  // очистка экрана
            }

            currentX += symbolSpacing
            drawText(
                textMeasurer = textMeasurer,
                text = AnnotatedString(letter.text),
                topLeft = Offset(currentX, 8f),
                style = TextStyle(fontSize = 18.sp, color = Color.White)
            )
            currentX += 25f + symbolSpacing

            if (index < Decoder.decodedLetters.lastIndex) {
                val lineX = currentX
                drawRect(
                    color = Color.LightGray,
                    topLeft = Offset(lineX, 0f),
                    size = Size(lineWidth, size.height)
                )
                currentX += 5f
            }
        }
    }
}






/*
@Composable
fun MorseDecodeCanvas(controller: MorseController, modifier: Modifier = Modifier) {
    val textMeasurer = rememberTextMeasurer()
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)  // Только высота расшифровки
            .background(Color(0xFF1A1A1A))
    ) {
        val decodeLeft = Vars.FIXED_START_X + controller.tapeOffset
        val decodeText = getDecoder(controller)

        drawText(
            textMeasurer = textMeasurer,
            text = decodeText,
            topLeft = Offset(decodeLeft + 10f, 28f),
            style = TextStyle(fontSize = 20.sp, color = Color.White)
        )
    }
}*/