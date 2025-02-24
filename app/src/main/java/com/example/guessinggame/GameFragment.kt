package com.example.guessinggame

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.guessinggame.databinding.FragmentGameBinding

class GameFragment : Fragment() {
    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!

    private val secretWords = listOf("Android", "Activity", "Fragment")
    private val secretWord = secretWords.random().uppercase()
    private var secretWordDisplay = ""
    private var correctLetters = ""
    private var incorrectLetters = ""
    private var countLives = 8

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentGameBinding.inflate(inflater)
        val view = binding.root

        secretWordDisplay = deriveSecretWord()
        updateDisplay()

        binding.checkButton.setOnClickListener{
            makeGuess(binding.guess.text.toString().uppercase())
            binding.guess.text = null
            updateDisplay()
            if (isWon() || isLos()){
                val action = GameFragmentDirections.actionGameFragmentToResultFragment(wonLostMessage())
                view.findNavController().navigate(action)
            }
        }

        return view
    }

    private fun wonLostMessage():String {
        return if(isWon()) "Вы выиграли. Отгданное слово $secretWord" else "Вы проиграли. Загаданное слово $secretWord"
    }

    private fun isWon(): Boolean {
        return secretWord.equals(secretWordDisplay, true)
    }

    private fun isLos(): Boolean {
        return countLives <= 0
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun deriveSecretWord(): String {
        var display = ""
        secretWord.forEach { display += checkLetter(it.toString()) }

        return display
    }

    private fun checkLetter(str: String): String { return when (correctLetters.contains(str)) { true -> str else -> "_" } }

    @SuppressLint("SetTextI18n")
    private fun updateDisplay() {
        binding.word.text = secretWordDisplay
        binding.countLives.text = "У вас осталось $countLives жизней"
        binding.lettersUsed.text = "Использованные буквы: $incorrectLetters"
    }

    private fun makeGuess(letter: String) {
        if(letter.length == 1){
            if (secretWord.contains(letter)){
                correctLetters += letter
                secretWordDisplay = deriveSecretWord()
            } else{
                incorrectLetters += " $letter"
                countLives -= 1
            }
        }
    }
}