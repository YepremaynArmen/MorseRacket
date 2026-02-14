package com.example.morseracket.ui.controllers

import com.example.morseracket.data.MorseData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LetterController {
    private val _currentLetterIndex = MutableStateFlow(0)
    val currentLetterIndex: StateFlow<Int> = _currentLetterIndex.asStateFlow()

    private val _currentLetter = MutableStateFlow<Pair<String, String>?>(null)
    val currentLetter: StateFlow<Pair<String, String>?> = _currentLetter.asStateFlow()

    fun updateLanguage(isRussian: Boolean) {
        val letters = if (isRussian) MorseData.RUSSIAN_LETTERS else MorseData.LATIN_LETTERS
        val index = _currentLetterIndex.value.coerceAtMost(letters.size - 1)
        _currentLetterIndex.value = index
        _currentLetter.value = letters.getOrNull(index)
    }

    fun nextLetter(isRussian: Boolean) {
        val letters = if (isRussian) MorseData.RUSSIAN_LETTERS else MorseData.LATIN_LETTERS
        val maxIndex = letters.size - 1
        _currentLetterIndex.value = (_currentLetterIndex.value + 1).coerceAtMost(maxIndex)
        _currentLetter.value = letters.getOrNull(_currentLetterIndex.value)
    }

    fun prevLetter(isRussian: Boolean) {
        val letters = if (isRussian) MorseData.RUSSIAN_LETTERS else MorseData.LATIN_LETTERS
        _currentLetterIndex.value = (_currentLetterIndex.value - 1).coerceAtLeast(0)
        _currentLetter.value = letters.getOrNull(_currentLetterIndex.value)
    }
}
