package com.example.morseracket.ui


import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import com.example.morseracket.ui.Cell

@Stable
class Tape(
    var xStart: Float = 355f,//Vars.FIXED_START_X - Vars.signalWidth,
    var xEnd: Float = xStart + 800f,
    var xCurrent: Float = xStart,
    var width: Float = 800f,
    var height: Float = 90f,
    val cellHeight: Float = 30f,
    val color: Color = Color.Yellow,
    val cells: MutableList<Cell> = mutableListOf()
) {

    init {
        width = xEnd - xStart
        createCells()  // ✅ Создаем разметку при инициализации!
    }

    private fun createCells() {
        var currentTypeX = xStart
        var currentCellX = 0f

        while (currentTypeX < xEnd) {
            cells.add(Cell(
                x = currentCellX
                //width = cellWidth,
                //height = cellHeight,
                //bodyColor = Color(0xFFD4AF37),
                //borderColor = Color.Black
            ))
            currentTypeX += Vars.signalWidth
            currentCellX += Vars.signalWidth
        }
    }

    fun moveLeft(offset: Float) {
        xStart -= offset
        xEnd -= offset
        xCurrent -= offset
        //xCurrent -= offset
        // cells двигаются с Tape автоматически!
    }
}

