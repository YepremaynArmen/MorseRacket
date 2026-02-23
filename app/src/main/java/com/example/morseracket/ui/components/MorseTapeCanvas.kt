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
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.text.drawText
import com.example.morseracket.ui.Vars
import com.example.morseracket.ui.controllers.MorseController

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.sp

@Composable
fun MorseTapeCanvas(
    controller: MorseController,
    modifier: Modifier = Modifier
) {
    val textMeasurer = rememberTextMeasurer()
    Canvas(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF2F1B14).copy(alpha = 0.2f))  // ✅ ФОН бобины!
    ) {
        // ✅ ОБРЕЗАЕМ ВСЕ рисование пределами бобины!
        clipRect(
            left = 0f,
            top = 0f,
            right = size.width,
            bottom = size.height

        ) {
            val centerY = size.height / 2f
            val tapeLeft = Vars.FIXED_START_X + controller.tapeOffset
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
            controller.tape.cells.forEach { cell ->
                // ФИКСИРОВАННАЯ позиция (без сдвига)
                val x = cell.x
                val cellIndex = controller.tape.cells.indexOf(cell)
                if (x > -size.width && x < controller.tape.width)  {
                    val centerY = size.height / 2f - 20f
                    drawRect(
                        color = cell.bodyColor,
                        topLeft = Offset(tapeLeft + cell.x + 2f, centerY + 2f),
                        size = Size(cell.width, cell.height)
                    )
                    // Номер ячейки
                    drawText(
                        textMeasurer = textMeasurer,  // ← Нужен TextMeasurer!
                        text = "$cellIndex",
                        topLeft = Offset(
                            tapeLeft + cell.x + 8f,  // Центр ячейки по X
                            centerY + 8f             // Центр ячейки по Y
                        ),
                        style = TextStyle(
                            fontSize = 12.sp,
                            color = Color.Black
                        )
                    )

                }
            }
        }
    }
}