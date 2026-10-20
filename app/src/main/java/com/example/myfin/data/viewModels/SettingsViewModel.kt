package com.example.myfin.data.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfin.data.UserSettings
import com.example.myfin.data.repositoryes.UserPreferencesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.collections.set

class SettingsViewModel(
    private val userPreferencesRepository: UserPreferencesRepository
): ViewModel() {

    val userSettings = userPreferencesRepository.userSettingsFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UserSettings(
                themeMode = "system",
                autoConverse = false,
                turnedOnPassword = false,
                turnedOnBiometry = false
            )
        )

    fun setThemeMode(mode: String) {
        viewModelScope.launch {
            userPreferencesRepository.update { preferences ->
                preferences[UserPreferencesRepository.PreferencesKeys.THEME_MODE] = mode
            }
        }
    }

    fun setAutoConverse(enabled: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.update { preferences ->
                preferences[UserPreferencesRepository.PreferencesKeys.AUTO_CONVERSE] = enabled
            }
        }
    }

    fun setTurnedOnPassword(enabled: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.update { preferences ->
                preferences[UserPreferencesRepository.PreferencesKeys.TURNED_ON_PASSWORD] = enabled
            }
        }
    }

    fun setTurnedOnBiometry(enabled: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.update { preferences ->
                preferences[UserPreferencesRepository.PreferencesKeys.TURNED_ON_BIOMETRY] = enabled
            }
        }
    }


}