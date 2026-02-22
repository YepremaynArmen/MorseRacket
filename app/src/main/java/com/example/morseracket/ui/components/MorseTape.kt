package com.example.morseracket.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipRect
import com.example.morseracket.ui.Vars
import com.example.morseracket.ui.controllers.MorseController

@Composable
fun MorseTape(
    controller: MorseController,
    modifier: Modifier = Modifier
) {

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF2F1B14).copy(alpha = 0.2f))  // ✅ ФОН бобины!
    ) {
        val centerY = size.height / 2f - 20f

        // ✅ ОБРЕЗАЕМ ВСЕ рисование пределами бобины!
        clipRect(
            left = 0f,
            top = 0f,
            right = size.width,
            bottom = size.height

        ) {
            // 1. ЖЕЛТАЯ ЛЕНТА
            /*var firstOffset = 0f
            if (controller.signals.size == 0)
                firstOffset = Vars.signalWidth*2

            val tapeLeft = Vars.FIXED_START_X + controller.tapeOffset - firstOffset
            val tapeRight = controller.tapeOffset + size.width * 2f
            drawRect(
                color = Color(0xFFD4AF37).copy(alpha = 0.9f),
                topLeft = Offset(tapeLeft, centerY - 25f),
                size = Size(tapeRight - tapeLeft, 90f)
            )*/

            val centerY = size.height / 2f

            // ✅ Рисуем Tape объект из controller.tape
            drawRect(
                color = controller.tape.color,
                topLeft = Offset(
                    x = controller.tape.xStart,
                    y = centerY - controller.tape.height / 2
                ),
                size = Size(
                    width = controller.tape.width,
                    height = controller.tape.height
                )
            )

            // 2. Бронзовая черта
            drawLine(
                color = Color(0xFF8B4513),
                start = Offset(Vars.FIXED_START_X, centerY - 20f),
                end = Offset(Vars.FIXED_START_X, centerY + 20f + Vars.signalHeight),
                strokeWidth = 6f
            )

            // 3. Неровные края
            val path = Path().apply {
                moveTo(Vars.FIXED_START_X + 5f, centerY - 20f)
                for (i in 0..20) {
                    val noise = (kotlin.math.sin(i * 0.5f) * 3f)
                    relativeLineTo(15f, noise)
                }
                lineTo(Vars.FIXED_START_X + 5f, centerY + 20f + Vars.signalHeight)
                close()
            }
            drawPath(path, color = Color(0xFF8B4513).copy(alpha = 0.4f))

            // 4. ЧЕРНЫЕ сигналы

            controller.signals.forEachIndexed { index, signal ->
                // ✅ Сигналы уже в абсолютных координатах!
                val left = signal.xHead
                val width = signal.xTail - signal.xHead

                println("🔍 Signal[$index]: xHead=${"%.1f".format(signal.xHead)}, xTail=${"%.1f".format(signal.xTail)}, width=${"%.1f".format(width)}, left=${"%.1f".format(left)}, tapeOffset=${"%.1f".format(controller.tapeOffset)}")

                //if (left > 0f && left < size.width && width > 0f) {
                    val signalY = size.height / 2f - Vars.signalYOffset
                    drawRect(
                        color = signal.color,
                        //topLeft = Offset(left, signalY),
                        //size = Size(width, Vars.signalHeight)
                        topLeft = Offset(left, signalY),
                        size = Size(width, Vars.signalHeight)
                    )
                //}
            }
        }
    }
}