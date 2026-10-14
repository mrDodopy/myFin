package com.example.myfin.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myfin.data.repositoryes.CategoryRepository
import com.example.myfin.data.repositoryes.UserPreferencesRepository

class MainViewModelFactory(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val categoryRepository: CategoryRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(
                userPreferencesRepository = userPreferencesRepository,
                categoryRepository = categoryRepository
            ) as T
        }

        throw IllegalArgumentException(
            "Unknown ViewModel class: ${modelClass.name}"
        )
    }
}