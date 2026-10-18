package com.example.myfin.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myfin.data.repositoryes.AccountRepository
import com.example.myfin.data.repositoryes.BackgroundRepository
import com.example.myfin.data.repositoryes.CategoryRepository
import com.example.myfin.data.repositoryes.UserPreferencesRepository

class MainViewModelFactory(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val categoryRepository: CategoryRepository,
    private val accountRepository: AccountRepository,
    private val backgroundRepository: BackgroundRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(
                userPreferencesRepository = userPreferencesRepository,
                categoryRepository = categoryRepository,
                accountRepository = accountRepository,
                backgroundRepository = backgroundRepository
            ) as T
        }

        throw IllegalArgumentException(
            "Unknown ViewModel class: ${modelClass.name}"
        )
    }
}