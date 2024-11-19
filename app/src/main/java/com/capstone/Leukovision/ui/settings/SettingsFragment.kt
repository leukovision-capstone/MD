package com.capstone.Leukovision.ui.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Switch
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.capstone.Leukovision.R

class SettingsFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var languageGroup: RadioGroup
    private lateinit var darkModeSwitch: Switch

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize SharedPreferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())

        // Get UI elements
        languageGroup = view.findViewById(R.id.languageGroup)
        darkModeSwitch = view.findViewById(R.id.darkModeSwitch)

        // Load saved preferences
        val savedLanguage = sharedPreferences.getString("language", "en")
        val isDarkMode = sharedPreferences.getBoolean("dark_mode", false)

        // Set saved language
        when (savedLanguage) {
            "en" -> languageGroup.check(R.id.radioEnglish)
            "id" -> languageGroup.check(R.id.radioIndonesian)
        }

        // Set dark mode switch
        darkModeSwitch.isChecked = isDarkMode

        // Set listeners
        languageGroup.setOnCheckedChangeListener { _, checkedId ->
            val selectedLanguage = when (checkedId) {
                R.id.radioEnglish -> "en"
                R.id.radioIndonesian -> "id"
                else -> "en"
            }
            saveLanguagePreference(selectedLanguage)
            setAppLanguage(selectedLanguage)
        }

        darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            saveDarkModePreference(isChecked)
            setDarkMode(isChecked)
        }
    }

    private fun saveLanguagePreference(language: String) {
        sharedPreferences.edit().putString("language", language).apply()
    }

    private fun setAppLanguage(language: String) {
        val locale = java.util.Locale(language)
        java.util.Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
        requireActivity().recreate() // Recreate activity to apply changes
    }

    private fun saveDarkModePreference(isDarkMode: Boolean) {
        sharedPreferences.edit().putBoolean("dark_mode", isDarkMode).apply()
    }

    private fun setDarkMode(isDarkMode: Boolean) {
        val mode = if (isDarkMode) {
            AppCompatDelegate.MODE_NIGHT_YES
        } else {
            AppCompatDelegate.MODE_NIGHT_NO
        }
        AppCompatDelegate.setDefaultNightMode(mode)
    }
}
