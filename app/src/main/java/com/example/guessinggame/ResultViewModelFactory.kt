@file:Suppress("UNCHECKED_CAST")

package com.example.guessinggame

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ResultViewModelFactory(val result: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ResultViewModel::class.java))
            return ResultViewModel(result) as T

        throw IllegalArgumentException("Неизвестная модель представления")
    }
}