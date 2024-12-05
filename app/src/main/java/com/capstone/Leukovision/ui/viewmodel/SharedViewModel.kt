package com.capstone.leukovision.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {

    // LiveData untuk menyimpan hasil pemindaian
    private val _scanResult = MutableLiveData<String>()
    val scanResult: LiveData<String> get() = _scanResult

    // Metode untuk memperbarui hasil pemindaian
    fun updateScanResult(result: String) {
        _scanResult.value = result
    }
}