package com.example.morseracket.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.drawText
import com.example.morseracket.ui.Vars
import com.example.morseracket.ui.controllers.MorseController

import androidx.compose.ui.text.TextStyle

import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.morseracket.ui.cards.Decoder.getDecoder

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
}