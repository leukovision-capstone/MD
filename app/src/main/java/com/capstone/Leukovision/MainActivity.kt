package com.capstone.leukovision

import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.capstone.leukovision.databinding.ActivityMainBinding
import com.capstone.leukovision.ui.scan.ScanFragment
import com.capstone.leukovision.ui.settings.ThemePreferences
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var themePreferences: ThemePreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ScanFragment())
                .commit()
        }

        // Inisialisasi SharedPreferences
        sharedPreferences = getSharedPreferences("app_preferences", MODE_PRIVATE)
        themePreferences = ThemePreferences(this)

        // Mengambil preferensi tema dari SharedPreferences
        val isDarkMode = sharedPreferences.getBoolean("isDarkMode", false)
        updateTheme(isDarkMode)

        // Inflate layout dan set content view
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup navigasi
        setupNavigation()
    }

    // Call this method when the user toggles the theme
    fun toggleTheme() {
        val isDarkMode = !themePreferences.isDarkModeEnabled()
        themePreferences.onThemeToggle(isDarkMode)
    }

    private fun setupNavigation() {
        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        // Konfigurasi app bar dan nav controller
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_patients,
                R.id.navigation_scan,
                R.id.navigation_history,
                R.id.navigation_settings
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    // Fungsi untuk memperbarui tema
    private fun updateTheme(isDarkMode: Boolean) {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
            WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED
        )

        lifecycleScope.launch {
            val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
            val newNightMode = if (isDarkMode) Configuration.UI_MODE_NIGHT_YES else Configuration.UI_MODE_NIGHT_NO

            if (currentNightMode != newNightMode) {
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        window.setWindowAnimations(android.R.style.Animation_Toast)
                    }
                    AppCompatDelegate.setDefaultNightMode(
                        if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES
                        else AppCompatDelegate.MODE_NIGHT_NO
                    )
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                } catch (e: Exception) {
                    Log.e(TAG, "Error updating theme: ${e.message}")
                }
            }
        }
    }

    // Fungsi untuk menyimpan preferensi tema
    fun saveThemeSetting(isDarkMode: Boolean) {
        sharedPreferences.edit().putBoolean("isDarkMode", isDarkMode).apply()
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}