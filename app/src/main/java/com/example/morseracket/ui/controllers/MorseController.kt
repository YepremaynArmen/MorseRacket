package com.example.morseracket.ui.controllers


import android.R
import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import com.example.morseracket.ui.Signal
import com.example.morseracket.ui.Tape
import com.example.morseracket.ui.Vars
import kotlinx.coroutines.delay


@Stable
class MorseController {
    var lineOffset by mutableFloatStateOf(0f)
    //var isDrawing by mutableStateOf(false)

    val signals = mutableStateListOf<Signal>()
    val tape = Tape(
        xEnd = Vars.FIXED_START_X +4000f,
        xCurrent = Vars.FIXED_START_X,
        width = Vars.FIXED_START_X +4000f,
        height = 90f,
        cellHeight = Vars.signalHeight-10f,
        color = Color.Yellow,
        cells = mutableListOf(),
        controller = this
    )
    private var pressStartTime = 0L
   // private var currentSignal: Signal? = null  // ✅ Заменили currentSymbol
    var isKeyPressed by mutableStateOf(false)      // ✅ ОБЯЗАТЕЛЬНО mutableStateOf
    var firstKeyPress by mutableStateOf(true)
    // var shouldMoveTape by mutableStateOf(false)
    var tapeOffset by mutableStateOf(0f)
   // var offset by mutableStateOf(0f)
   var spaceCount = 3

    fun emmitSignal() {
        val tapeLeft = Vars.FIXED_START_X + tapeOffset
        for (cell in tape.cells) {
            if (tapeLeft + cell.x  >= Vars.FIXED_START_X){
                cell.bodyColor = Color.Black
                break
            }
        }
    }

    fun onKeyPress() {
        isKeyPressed = true
        spaceCount = 3
    }

    fun onKeyRelease() {
        isKeyPressed = false
        //moveTape(1)
    }

    fun updateTape() {
        if (isKeyPressed) {
            emmitSignal()
        }
        else{
            if (spaceCount > 0 ) {
                spaceCount--
            }
        }
        moveTape(1)

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
        spaceCount = 3
        tape.cells.forEach { cell ->
                cell.bodyColor = cell.defaultColor
            }
    }
    fun moveTape(repeatCount: Int) {
        repeat(repeatCount) {
            val step = Vars.signalWidth
            tapeOffset -= step
            tape.moveLeft(step)
        }
    }
}
