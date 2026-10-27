package com.example.myfin.data.repositoryes

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.myfin.data.UserSettings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import java.io.IOException

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "user_preferences"
)

class UserPreferencesRepository(
    private val context: Context
) {

    object PreferencesKeys {
        val THEME_MODE = stringPreferencesKey("theme_mode")
        val AUTO_CONVERSE = booleanPreferencesKey("auto_converse")
        val TURNED_ON_PASSWORD = booleanPreferencesKey("turned_on_password")
        val TURNED_ON_BIOMETRY = booleanPreferencesKey("turned_on_biometry")
        val MAIN_CURRENCY = stringPreferencesKey("main_currency")
        val ADDITIONAL_CURRENCIES = stringSetPreferencesKey("additional_currencies")
        val CUSTOM_COLORS = stringSetPreferencesKey("custom_colors")

        // В будущем:
        // val USER_NAME = stringPreferencesKey("user_name")
    }

    private val rootPreferencesFlow: Flow<Preferences> =
        context.dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }

    val userSettingsFlow: Flow<UserSettings> =
        rootPreferencesFlow.map { preferences ->
            UserSettings(
                themeMode = preferences[PreferencesKeys.THEME_MODE] ?: "system",
                autoConverse = preferences[PreferencesKeys.AUTO_CONVERSE] ?: false,
                turnedOnPassword = preferences[PreferencesKeys.TURNED_ON_PASSWORD] ?: false,
                turnedOnBiometry = preferences[PreferencesKeys.TURNED_ON_BIOMETRY] ?: false,
                mainCurrency = preferences[PreferencesKeys.MAIN_CURRENCY] ?: "BYN",
                additionalCurrencies = preferences[PreferencesKeys.ADDITIONAL_CURRENCIES] ?: emptySet(),
                customColors = preferences[PreferencesKeys.CUSTOM_COLORS] ?: emptySet()
            )
        }

    val themeModeFlow: Flow<String> =
        rootPreferencesFlow
            .map { preferences ->
                preferences[PreferencesKeys.THEME_MODE] ?: "system"
            }
            .distinctUntilChanged()


    suspend fun update(
        action: (MutablePreferences) -> Unit
    ) {
        context.dataStore.edit { preferences ->
            action(preferences)
        }
    }

    suspend fun setMainCurrency(currency: String) {
        update { preferences ->
            preferences[PreferencesKeys.MAIN_CURRENCY] = currency
            // Основная валюта не может одновременно быть дополнительной
            val rest = preferences[PreferencesKeys.ADDITIONAL_CURRENCIES] ?: emptySet()
            preferences[PreferencesKeys.ADDITIONAL_CURRENCIES] = rest - currency
        }
    }

    suspend fun setAdditionalCurrencies(currencies: Set<String>) {
        update { preferences ->
            // Основная валюта всегда исключается из дополнительных
            val main = preferences[PreferencesKeys.MAIN_CURRENCY] ?: "BYN"
            preferences[PreferencesKeys.ADDITIONAL_CURRENCIES] = currencies - main
        }
    }

    /**
     * Пользовательские цвета для редакторов категорий/счетов
     * (сейчас только читаются — ячейки в палитре; добавление новых отключено).
     */
    suspend fun setCustomColors(colors: List<Int>) {
        update { preferences ->
            preferences[PreferencesKeys.CUSTOM_COLORS] =
                colors.take(2).map { it.toString() }.toSet()
        }
    }
}