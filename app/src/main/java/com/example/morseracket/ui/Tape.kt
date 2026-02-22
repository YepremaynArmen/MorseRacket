package com.example.morseracket.ui


import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color

@Stable
class Tape(
    var xStart: Float = Vars.FIXED_START_X - Vars.signalWidth,      // Начало ленты
    var xEnd: Float = Vars.FIXED_START_X + 18000f, // Конец ленты
    var width: Float = 18000f,                     // Ширина
    var height: Float = 90f,                     // Высота
    //var color: Color = Color.Black//Color(0xFFD4AF37).copy(alpha = 0.9f)  // Цвет
    val color: Color = Color.Yellow
) {
    fun moveRight(offset: Float) {
        xStart += offset
        xEnd += offset
    }
    fun moveLeft(offset: Float) {
        xStart -= offset
        xEnd -= offset
    }
}
