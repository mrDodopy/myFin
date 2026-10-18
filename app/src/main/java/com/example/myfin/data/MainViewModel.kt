package com.example.myfin.data

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfin.data.entity.AccountEntity
import com.example.myfin.data.repositoryes.AccountRepository
import com.example.myfin.data.repositoryes.BackgroundRepository
import com.example.myfin.data.repositoryes.UserPreferencesRepository.PreferencesKeys
import com.example.myfin.data.repositoryes.CategoryRepository
import com.example.myfin.data.repositoryes.UserPreferencesRepository
import com.example.myfin.utils.converseIconToString
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val categoryRepository: CategoryRepository,
    private val accountRepository: AccountRepository,
    private val backgroundRepository: BackgroundRepository
) : ViewModel() {

    val userSettings = userPreferencesRepository.userSettingsFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UserSettings(
                themeMode = "system",
                autoConverse = false,
                turnedOnPassword = false,
                turnedOnBiometry = false,
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

    fun setAutoConverse(enabled: Boolean){
        viewModelScope.launch {
            userPreferencesRepository.update { preferences ->
                preferences[PreferencesKeys.AUTO_CONVERSE] = enabled
            }
        }
    }

    fun setTurnedOnPassword(enabled: Boolean){
        viewModelScope.launch {
            userPreferencesRepository.update { preferences ->
                preferences[PreferencesKeys.TURNED_ON_PASSWORD] = enabled
            }
        }
    }

    fun setTurnedOnBiometry(enabled: Boolean){
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
        ){
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
    ){
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

    fun createNewBackgroundConfig(
        backgroundColor: Int,
        mainBackgroundColor: Int,
        secondaryBackgroundColor: Int,
        thirdBackgroundColor: Int,
        mainBackgroundAlpha: Float,
        secondaryBackgroundAlpha: Float,
        thirdBackgroundAlpha: Float,
        primaryColor: Int,
        mainContainerColor: Int,
        textColor: Int
    ){
        viewModelScope.launch {
            backgroundRepository.addBackgroundConfig(
                backgroundColor = backgroundColor,
                mainBackgroundColor = mainBackgroundColor,
                secondaryBackgroundColor = secondaryBackgroundColor,
                thirdBackgroundColor = thirdBackgroundColor,
                mainBackgroundAlpha = mainBackgroundAlpha,
                secondaryBackgroundAlpha = secondaryBackgroundAlpha,
                thirdBackgroundAlpha = thirdBackgroundAlpha,
                primaryColor = primaryColor,
                mainContainerColor = mainContainerColor,
                textColor = textColor,
            )
        }
    }

    fun updateBackgroundConfig(
        id: Int,
        backgroundColor: Int,
        mainBackgroundColor: Int,
        secondaryBackgroundColor: Int,
        thirdBackgroundColor: Int,
        mainBackgroundAlpha: Float,
        secondaryBackgroundAlpha: Float,
        thirdBackgroundAlpha: Float,
        primaryColor: Int,
        mainContainerColor: Int,
        textColor: Int
    ){
        viewModelScope.launch {
            backgroundRepository.updateBackgroundConfig(
                id = id,
                backgroundColor = backgroundColor,
                mainBackgroundColor = mainBackgroundColor,
                secondaryBackgroundColor = secondaryBackgroundColor,
                thirdBackgroundColor = thirdBackgroundColor,
                mainBackgroundAlpha = mainBackgroundAlpha,
                secondaryBackgroundAlpha = secondaryBackgroundAlpha,
                thirdBackgroundAlpha = thirdBackgroundAlpha,
                primaryColor = primaryColor,
                mainContainerColor = mainContainerColor,
                textColor = textColor,
            )
        }
    }


    fun deleteAccount(
        account: AccountEntity
    ){
        viewModelScope.launch {
            accountRepository.deleteAccount(account = account)
        }
    }
}