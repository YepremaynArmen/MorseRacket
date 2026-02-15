package com.example.morseracket.ui.controllers

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.time.delay

@Stable
class Signal {
    var startX by mutableFloatStateOf(-1000f)
    var width by mutableFloatStateOf(0f)
    var height by mutableFloatStateOf(40f)        // ✅ НОВАЯ ВЫСОТА!
    var yOffset by mutableFloatStateOf(0f)
    var color by mutableStateOf(Color.Black)

    constructor(startX: Float = -1000f, width: Float = 0f, height: Float = 40f) {
        this.startX = startX
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

    private var nextX = 350f

    fun addSignal(signal: Signal) {
        signal.startX = nextX  // ✅ Каждая новая на новой позиции
        signals.add(signal)
        nextX += 20f  // Расстояние между полосками
    }
    suspend fun onKeyPress() {
        isKeyPressed = true
        isDrawing = true
        pressStartTime = System.currentTimeMillis()
        tapeOffset -= 20f
        lineOffset -= 20f

        currentSignal = Signal(
            startX = 350f ,
            width = 0f,
            height = 40f
        )
        addSignal(currentSignal!!)


/*        while (isKeyPressed) {
            tapeOffset -= 20f
            lineOffset -= 20f
            currentSignal = Signal(startX = 350f, width = 0f, height = 40f)
            addSignal(currentSignal!!)
            delay(120L)
       }*/
    }

    fun onKeyRelease() {
        isKeyPressed = false
        isDrawing = false
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
        nextX = 350f
    }
}
