package com.example.morseracket.ui


import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import com.example.morseracket.ui.Cell
import com.example.morseracket.ui.controllers.MorseController
import kotlin.math.abs

@Stable
class Tape(
    var xStart: Float = Vars.FIXED_START_X,//Vars.FIXED_START_X - Vars.signalWidth,
    var xEnd: Float = xStart + 800f,
    var xCurrent: Float = xStart,
    var width: Float = 800f,
    var height: Float = 90f,
    val cellHeight: Float = 30f,
    val color: Color = Color.Yellow,
    val cells: MutableList<Cell> = mutableListOf(),
    private val controller: MorseController
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
        alignToNearestCell(Vars.FIXED_START_X)
    }

    private fun alignToNearestCell(targetX: Float) {
        var nearestCell: Cell? = null
        var minDistance = Float.MAX_VALUE
        val searchRadius = 100f  // ← НАСТРОЙКА 1: радиус поиска
        //val minCellWidth = 10f   // ← НАСТРОЙКА 2: мин. ширина ячейки
        // Находим ближайший cell к targetX
        for (cell in cells) {
            //if (cell.width < Vars.signalWidth) continue  // Пропускаем мелкие
            //val cellLeft = xStart + cell.x
            //val distance = abs(cellLeft - targetX)

            // Только в радиусе поиска
            //if (distance < searchRadius && distance < minDistance) {
               // minDistance = distance
                //nearestCell = cell
           // }
            println("📍 tape xCurrent= ${xCurrent} xStart = ${xStart}  targetX =${targetX}")
            if (xStart + cell.x >= targetX){

                if (cell.bodyColor != cell.defaultColor) continue
                nearestCell = cell
                println("📍 nearestCell cell.x ${cell.x} targetX ${targetX}")
                break
            }
        }

        nearestCell ?: return  // Нет подходящих ячеек

        // Вычисляем сдвиг чтобы cell.x оказался точно в FIXED_START_X
        //val cellLeft = xStart + nearestCell.x
        //val neededShift = Vars.FIXED_START_X - cellLeft

        // Применяем сдвиг ко ВСЕМ позициям
        //xStart = xStart - Vars.FIXED_START_X
        //xEnd += neededShift
        //xCurrent = Vars.FIXED_START_X
        //println("📍 Подстроили tapeOffset ${controller.tapeOffset} cell ${nearestCell.x} к ${Vars.FIXED_START_X}, сдвиг: $neededShift")
        //controller.tapeOffset = xStart - Vars.FIXED_START_X
        //println("📍 Подстроили tapeOffset ${controller.tapeOffset}")
    }

}

