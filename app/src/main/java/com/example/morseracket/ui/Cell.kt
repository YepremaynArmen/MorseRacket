package com.example.morseracket.ui

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color

@Stable
data class Cell(
    val x: Float = 0f,                       // позиция на ленте
    val width: Float = Vars.signalWidth,     // 40f
    val height: Float = Vars.signalHeight,//30f,
    var spaceColor: Color = Color.Green, // желты
    var dotColor: Color = Color.Black, // желты
    var bodyColor: Color = spaceColor, // желтый
    //val borderColor: Color = Color.Black,
    val borderWidth: Float = 2f

    /*    // Morse данные ВНУТРИ Cell
        val symbol: Char? = null,                // 'А'
        val symbolColor: Color = Color.Black,
        val morseColor: Color = Color.Black,     // цвет точек/тире
        val isDot: Boolean = true,               // true=точка, false=тире
        val morseX: Float = 0f                   // позиция морзе ЭЛЕМЕНТА в ячейке*/
)
