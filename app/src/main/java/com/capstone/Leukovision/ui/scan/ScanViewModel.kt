package com.capstone.leukovision.ui.scan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScanViewModel : ViewModel() {

    private val _result = MutableLiveData<String>()
    val result: LiveData<String> get() = _result

    fun processImage(callback: (String) -> Unit) {
        // Simulasi pemrosesan gambar
        // Di sini Anda bisa memanggil metode pemrosesan gambar yang sebenarnya
        // Misalnya, menggunakan TensorFlow Lite untuk memproses gambar

        // Simulasi delay untuk pemrosesan
        Thread {
            // Simulasi pemrosesan gambar
            Thread.sleep(2000) // Simulasi waktu pemrosesan
            val processedResult = "Hasil Pemrosesan Gambar" // Ganti dengan hasil pemrosesan yang sebenarnya

            // Mengupdate LiveData
            _result.postValue(processedResult)

            // Panggil callback dengan hasil
            callback(processedResult)
        }.start()
    }
}