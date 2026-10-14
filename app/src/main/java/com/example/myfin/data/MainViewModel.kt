package com.example.myfin.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfin.data.repositoryes.UserPreferencesRepository.PreferencesKeys
import com.example.myfin.data.repositoryes.CategoryRepository
import com.example.myfin.data.repositoryes.UserPreferencesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    val userSettings = userPreferencesRepository.userSettingsFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UserSettings(
                themeMode = "system",
                authToken = null
            )
        )

    val categories = categoryRepository.categories
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    /**
     * Установка режима темы:
     *
     * "light"  — светлая
     * "dark"   — тёмная
     * "system" — системная
     */
    fun setThemeMode(mode: String) {
        viewModelScope.launch {
            userPreferencesRepository.update { preferences ->
                preferences[PreferencesKeys.THEME_MODE] = mode
            }
        }
    }

    fun login(token: String) {
        viewModelScope.launch {
            userPreferencesRepository.update { preferences ->
                preferences[PreferencesKeys.AUTH_TOKEN] = token

                // В будущем сюда можно добавить:
                // preferences[PreferencesKeys.USER_NAME] = name
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            userPreferencesRepository.update { preferences ->
                preferences.remove(PreferencesKeys.AUTH_TOKEN)

                // В будущем:
                // preferences.remove(PreferencesKeys.USER_NAME)
            }
        }
    }
}