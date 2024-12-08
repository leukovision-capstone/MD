package com.capstone.leukovision.ui.settings

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Menggunakan Preferences dari androidx.datastore.preferences.core
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingPreferences private constructor(private val dataStore: DataStore<Preferences>) {
    private val themeKey = booleanPreferencesKey("setting_theme")

    // Fungsi untuk mengambil pengaturan tema
    fun getThemeSetting(): Flow<Boolean> {
        return dataStore.data
            .map { preferences ->
                preferences[themeKey] ?: false // default false jika belum diset
            }
    }

    // Fungsi untuk menyimpan pengaturan tema
    suspend fun saveThemeSetting(isDarkMode: Boolean) {
        dataStore.edit { preferences ->
            preferences[themeKey] = isDarkMode
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: SettingPreferences? = null

        // Singleton untuk mengambil instance SettingPreferences
        fun getInstance(dataStore: DataStore<Preferences>): SettingPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = SettingPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}