package com.capstone.leukovision.ui.settings

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate

class ThemePreferences(private val context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)

    // Method to toggle theme
    fun onThemeToggle(isDarkMode: Boolean) {
        saveThemeSetting(isDarkMode)
        updateTheme(isDarkMode)
    }

    // Save the theme setting
    private fun saveThemeSetting(isDarkMode: Boolean) {
        sharedPreferences.edit().putBoolean("isDarkMode", isDarkMode).apply()
    }

    // Update the theme based on the preference
    private fun updateTheme(isDarkMode: Boolean) {
        AppCompatDelegate.setDefaultNightMode(
            if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )
    }

    // Retrieve the current theme setting
    fun isDarkModeEnabled(): Boolean {
        return sharedPreferences.getBoolean("isDarkMode", false)
    }
}