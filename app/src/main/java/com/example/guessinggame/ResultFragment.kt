package com.example.guessinggame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.guessinggame.databinding.FragmentResultBinding

class ResultFragment : Fragment() {
    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater)
        val view = binding.root

        binding.result.text = ResultFragmentArgs.fromBundle(requireArguments()).result
        binding.restartButton.setOnClickListener{view.findNavController().navigate(R.id.action_resultFragment_to_gameFragment)}

        return view
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}