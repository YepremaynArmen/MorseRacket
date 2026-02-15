package com.example.morseracket.ui.controllers

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

@Stable
class Signal {
    var currentX by mutableFloatStateOf(350f)
    var width by mutableFloatStateOf(0f)
    var height by mutableFloatStateOf(40f)        // ✅ НОВАЯ ВЫСОТА!
    var yOffset by mutableFloatStateOf(0f)
    var color by mutableStateOf(Color.Black)

    constructor(startX: Float = 350f, width: Float = 0f, height: Float = 40f) {
        this.currentX = startX
        this.width = width
        this.height = height
    }
}


@Stable
class MorseController {
    var tapeOffset by mutableFloatStateOf(0f)
    var lineOffset by mutableFloatStateOf(0f)
    var isDrawing by mutableStateOf(false)
    var isKeyPressed by mutableStateOf(false)

    val signals = mutableStateListOf<Signal>()  // ✅ Заменили symbols → signals
    private var pressStartTime = 0L
    private var currentSignal: Signal? = null  // ✅ Заменили currentSymbol
    var shouldMoveTape by mutableStateOf(false)
    private val FIXED_START_X = 350f
    private var nextX = FIXED_START_X

    fun addSignal(signal: Signal) {
        signal.currentX = FIXED_START_X
        signals.add(signal)
        //nextX += 20f  // Расстояние между полосками
    }
    suspend fun onKeyPress() {

        isKeyPressed = true
        isDrawing = true
        pressStartTime = System.currentTimeMillis()
        shouldMoveTape = true
        currentSignal = Signal(
            startX = nextX ,
            width = 0f,
            height = 40f
        )
        addSignal(currentSignal!!)
    }

    fun onKeyRelease() {
        isKeyPressed = false
        isDrawing = false
        shouldMoveTape = true
        //nextX = FIXED_START_X

    }

    fun update() {
        currentSignal?.let {
            // ✅ / 200f = 5px за 1 секунду — НОРМАЛЬНО!
            it.width = ((System.currentTimeMillis() - pressStartTime) / 200f).coerceAtMost(140f)
        }
    }


    fun restart() {
        signals.clear()  // ✅ signals вместо symbols
        tapeOffset = 0f
        lineOffset = 0f
        isDrawing = false
        isKeyPressed = false
        shouldMoveTape = false
        nextX = FIXED_START_X
    }
}
