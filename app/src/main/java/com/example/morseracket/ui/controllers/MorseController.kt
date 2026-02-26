package com.example.morseracket.ui.controllers


import android.R
import androidx.compose.runtime.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import com.example.morseracket.data.MorseData
import com.example.morseracket.ui.Signal
import com.example.morseracket.ui.Tape
import com.example.morseracket.ui.Vars
import com.example.morseracket.ui.cards.Decoder
import kotlinx.coroutines.delay


@Stable
class MorseController (val letterController: LetterController){
    var lineOffset by mutableFloatStateOf(0f)
    //var isDrawing by mutableStateOf(false)
    var hitCount by mutableIntStateOf(0)  // 🔥 НОВОЕ ПОЛЕ!
        private set
    val signals = mutableStateListOf<Signal>()
    val tape = Tape(
        xEnd = Vars.FIXED_START_X +Vars.typeLength,
        xCurrent = Vars.FIXED_START_X,
        width = Vars.FIXED_START_X +Vars.typeLength,
        height = 90f,
        cellHeight = Vars.signalHeight-10f,
        color = Color.Yellow,
        cells = mutableListOf(),
        previousSignalIndex = 0,
        lastSignalIndex = 0,
        controller = this
    )
    private var pressStartTime = 0L
   // private var currentSignal: Signal? = null  // ✅ Заменили currentSymbol
    var isKeyPressed by mutableStateOf(false)      // ✅ ОБЯЗАТЕЛЬНО mutableStateOf
    var firstKeyPress by mutableStateOf(true)
    // var shouldMoveTape by mutableStateOf(false)
    var tapeOffset by mutableStateOf(0f)
   // var offset by mutableStateOf(0f)
   var spaceCount = 0

    fun emmitSignal() {
        val tapeLeft = Vars.FIXED_START_X + tapeOffset

        for (i in tape.cells.indices) {  // 🔥 С ИНДЕКСОМ!
            val cell = tape.cells[i]
            if (tapeLeft + cell.x >= Vars.FIXED_START_X) {
                cell.bodyColor = cell.dotColor  // Закрашиваем
                tape.previousSignalIndex = tape.lastSignalIndex
                tape.lastSignalIndex = i         // ← Запоминаем!
                println(tape.lastSignalIndex)
                break
            }
        }
    }

    fun onKeyPress() {
        isKeyPressed = true
        spaceCount = 3
    }

    suspend fun onKeyRelease() {
        isKeyPressed = false
        //delay(1000)
/*        val expectedLetter = letterController.currentLetter.value?.first ?: ""
        val userResult = if (Decoder.decodedLetters.isNotEmpty()) {
            Decoder.decodedLetters.last().text.substring(0, 1)  // .last() вместо lastIndex
        } else {
            ""
        }
       // println("DEBUG: userResult='$userResult' expectedLetter='$expectedLetter'")
        var spaceSignalCount = tape.lastSignalIndex - tape.previousSignalIndex
        println(Decoder.decodedLetters + "spaceSignalCount $spaceSignalCount")
        if (spaceSignalCount > 1 && userResult != expectedLetter && userResult.isNotEmpty()) {

            //restart()
            spaceCount = 0
            return
        }

        // 🔥 ✅ УСПЕХ + КОНЕЦ ЛЕНТЫ → СЛЕДУЮЩАЯ БУКВА!
        if (userResult == expectedLetter && isEndOfTape()) {
            letterController.nextLetter(isRussian = false)  // Следующая буква
            //restart()                                       // Сброс ленты
            spaceCount = 0
        }*/
    }
    private fun isEndOfTape(): Boolean {
        //println("tapeOffset $tapeOffset tape.xCurrent  ${tape.xCurrent}  tape.xEnd  ${tape.xEnd}")
        return tape.xEnd <= 0f  // Лента прошла
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
        // 🔥 ПРОВЕРКА ПОПАДАНИЙ при движении ленты
        countMatches()
    }

    private fun countMatches() {
        val expectedLetter = letterController.currentLetter.value?.first ?: return

        // Считаем КОЛИЧЕСТВО букв в decodedLetters, РАВНЫХ целевой
        val currentMatches = Decoder.decodedLetters.count { letter ->
            letter.text.length > 0 && letter.text[0].toString() == expectedLetter
        }

        // Обновляем hitCount только если количество ИЗМЕНИЛОСЬ
        if (currentMatches != hitCount) {
            hitCount = currentMatches
            println("📊 Matches: $hitCount/${Vars.MAX_HITS} (реальные буквы)")
        }

        // 10 совпадений → следующая буква
        if (hitCount >= Vars.MAX_HITS) {
            letterController.nextLetter(false)
            restart()  // Очищает decodedLetters + hitCount
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
        tape.xEnd = Vars.FIXED_START_X +Vars.typeLength
        tape.xCurrent = tape.xStart
        spaceCount = 3
        tape.lastSignalIndex = 0
        tape.cells.forEach { cell ->
                cell.bodyColor = cell.spaceColor
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
