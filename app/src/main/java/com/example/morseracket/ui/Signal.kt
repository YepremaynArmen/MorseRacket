package com.example.morseracket.ui

import androidx.compose.runtime.Stable

@Stable
class Signal {
    var xHead: Float = Vars.FIXED_START_X
    var xTail: Float = Vars.FIXED_START_X

    constructor() {
        // Дефолтные значения уже в свойствах!
        // Ничего дополнительного не нужно!
    }
}