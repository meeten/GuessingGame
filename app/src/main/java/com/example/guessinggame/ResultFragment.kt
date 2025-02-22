package com.example.guessinggame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.guessinggame.databinding.FragmentResultBinding

class ResultFragment : Fragment() {
    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    private lateinit var resultViewModelFactory: ResultViewModelFactory
    private lateinit var resultViewModel: ResultViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater)
        val view = binding.root

        val result = ResultFragmentArgs.fromBundle(requireArguments()).result
        resultViewModelFactory = ResultViewModelFactory(result)
        resultViewModel =
            ViewModelProvider(this, resultViewModelFactory)[ResultViewModel::class.java]

        binding.result.text = resultViewModel.result

        binding.restartButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_resultFragment_to_gameFragment)
        }
        return view
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}