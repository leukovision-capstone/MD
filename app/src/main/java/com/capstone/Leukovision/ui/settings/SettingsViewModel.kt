package com.capstone.leukovision.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SettingsViewModel(private val settingsPreferences: SettingPreferences) : ViewModel() {

    // LiveData untuk status tema (Dark Mode)
    private val _theme = MutableLiveData<Boolean>()
    val theme: LiveData<Boolean> get() = _theme

    // Memuat status tema dari DataStore
    init {
        viewModelScope.launch {
            settingsPreferences.getThemeSetting().collect { isDarkMode ->
                _theme.postValue(isDarkMode)
            }
        }
    }

    // Menyimpan status tema ke DataStore
    fun saveThemeSetting(isDarkMode: Boolean) {
        viewModelScope.launch {
            settingsPreferences.saveThemeSetting(isDarkMode)
        }
    }
}
