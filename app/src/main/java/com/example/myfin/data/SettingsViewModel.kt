package com.example.myfin.data

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import com.example.myfin.data.UserPreferencesRepository.PreferencesKeys // Импортируем ключи
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first

class MainViewModel(
    private val repository: UserPreferencesRepository
) : ViewModel() {

    // Подписка на весь объект настроек
    val userSettings: StateFlow<UserSettings> = repository.userSettingsFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UserSettings(isDarkMode = false, authToken = null)
        )

    // Изменение одного поля (Тема)
    fun toggleDarkMode(enabled: Boolean) {
        viewModelScope.launch {
            repository.update { preferences ->
                preferences[PreferencesKeys.IS_DARK_MODE] = enabled
            }
        }
    }

    // Изменение поля (Токен).
    // ➕ ЕСЛИ ДОБАВЯТСЯ ПОЛЯ (например имя и email при логине), вы просто допишете их внутрь этого ЖЕ блока:
    fun login(token: String) {
        viewModelScope.launch {
            repository.update { preferences ->
                preferences[PreferencesKeys.AUTH_TOKEN] = token
                // preferences[PreferencesKeys.USER_NAME] = name  <- Вот так, в одну транзакцию!
            }
        }
    }

    // Сброс данных при выходе
    fun logout() {
        viewModelScope.launch {
            repository.update { preferences ->
                preferences.remove(PreferencesKeys.AUTH_TOKEN)
                // ➕ ЕСЛИ ДОБАВЯТСЯ ПОЛЯ: Удаляйте их здесь же при выходе:
                // preferences.remove(PreferencesKeys.USER_NAME)
            }
        }
    }
}
