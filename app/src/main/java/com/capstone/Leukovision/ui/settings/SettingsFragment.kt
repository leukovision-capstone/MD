package com.capstone.leukovision.ui.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.capstone.leukovision.R

class SettingsFragment : Fragment() {

    private lateinit var switchTheme: Switch
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_settings, container, false)

        switchTheme = view.findViewById(R.id.switch_theme)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())

        // Set the switch state based on saved preference
        val isDarkTheme = sharedPreferences.getBoolean("isDarkTheme", false)
        switchTheme.isChecked = isDarkTheme

        // Set listener for switch
        switchTheme.setOnCheckedChangeListener { _, isChecked ->
            // Save the theme preference
            sharedPreferences.edit().putBoolean("isDarkTheme", isChecked).apply()
            // Apply the theme
            applyTheme(isChecked)
        }

        return view
    }

    private fun applyTheme(isDark: Boolean) {
        if (isDark) {
            requireActivity().setTheme(R.style.Theme_App_Dark) // Tema gelap
        } else {
            requireActivity().setTheme(R.style.Theme_App_Light) // Tema terang
        }
        requireActivity().recreate() // Recreate activity untuk menerapkan tema
    }
}
