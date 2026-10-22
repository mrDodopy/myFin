package com.example.myfin.data.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myfin.data.repositoryes.OperationRepository
import com.example.myfin.data.viewModels.OperationViewModel

class OperationViewModelFactory(
    private val operationRepository: OperationRepository
): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(OperationViewModel::class.java)) {
            return OperationViewModel(
                operationRepository = operationRepository
            ) as T
        }

        throw IllegalArgumentException(
            "Unknown ViewModel class: ${modelClass.name}"
        )
    }
}