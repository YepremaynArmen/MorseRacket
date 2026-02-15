package com.example.morseracket.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.morseracket.ui.controllers.MorseController
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MorseTape(controller: MorseController, modifier: Modifier = Modifier) {
    Canvas(
        modifier = modifier
            .clipToBounds()  // ✅ ОБРЕЗАЕТ за пределами контейнера!
    ) {
        controller.signals.forEach { signal ->
            val left = signal.startX + controller.tapeOffset

            // ✅ Даже простое условие работает!
            if (left > -size.width && left < size.width * 2f) {
                drawRect(
                    color = signal.color,
                    topLeft = Offset(left, size.height / 2f - signal.height / 2f),
                    size = Size(signal.width.coerceAtLeast(30f), signal.height)
                )
            }
        }
    }
}

