package com.capstone.leukovision.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.leukovision.ui.settings.SettingPreferences
import com.capstone.leukovision.ui.settings.SettingsViewModel
import com.capstone.leukovision.ui.settings.dataStore

@Suppress("UNCHECKED_CAST")
class SettingsViewModelFactory(
    private val preference: SettingPreferences
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SettingsViewModel::class.java) -> {
                SettingsViewModel(preference) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }

    companion object {
        @Volatile
        private var instance: SettingsViewModelFactory? = null

        fun getInstance(context: Context): SettingsViewModelFactory =
            instance ?: synchronized(this) {
                val preference = SettingPreferences.getInstance(context.dataStore)
                instance ?: SettingsViewModelFactory(preference)
            }.also { instance = it }
    }
}