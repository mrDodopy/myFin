package com.example.myfin.data

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfin.data.entity.AccountEntity
import com.example.myfin.data.entity.BackgroundConfigEntity
import com.example.myfin.data.repositoryes.AccountRepository
import com.example.myfin.data.repositoryes.BackgroundRepository
import com.example.myfin.data.repositoryes.CategoryRepository
import com.example.myfin.data.repositoryes.UserPreferencesRepository
import com.example.myfin.data.repositoryes.UserPreferencesRepository.PreferencesKeys
import com.example.myfin.utils.converseIconToString
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val categoryRepository: CategoryRepository,
    private val accountRepository: AccountRepository,
    private val backgroundRepository: BackgroundRepository
) : ViewModel() {

    private val backgroundConfigUpdateMutex = Mutex()

    init {
        viewModelScope.launch {
            backgroundRepository.ensureDefaultConfig()
        }
    }

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

    val categories = categoryRepository.categoriesFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val accounts = accountRepository.accountsFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    /**
     * Текущая конфигурация внешнего вида приложения.
     *
     * В ней находятся:
     * - цвета LiveBackground
     * - primaryColor — основной цвет приложения
     * - mainContainerColor — цвет контейнеров
     * - textColor — основной цвет текста
     */
    val backgroundConfig = backgroundRepository.backgroundConfigFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = BackgroundConfigEntity()
        )

    fun setThemeMode(mode: String) {
        viewModelScope.launch {
            userPreferencesRepository.update { preferences ->
                preferences[PreferencesKeys.THEME_MODE] = mode
            }
        }
    }

    fun setAutoConverse(enabled: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.update { preferences ->
                preferences[PreferencesKeys.AUTO_CONVERSE] = enabled
            }
        }
    }

    fun setTurnedOnPassword(enabled: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.update { preferences ->
                preferences[PreferencesKeys.TURNED_ON_PASSWORD] = enabled
            }
        }
    }

    fun setTurnedOnBiometry(enabled: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.update { preferences ->
                preferences[PreferencesKeys.TURNED_ON_BIOMETRY] = enabled
            }
        }
    }

    fun createNewCategory(
        name: String,
        color: Color,
        icon: ImageVector
    ) {
        viewModelScope.launch {
            categoryRepository.addCategory(
                name = name,
                color = color.toArgb(),
                icon = converseIconToString(icon)
            )
        }
    }

    fun createNewAccount(
        name: String,
        funds: Float,
        currency: String,
        color: Int,
        icon: ImageVector
    ) {
        viewModelScope.launch {
            accountRepository.addAccount(
                name = name,
                funds = funds,
                currency = currency,
                color = color,
                icon = converseIconToString(icon)
            )
        }
    }

    /** Updates one or more appearance fields without overwriting newer changes. */
    fun updateBackgroundConfig(
        transform: (BackgroundConfigEntity) -> BackgroundConfigEntity
    ) {
        viewModelScope.launch {
            backgroundConfigUpdateMutex.lock()
            try {
                backgroundRepository.updateBackgroundConfig(transform)
            } finally {
                backgroundConfigUpdateMutex.unlock()
            }
        }
    }

    fun setBackgroundColor(color: Color) {
        updateBackgroundConfig { it.copy(backgroundColor = color.toArgb()) }
    }

    fun setMainBackgroundColor(color: Color) {
        updateBackgroundConfig { it.copy(mainBackgroundColor = color.toArgb()) }
    }

    fun setSecondaryBackgroundColor(color: Color) {
        updateBackgroundConfig { it.copy(secondaryBackgroundColor = color.toArgb()) }
    }

    fun setThirdBackgroundColor(color: Color) {
        updateBackgroundConfig { it.copy(thirdBackgroundColor = color.toArgb()) }
    }

    fun setPrimaryColor(color: Color) {
        updateBackgroundConfig { it.copy(primaryColor = color.toArgb()) }
    }

    fun setMainContainerColor(color: Color) {
        updateBackgroundConfig { it.copy(mainContainerColor = color.toArgb()) }
    }

    fun setTextColor(color: Color) {
        updateBackgroundConfig { it.copy(textColor = color.toArgb()) }
    }

    fun deleteAccount(account: AccountEntity) {
        viewModelScope.launch {
            accountRepository.deleteAccount(account)
        }
    }
}