package com.example.guessinggame

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    private val secretWords = listOf("Android", "Activity", "Fragment")
    private val secretWord = secretWords.random().uppercase()
    private var correctLetters = ""

    private var _secretWordDisplay = MutableLiveData<String>()
    val secretWordDisplay: LiveData<String> get() = _secretWordDisplay

    private var _countLives = MutableLiveData(8)
    val countLives: LiveData<Int> get() = _countLives

    private var _incorrectLetters = MutableLiveData("")
    val incorrectLetters: LiveData<String> get() = _incorrectLetters

    init {
        _secretWordDisplay.value = deriveSecretWord()
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
                _secretWordDisplay.value = deriveSecretWord()
            } else {
                _incorrectLetters.value += letter
                _countLives.value = _countLives.value?.minus(1)
            }
        }
    }

    fun wonLostMessage(): String {
        return if (isWon()) "Вы выиграли. Отгданное слово $secretWord" else "Вы проиграли. Загаданное слово $secretWord"
    }

    fun isWon(): Boolean {
        return secretWord.equals(_secretWordDisplay.value, true)
    }

    fun isLos(): Boolean {
        return (_countLives.value ?: 0) <= 0
    }

}