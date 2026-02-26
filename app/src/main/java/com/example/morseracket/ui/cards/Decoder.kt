package com.example.morseracket.ui.cards

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.example.morseracket.data.MorseData
import com.example.morseracket.ui.Cell
import com.example.morseracket.ui.Vars
import com.example.morseracket.ui.controllers.MorseController

data class DecodedLetter(
    val text: String,      // "A" или ".-"
    val startIndex: Int,   // начало сигнала в ленте
    val endIndex: Int      // конец сигнала
)

object Decoder {
    val letters: MutableList<String> = mutableListOf()
    val decodedLetters: MutableList<DecodedLetter> = mutableListOf()






    fun decodeTape(controller: MorseController, isRussian: Boolean = false) {
        decodedLetters.clear()
        var i = 0
        var currentMorseCode = ""

        while (i < controller.tape.cells.size) {
            // Считаем пробелы
            var spaceCount = 0
            while (i < controller.tape.cells.size &&
                controller.tape.cells[i].bodyColor == controller.tape.cells[i].spaceColor) {
                spaceCount++
                i++
            }

            // ✅ 2+ пробела = конец буквы (более мягкое условие)
            if (spaceCount >= 2 && currentMorseCode.isNotEmpty()) {
                val letter = findLetter(currentMorseCode, isRussian)
                decodedLetters.add(DecodedLetter("$letter $currentMorseCode", i - 15, i))
                //println("✅ БУКВА: $letter $currentMorseCode")
                currentMorseCode = ""
            }

            if (i >= controller.tape.cells.size) break

            val dots = countContinuousDots(controller, i)
            if (dots > 0) {
                val signal = when (dots) {
                    1 -> "·"
                    2 -> "—"
                    else -> "?"
                }
                //if (signal != "?") {
                    currentMorseCode += signal
                //}
                i += dots
            } else {
                i++
            }
        }

        // ✅ Последняя буква в конце ленты
        if (currentMorseCode.isNotEmpty()) {
            val letter = findLetter(currentMorseCode, isRussian)
            decodedLetters.add(DecodedLetter("$letter $currentMorseCode", i - 15, i))
            println("✅ LAST: $letter $currentMorseCode")
        }

       //println("🎯 FINAL decodedLetters: ${decodedLetters.joinToString()}")
    }










    private fun findLetter(morseCode: String, isRussian: Boolean = false): String {
        return if (isRussian) {
            MorseData.RUSSIAN_LETTERS.find { it.second == morseCode }?.first ?: "?"
        } else {
            // ✅ По умолчанию ЛАТИНИЦА
            MorseData.LATIN_LETTERS.find { it.second == morseCode }?.first ?: "?"
        }
    }





    private fun countDotsInBlock(controller: MorseController, startIndex: Int): Int {
        var count = 0
        var i = startIndex
        while (i < controller.tape.cells.size &&
            controller.tape.cells[i].bodyColor == controller.tape.cells[i].dotColor) {
            count++
            i++
        }
        return count
    }


    private fun readSignal(controller: MorseController, startIndex: Int): Unit {
        val dots = countContinuousDots(controller, startIndex)
        val endIndex = startIndex + dots - 1

        when (dots) {
            1 -> decodedLetters.add(DecodedLetter(".", startIndex, endIndex))
            2 -> decodedLetters.add(DecodedLetter("-", startIndex, endIndex))
            else -> decodedLetters.add(DecodedLetter("?", startIndex, endIndex))
        }
    }



    private fun countContinuousDots(controller: MorseController, startIndex: Int): Int {
        var count = 0
        var i = startIndex
        while (i < controller.tape.cells.size &&
            controller.tape.cells[i].bodyColor == controller.tape.cells[i].dotColor) {
            count++
            i++
        }
        return count
    }

    private fun isSpace(cell: Cell): Boolean {
        return cell.bodyColor == cell.spaceColor
    }

    private fun isSymbolSeparator(controller: MorseController, index: Int): Boolean {
        // 2+ пробела подряд = разделитель символов
        if (index + 1 >= controller.tape.cells.size) return false
        return isSpace(controller.tape.cells[index]) &&
                isSpace(controller.tape.cells[index + 1])
    }

    fun getDecoder(controller: MorseController): String {
        decodeTape(controller)
        println("DEBUG: decodedLetters.size = ${Decoder.decodedLetters.size}")  // ← ДОБАВЬ!
        println("DEBUG: letters = ${Decoder.decodedLetters}")
        return Decoder.decodedLetters.joinToString(" ")
    }

}