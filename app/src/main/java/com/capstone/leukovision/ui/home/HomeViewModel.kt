package com.capstone.leukovision.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Selamat datang di LeukoVision!" // Mengubah teks sambutan
    }
    val text: LiveData<String> = _text

    // Anda bisa menambahkan LiveData lain di sini untuk data dashboard
    // Misalnya, jumlah pasien, diagnosis terbaru, dll.
    // private val _patientCount = MutableLiveData<Int>()
    // val patientCount: LiveData<Int> = _patientCount
    //
    // fun loadDashboardData() {
    //     // Logika untuk memuat data dashboard dari repo/API
    //     _patientCount.value = 123
    // }
}