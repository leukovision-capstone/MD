package com.capstone.leukovision.ui.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {

    // Contoh: LiveData untuk versi aplikasi
    private val _appVersion = MutableLiveData<String>().apply {
        value = "Versi 1.0.0" // Ini bisa didapatkan secara dinamis nanti
    }
    val appVersion: LiveData<String> = _appVersion

    // Contoh: LiveData untuk status notifikasi (jika ada toggle)
    private val _notificationsEnabled = MutableLiveData<Boolean>().apply {
        value = true // Defaultnya aktif
    }
    val notificationsEnabled: LiveData<Boolean> = _notificationsEnabled

    fun toggleNotifications(isEnabled: Boolean) {
        _notificationsEnabled.value = isEnabled
        // Di sini Anda bisa menyimpan preferensi ini ke SharedPreferences atau datastore
    }

    // Fungsi lain yang mungkin dibutuhkan, misalnya untuk logout
    fun performLogout() {
        // Implementasi logika logout:
        // - Hapus token otentikasi
        // - Navigasi ke halaman login
    }
}