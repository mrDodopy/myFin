 package com.example.myfin.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import java.io.IOException

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

class UserPreferencesRepository(private val context: Context) {

    // Ключи теперь публичные (без слова private), чтобы ViewModel видела их
    object PreferencesKeys {
        val IS_DARK_MODE = booleanPreferencesKey("is_dark_mode")
        val AUTH_TOKEN = stringPreferencesKey("auth_token")
        // ➕ ЕСЛИ ДОБАВЯТСЯ ПОЛЯ: Создавайте новые ключи здесь:
        // val USER_NAME = stringPreferencesKey("user_name")
    }

    // Общий базовый поток для безопасного чтения
    private val rootPreferencesFlow: Flow<Preferences> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }

    // Чтение всего объекта (маппинг)
    val userSettingsFlow: Flow<UserSettings> = rootPreferencesFlow.map { preferences ->
        UserSettings(
            isDarkMode = preferences[PreferencesKeys.IS_DARK_MODE] ?: false,
            authToken = preferences[PreferencesKeys.AUTH_TOKEN]
            // ➕ ЕСЛИ ДОБАВЯТСЯ ПОЛЯ: Считывайте их из preferences здесь:
            // userName = preferences[PreferencesKeys.USER_NAME] ?: ""
        )
    }

    // Изолированное чтение отдельных полей (distinctUntilChanged защищает от ложных триггеров UI)
    val isDarkModeFlow: Flow<Boolean> = rootPreferencesFlow
        .map { it[PreferencesKeys.IS_DARK_MODE] ?: false }
        .distinctUntilChanged()

    // ➕ ЕСЛИ ДОБАВЯТСЯ ПОЛЯ: Для каждого важного поля пишем одну такую строчку:
    // val userNameFlow: Flow<String> = rootPreferencesFlow.map { it[PreferencesKeys.USER_NAME] ?: "" }.distinctUntilChanged()


    // ВСЕГО ОДНА универсальная функция записи вместо десятка старых методов!
    suspend fun update(action: (MutablePreferences) -> Unit) {
        context.dataStore.edit { preferences ->
            action(preferences)
        }
    }
}
