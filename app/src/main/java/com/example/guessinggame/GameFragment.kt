package com.example.guessinggame

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import com.example.guessinggame.databinding.FragmentGameBinding

class GameFragment : Fragment() {
    private lateinit var gameViewModel: GameViewModel
    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentGameBinding.inflate(inflater)
        val view = binding.root

        gameViewModel = ViewModelProvider(this)[GameViewModel::class.java]

        gameViewModel.secretWordDisplay.observe(viewLifecycleOwner) { newValue ->
            binding.word.text = newValue
        }

        gameViewModel.countLives.observe(viewLifecycleOwner) { newValue ->
            binding.countLives.text = "У вас осталось $newValue жизней"
        }

        gameViewModel.incorrectLetters.observe(viewLifecycleOwner) { newValue ->
            binding.lettersUsed.text = "Использованные буквы: $newValue"
        }
        gameViewModel.gameOver.observe(viewLifecycleOwner) { newValue ->
            if (newValue) {
                val action =
                    GameFragmentDirections.actionGameFragmentToResultFragment(gameViewModel.wonLostMessage())
                view.findNavController().navigate(action)
            }
        }

        binding.checkButton.setOnClickListener {
            gameViewModel.makeGuess(binding.guess.text.toString().uppercase())
            binding.guess.text = null
        }

        return view
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}