package com.example.myfin.data.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfin.data.entity.OperationEntity
import com.example.myfin.data.repositoryes.OperationRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class OperationViewModel(
    private val operationRepository: OperationRepository
): ViewModel() {

    val operations = operationRepository.operationsFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun createNewOperation(
        operation: OperationEntity
    ){
        viewModelScope.launch {
            operationRepository.addOperation(
                operation
            )
        }
    }

    fun deleteOperation(
        operation: OperationEntity
    ){
        viewModelScope.launch {
            operationRepository.deleteOperation(
                operation
            )
        }
    }
}