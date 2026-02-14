package com.example.morseracket.ui.controllers

import androidx.compose.runtime.*

data class Symbol(var startX: Float, var width: Float)

class MorseController {
    var tapeOffset by mutableStateOf(0f)
    var lineOffset by mutableStateOf(0f)      // ← ТВОЁ
    val symbols = mutableStateListOf<Symbol>()
    var isDrawing by mutableStateOf(false)
    var isKeyPressed by mutableStateOf(false)  // ← ТВОЁ

    private var pressStartTime = 0L
    private var currentSymbol: Symbol? = null

    fun onKeyPress() {  // ← ТВОЁ
        isKeyPressed = true
        isDrawing = true
        pressStartTime = System.currentTimeMillis()
        tapeOffset -= 20f
        lineOffset -= 20f
        currentSymbol = Symbol(140f + tapeOffset, 0f)
        symbols.add(currentSymbol!!)
    }

    fun onKeyRelease() {  // ← ТВОЁ
        isKeyPressed = false
        isDrawing = false
        currentSymbol = null
    }

    fun update() {
        currentSymbol?.let {
            it.width = ((System.currentTimeMillis() - pressStartTime) / 50f).coerceAtMost(140f)
        }
    }
}
