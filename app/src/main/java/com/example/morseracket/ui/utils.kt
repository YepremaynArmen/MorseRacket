package com.example.morseracket.ui

import androidx.compose.ui.graphics.Color

fun Color.toColorName(): String {
    return when (this) {
        Color.Black -> "Black"
        Color.White -> "White"
        Color.Red -> "Red"
        Color.Green -> "Green"
        Color.Blue -> "Blue"
        Color.Yellow -> "Yellow"
        Color.Cyan -> "Cyan"
        Color.Magenta -> "Magenta"
        Color.Gray -> "Gray"
        Color.DarkGray -> "DarkGray"
        Color.LightGray -> "LightGray"
        else -> {
            // HEX формат для остальных
            Color.toString()
        }
    }
}