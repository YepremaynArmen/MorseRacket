package com.example.morseracket.ui.controllers

import com.example.morseracket.data.Signal
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.example.morseracket.ui.Vars


@Stable
class MorseController {
    var lineOffset by mutableFloatStateOf(0f)
    var isDrawing by mutableStateOf(false)

    val signals = mutableStateListOf<Signal>()  // ✅ Заменили symbols → signals
    private var pressStartTime = 0L
    private var currentSignal: Signal? = null  // ✅ Заменили currentSymbol
    var isKeyPressed by mutableStateOf(false)      // ✅ ОБЯЗАТЕЛЬНО mutableStateOf
    var shouldMoveTape by mutableStateOf(false)
    var tapeOffset by mutableStateOf(0f)
    //private val FIXED_START_X = 325f

    var activeSignalIndex by mutableStateOf(-1)  // -1 = не активен


    // 📁 MorseController.kt — добавьте ВНУТРЬ класса MorseController:

    fun initSignals() {
        signals.clear()
        var currentX = Vars.FIXED_START_X

        repeat(1000) { i ->
            val signal = Signal(currentX, Vars.signalWidth, Vars.signalHeight, 20f)  // ✅ Работает!
            if (i % 100 == 0)  signal.width = Vars.signalWidth
            signals.add(signal)
            currentX += Vars.signalWidth
        }
    }

    fun setActiveSignalColor(isActive: Boolean) {
        val centerX = 325f
        signals.forEach { signal ->
            val signalCenter = signal.startX + tapeOffset //+ signal.width / 2f
            //val signalCenter = signal.startX
            if (signalCenter >= centerX - 10f && signalCenter <= centerX + 10f)
            {
                signal.color = Color.Black//if (isActive) Color.Black else Color(0xFFD4AF37)
            }
        }
    }

    private fun addSpace() {
        tapeOffset -= Vars.tapeOffset
    }

    fun onKeyPress() {
        isKeyPressed = true
        shouldMoveTape = true
        isDrawing = true
        pressStartTime = System.currentTimeMillis()
        setActiveSignalColor(true)
    }

    fun onKeyRelease() {
        isKeyPressed = false
        shouldMoveTape = false
        isDrawing = false
        //addSpace()
        //setActiveSignalColor(false)  // ✅ Желтый!
    }

    fun update() {
        currentSignal?.let {
            // ✅ / 200f = 5px за 1 секунду — НОРМАЛЬНО!
            it.width = ((System.currentTimeMillis() - pressStartTime) / 200f).coerceAtMost(140f)
        }
    }
    fun restart() {
        signals.clear()        // ✅ Очищаем старую ленту
        tapeOffset = 0f        // ✅ Сбрасываем позицию
        lineOffset = 0f
        isDrawing = false
        isKeyPressed = false
        shouldMoveTape = false

        initSignals()          // ✅ Создаем новую ленту!
    }

}
