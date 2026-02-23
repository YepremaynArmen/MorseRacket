package com.example.morseracket.ui.cards

import com.example.morseracket.ui.controllers.MorseController

object Decoder {
    fun getDecoder(controller: MorseController): String {
        // Текущая позиция каретки
        /*val currentIndex = controller.currentCellIndex.coerceAtMost(controller.tape.cells.lastIndex)

        // Последние 10 ячеек от каретки
        val recentCells = controller.tape.cells.slice((currentIndex - 9).coerceAtLeast(0)..currentIndex)

        // Читаем Black ячейки справа налево (последние сигналы)
        val signal = recentCells
            .reversed()
            .takeWhile { it.bodyColor == Color.Black }
            .joinToString("") { "." }

        // Ищем в LATIN_LETTERS
        val match = MorseData.LATIN_LETTERS.find { it.second == signal }
        return match?.first ?: ""*/
        return "Скоро"
    }
}