package com.example.morseracket.ui.controllers


import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.example.morseracket.ui.Signal
import com.example.morseracket.ui.Tape
import com.example.morseracket.ui.Vars


@Stable
class MorseController {
    var lineOffset by mutableFloatStateOf(0f)
    var isDrawing by mutableStateOf(false)

    val signals = mutableStateListOf<Signal>()
    val tape = Tape()
    private var pressStartTime = 0L
   // private var currentSignal: Signal? = null  // ✅ Заменили currentSymbol
    var isKeyPressed by mutableStateOf(false)      // ✅ ОБЯЗАТЕЛЬНО mutableStateOf
    var shouldMoveTape by mutableStateOf(false)
    var tapeOffset by mutableStateOf(0f)
    var offset by mutableStateOf(0f)



    fun onKeyPress() {
        if (tape.xStart + tape.width >= Vars.FIXED_START_X) {
            isKeyPressed = true
            isDrawing = true
            pressStartTime = System.currentTimeMillis()
            val newSignal = Signal()  // xHead=350, xTail=350
            newSignal.xHead = Vars.FIXED_START_X - Vars.signalWidth // ✅ ФИКСИРУЕМ хвост СРАЗУ!
            newSignal.xTail = Vars.FIXED_START_X  // ✅ ФИКСИРУЕМ хвост СРАЗУ!
            signals.add(newSignal)
        }
        moveTape()
    }

    fun onKeyRelease() {
        isKeyPressed = false
        shouldMoveTape = false
        isDrawing = false
/*        if (signals.isNotEmpty()) {
            val signal = signals.last()
            val lastIndex = signals.lastIndex
            //println("🔍 Signal[$lastIndex]: xHead=${"%.1f".format(signal.xHead)}, xTail=${"%.1f".format(signal.xTail)}, width=${"%.1f"
            //    .format(Vars.signalWidth)},  tapeOffset=${"%.1f".format(tapeOffset)}")
            //signal.xHead -= Vars.signalWidth*2//tapeOffset //- Vars.signalWidth
            signal.xTail = Vars.FIXED_START_X//Vars.signalWidth*2//tapeOffset
//            println("🔍 Signal Release[$lastIndex]: xHead=${"%.1f".format(signal.xHead)}, xTail=${"%.1f".format(signal.xTail)}, width=${"%.1f"
//                .format(Vars.signalWidth)},  tapeOffset=${"%.1f".format(tapeOffset)}")
        }*/
        tapeOffset -= Vars.signalWidth/6
        //tape.moveLeft(Vars.signalWidth )
        moveTape()
        //println("Release!")
    }


    fun updateTape() {
        if (isKeyPressed) {
            tapeOffset -= Vars.signalWidth/6
            if (signals.isNotEmpty()) {
                val signal = signals.last()
                signal.xTail = Vars.FIXED_START_X-Vars.signalWidth//Vars.signalWidth*2//tapeOffset
            }
            moveTape()
        }
    }

    fun restart() {
        signals.clear()        // ✅ Очищаем старую ленту
        tapeOffset = 0f        // ✅ Сбрасываем позицию
        lineOffset = 0f
        offset = 0f
        isDrawing = false
        isKeyPressed = false
        shouldMoveTape = false
        tape.xStart = Vars.FIXED_START_X - Vars.signalWidth
    }
    fun moveTape() {
        println("📏 ДО: tape.xStart = ${tape.xStart}")
        tape.moveLeft(Vars.signalWidth )
       //println("📏 ПОСЛЕ: tape.xStart = ${tape.xStart}")  // ← ЭТО ПОКАЖЕТ!
        signals.forEachIndexed { index, signal ->
            signal.moveLeft(Vars.signalWidth )
        }
    }
}
