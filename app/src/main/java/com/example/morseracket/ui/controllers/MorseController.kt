package com.example.morseracket.ui.controllers


import android.R
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.example.morseracket.ui.Signal
import com.example.morseracket.ui.Tape
import com.example.morseracket.ui.Vars


@Stable
class MorseController {
    var lineOffset by mutableFloatStateOf(0f)
    //var isDrawing by mutableStateOf(false)

    val signals = mutableStateListOf<Signal>()
    val tape = Tape(
        xEnd = Vars.FIXED_START_X +800f,
        xCurrent = Vars.FIXED_START_X,
        width = 800f,
        height = 90f,
        cellHeight = Vars.signalHeight-10f,
        color = Color.Yellow,
        cells = mutableListOf(),
        controller = this
    )
    private var pressStartTime = 0L
   // private var currentSignal: Signal? = null  // ✅ Заменили currentSymbol
    var isKeyPressed by mutableStateOf(false)      // ✅ ОБЯЗАТЕЛЬНО mutableStateOf
   // var shouldMoveTape by mutableStateOf(false)
    var tapeOffset by mutableStateOf(0f)
   // var offset by mutableStateOf(0f)


    fun emmitSignal() {
        val tapeLeft = Vars.FIXED_START_X + tapeOffset
        tape.cells.forEach { cell ->
            if (tapeLeft + cell.x + cell.width >= Vars.FIXED_START_X
                && tapeLeft + cell.x - cell.width < Vars.FIXED_START_X) {
                cell.bodyColor = Color.Black
            }
        }
    }

    fun onKeyPress() {
        isKeyPressed = true
        moveTape(1)
        emmitSignal()
    }

    fun onKeyRelease() {
        isKeyPressed = false
        //shouldMoveTape = false
        //isDrawing = false
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
        //tapeOffset -= Vars.signalWidth
        //tape.moveLeft(Vars.signalWidth )
        moveTape(2)
        //println("Release!")
    }


    fun updateTape() {
        if (isKeyPressed) {
            //tapeOffset -= Vars.signalWidth//6
/*            if (signals.isNotEmpty()) {
                val signal = signals.last()
                signal.xTail = Vars.FIXED_START_X-Vars.signalWidth//Vars.signalWidth*2//tapeOffset
            }*/
            moveTape(1)
            emmitSignal()
        }
    }

    fun restart() {
        signals.clear()        // ✅ Очищаем старую ленту
        tapeOffset = 0f        // ✅ Сбрасываем позицию
        lineOffset = 0f
       // offset = 0f
       // isDrawing = false
        isKeyPressed = false
        //shouldMoveTape = false
        tape.xStart = Vars.FIXED_START_X //- Vars.signalWidth
        tape.xCurrent = tape.xStart
        tape.cells.forEach { cell ->
                cell.bodyColor = cell.defaultColor
            }
    }
    fun moveTape(repeatCount: Int) {
        repeat(repeatCount) {
            tapeOffset -= Vars.signalWidth
            tape.moveLeft(Vars.signalWidth)
        }
    }
}
