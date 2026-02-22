package com.example.morseracket.ui

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color

@Stable
class Signal {
    var xHead: Float = Vars.FIXED_START_X
    var xTail: Float = Vars.FIXED_START_X
    val width = Vars.signalWidth
    val height = Vars.signalHeight
    val color: Color = Color.Black
    constructor() {
        // Дефолтные значения уже в свойствах!
        // Ничего дополнительного не нужно!
    }
    fun moveLeft(offset: Float) {
        xHead -= offset
        xTail -= offset
    }
}