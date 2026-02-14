package com.example.morseracket.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.morseracket.ui.controllers.MorseController
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

@Composable
fun MorseTape(
    controller: MorseController,
    modifier: Modifier = Modifier
) {
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(Color.Black)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        controller.onKeyPress()
                        coroutineScope {
                            while (controller.isDrawing) {
                                controller.update()
                                delay(16L)  // ✅ Long!
                                // Очистка старых символов
                                controller.symbols.removeAll {
                                    it.startX + controller.tapeOffset + it.width < -100f
                                }
                            }
                            controller.onKeyRelease()
                        }
                    }
                )
            }
    ) {
        controller.symbols.forEach { symbol ->
            val left = symbol.startX + controller.tapeOffset
            if (left > -500f && left < size.width) {
                drawRect(
                    color = Color.Yellow,
                    topLeft = Offset(left, size.height / 2f - 10f),
                    size = Size(symbol.width.coerceAtLeast(5f), 20f)
                )
            }
        }
    }
}
