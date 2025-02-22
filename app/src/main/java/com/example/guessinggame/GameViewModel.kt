package com.example.guessinggame

import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    private val secretWords = listOf("Android", "Activity", "Fragment")
    private val secretWord = secretWords.random().uppercase()
    private var correctLetters = ""
    var incorrectLetters = ""
    var countLives = 8
    var secretWordDisplay = ""

    init {
        secretWordDisplay = deriveSecretWord()
    }

    private fun deriveSecretWord(): String {
        var display = ""
        secretWord.forEach { display += checkLetter(it.toString()) }

        return display
    }

    private fun checkLetter(str: String): String = when (correctLetters.contains(str)) {
        true -> str
        else -> "_"
    }

    fun makeGuess(letter: String) {
        if (letter.length == 1) {
            if (secretWord.contains(letter)) {
                correctLetters += letter
                secretWordDisplay = deriveSecretWord()
            } else {
                incorrectLetters += letter
                countLives -= 1
            }
        }
    }

    fun wonLostMessage(): String {
        return if (isWon()) "Вы выиграли. Отгданное слово $secretWord" else "Вы проиграли. Загаданное слово $secretWord"
    }

    fun isWon(): Boolean {
        return secretWord.equals(secretWordDisplay, true)
    }

    fun isLos(): Boolean {
        return countLives <= 0
    }

}