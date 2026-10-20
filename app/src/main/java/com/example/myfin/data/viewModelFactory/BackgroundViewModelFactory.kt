package com.example.myfin.data.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myfin.data.repositoryes.BackgroundRepository
import com.example.myfin.data.viewModels.BackgroundViewModel

class BackgroundViewModelFactory(
    private val backgroundRepository: BackgroundRepository
): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(BackgroundViewModel::class.java)) {
            return BackgroundViewModel(
                backgroundRepository = backgroundRepository
            ) as T
        }

        throw IllegalArgumentException(
            "Unknown ViewModel class: ${modelClass.name}"
        )
    }
}