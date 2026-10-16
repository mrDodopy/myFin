package com.example.myfin.data.repositoryes

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
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
                themeMode = preferences[PreferencesKeys.THEME_MODE] ?: "system"
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
}