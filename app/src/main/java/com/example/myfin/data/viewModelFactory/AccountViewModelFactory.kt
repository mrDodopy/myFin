package com.example.myfin.data.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myfin.data.repositoryes.AccountRepository
import com.example.myfin.data.viewModels.AccountViewModel

class AccountViewModelFactory(
    private val accountRepository: AccountRepository
): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(AccountViewModel::class.java)) {
            return AccountViewModel(
                accountRepository = accountRepository
            ) as T
        }

        throw IllegalArgumentException(
            "Unknown ViewModel class: ${modelClass.name}"
        )
    }
}