package com.example.morseracket.ui.controllers


import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.example.morseracket.ui.Signal
import com.example.morseracket.ui.Vars


@Stable
class MorseController {
    var lineOffset by mutableFloatStateOf(0f)
    var isDrawing by mutableStateOf(false)

    val signals = mutableStateListOf<Signal>()  // ✅ Заменили symbols → signals
    private var pressStartTime = 0L
   // private var currentSignal: Signal? = null  // ✅ Заменили currentSymbol
    var isKeyPressed by mutableStateOf(false)      // ✅ ОБЯЗАТЕЛЬНО mutableStateOf
    var shouldMoveTape by mutableStateOf(false)
    var tapeOffset by mutableStateOf(0f)


    //private val FIXED_START_X = 325f

    var activeSignalIndex by mutableStateOf(-1)  // -1 = не активен


    // 📁 MorseController.kt — добавьте ВНУТРЬ класса MorseController:

    fun onKeyPress() {
        isKeyPressed = true
        isDrawing = true
        val newSignal = Signal()  // xHead=350, xTail=350
        newSignal.xTail = Vars.FIXED_START_X  // ✅ ФИКСИРУЕМ хвост СРАЗУ!
        signals.add(newSignal)
    }

    fun onKeyRelease() {
        isKeyPressed = false
        shouldMoveTape = false
        isDrawing = false
        if (signals.isNotEmpty()) {
            val signal = signals.last()
            // ✅ ПЕРЕВОДИМ В АБСОЛЮТНЫЕ КООРДИНАТЫ!
            signal.xHead = signal.xHead + tapeOffset
            signal.xTail = Vars.FIXED_START_X        // Хвост в центре пера
        }
        tapeOffset += Vars.signalWidth * 2f
    }

    fun updateTape() {
        if (!isDrawing) {  // ✅ НЕ трогаем активный сигнал!
            signals.forEach { signal ->
                signal.xHead += Vars.signalOffset
                signal.xTail += Vars.signalOffset
            }
        }
        tapeOffset += Vars.signalOffset

    }

    fun restart() {
        signals.clear()        // ✅ Очищаем старую ленту
        tapeOffset = 0f        // ✅ Сбрасываем позицию
        lineOffset = 0f
        isDrawing = false
        isKeyPressed = false
        shouldMoveTape = false

    }

}
