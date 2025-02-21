package com.example.guessinggame

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.guessinggame.databinding.FragmentGameBinding

class GameFragment : Fragment() {
    private lateinit var gameViewModel: GameViewModel
    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentGameBinding.inflate(inflater)
        val view = binding.root

        gameViewModel = ViewModelProvider(this)[GameViewModel::class.java]
        updateDisplay()

        binding.checkButton.setOnClickListener {
            gameViewModel.makeGuess(binding.guess.text.toString().uppercase())
            binding.guess.text = null
            updateDisplay()
            if (gameViewModel.isWon() || gameViewModel.isLos()) {
                val action =
                    GameFragmentDirections.actionGameFragmentToResultFragment(gameViewModel.wonLostMessage())
                view.findNavController().navigate(action)
            }
        }

        return view
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    @SuppressLint("SetTextI18n")
    private fun updateDisplay() {
        binding.word.text = gameViewModel.secretWordDisplay
        binding.countLives.text = "У вас осталось ${gameViewModel.countLives} жизней"
        binding.lettersUsed.text = "Использованные буквы: ${gameViewModel.incorrectLetters}"
    }
}