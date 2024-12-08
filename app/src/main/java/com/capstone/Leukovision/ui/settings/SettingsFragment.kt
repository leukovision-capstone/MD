package com.capstone.leukovision.ui.settings

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.capstone.leukovision.R
import com.capstone.leukovision.util.SettingsViewModelFactory

class SettingsFragment : PreferenceFragmentCompat() {
    private val viewModel by viewModels<SettingsViewModel> {
        SettingsViewModelFactory.getInstance(requireContext())
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preference)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupThemePreference()
    }

    private fun setupThemePreference() {
        val themeSwitch = findPreference<SwitchPreference>("themeKey")

        // Mengamati perubahan tema dari ViewModel
        viewModel.theme.observe(viewLifecycleOwner) { isDark ->
            themeSwitch?.isChecked = isDark
            // Mengatur mode malam sesuai dengan preferensi
            AppCompatDelegate.setDefaultNightMode(
                if (isDark) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            )
        }

        // Menangani perubahan preferensi
        themeSwitch?.setOnPreferenceChangeListener { _, newValue ->
            val isDarkMode = newValue as Boolean
            viewModel.saveThemeSetting(isDarkMode)
            // Mengatur mode malam sesuai dengan preferensi yang baru
            AppCompatDelegate.setDefaultNightMode(
                if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            )
            true
        }
    }

    companion object {
        private const val TAG = "SettingsFragment"
    }
}