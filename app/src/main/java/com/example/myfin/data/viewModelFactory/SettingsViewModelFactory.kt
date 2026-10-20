package com.example.myfin.data.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myfin.data.repositoryes.UserPreferencesRepository
import com.example.myfin.data.viewModels.SettingsViewModel

class SettingsViewModelFactory(
    private val userPreferencesRepository: UserPreferencesRepository
): ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            return SettingsViewModel(
                userPreferencesRepository = userPreferencesRepository
            ) as T
        }

        throw IllegalArgumentException(
            "Unknown ViewModel class: ${modelClass.name}"
        )
    }
}