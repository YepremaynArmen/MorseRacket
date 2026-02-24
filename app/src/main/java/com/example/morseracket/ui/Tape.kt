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
    private var currentCellIndex = 0
    private fun createCells() {
        var currentCellX = 0f
        var index = 0f
        println("✅ currentCellX: ${currentCellX} width ${width}")
        while (currentCellX < width) {
            index++
            cells.add(Cell(
                x = currentCellX

            ))
            //currentTypeX += Vars.signalWidth
            println("✅index ${index} currentCellX: ${currentCellX} width ${width}")
            currentCellX += Vars.signalWidth
        }
        println("✅ cells: ${cells.size}")
    }

    fun moveLeft(offset: Float) {
        xStart -= offset
        xEnd -= offset
        xCurrent -= offset
        //alignToNearestCell(Vars.FIXED_START_X)
    }

    private fun alignToNearestCell(targetX: Float) {
        var previosCell: Cell? = null
        var nearestCell: Cell? = null
        //var index = 0
        //for (cell in cells) {
        run searchLoop@{
        controller.tape.cells.forEachIndexed { index, cell ->
            /*println("📍 index ${index}  tape xCurrent= ${xCurrent} xStart = ${xStart}  cell.x ${cell.x} " +
                    " controller.tapeOffset ${controller.tapeOffset}  targetX =${targetX} " +
                    "color ${cell.bodyColor.toColorName()} ")*/
            //if (xStart + Vars.signalWidth > targetX){
            println("📍 index ${index} xStart ${xStart}  tape cell.x= ${cell.x} " +
                    "tapeOffset ${controller.tapeOffset} targetX ${targetX} ")
            println("📍 index ${index} if ${xStart + cell.x - controller.tapeOffset - Vars.signalWidth} " +
                    "> targetX ${targetX} ")
            if (xStart + cell.x - controller.tapeOffset - Vars.signalWidth > targetX){
                if (cell.bodyColor == cell.spaceColor) { //continue
                    nearestCell = cell
                    previosCell = controller.tape.cells[index - 1]
                    //return@forEachIndexed
                    return@searchLoop
                }
                //break
            }
            }
            //index++
        }
        if (previosCell != null){
            println("📍 previosCell cell.x ${previosCell.x} targetX ${targetX}" +
                    " xStart ${xStart} tapeOffset ${controller.tapeOffset}")
        }

        nearestCell ?: return
        val diff = nearestCell.x + controller.tapeOffset
        //controller.tapeOffset -= diff
        xStart -= diff
        println("📍 nearestCell cell.x ${nearestCell.x} targetX ${targetX}" +
                " diff ${diff} xStart ${xStart} tapeOffset ${controller.tapeOffset}")

    }

}

