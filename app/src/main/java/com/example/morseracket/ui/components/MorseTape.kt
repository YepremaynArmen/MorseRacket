package com.example.morseracket.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import com.example.morseracket.ui.controllers.MorseController

@Composable
fun MorseTape(
    controller: MorseController,
    modifier: Modifier = Modifier
) {
    Canvas(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF2F1B14).copy(alpha = 0.2f))
    ) {
/*        val vintagePaper = Color(0xFFF5E8C7).copy(alpha = 0.7f)
        val agedYellow = Color(0xFFD4AF37)
        val paperNoise = Color(0xFF8B7355).copy(alpha = 0.15f)*/
        val centerY = size.height / 2f - 20f


        // Бронзовая черта — по центру!
        drawLine(
            color = Color(0xFF8B4513),
            start = Offset(350f, centerY - 20f),
            end = Offset(350f, centerY + 20f + 40f),
            strokeWidth = 6f
        )

        // Неровные края — по центру!
        val path = Path().apply {
            moveTo(355f, centerY - 20f)
            for (i in 0..20) {
                val noise = (kotlin.math.sin(i * 0.5f) * 3f)
                relativeLineTo(15f, noise)
            }
            lineTo(355f, centerY + 20f + 40f)
            close()
        }
        drawPath(path, color = Color(0xFF8B4513).copy(alpha = 0.4f))

        // ✅ ЛЕНТА — ПРЕЖНЯЯ ЯРКОСТЬ + ЧЕРНЫЙ ПРОБОЙ!
// В цикле signals — замените БЛОК полностью:
        controller.signals.forEachIndexed { index, signal ->
            val left = signal.startX + controller.tapeOffset
            println("Signal $index: left=$left, ${if (signal.color == Color.Black) "BLACK" else "YELLOW"}")
            if (left > 0f && left < size.width - 20f) {
                val centerY = size.height / 2f - 10f
// ✅ 1. СНАЧАЛА СИГНАЛ (черный слой)
                drawRect(
                    color = signal.color,  // Color.Black или Color(0xFF1A1A1A)
                    topLeft = Offset(left, centerY),
                    size = Size(signal.width * 1f, signal.height * 1f)
                )

// ✅ 2. ПОТОМ бумага (сверху)
/*                drawRect(
                    color = vintagePaper,
                    topLeft = Offset(left + 1f, centerY + 1f),
                    size = Size(signal.width * 0.85f, signal.height * 0.85f)
                )*/

// ✅ 3. Шум (последним)
/*                drawRect(
                    color = paperNoise,
                    topLeft = Offset(left + 2f, centerY + 2f),
                    size = Size(signal.width * 0.8f, signal.height * 0.8f)
                )*/

            }
        }


        // Пыль
       // drawRect(color = Color.Gray.copy(alpha = 0.05f), topLeft = Offset(0f, 0f), size = size)
    }
}
