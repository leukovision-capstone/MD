package com.capstone.leukovision.ui.scan

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScanViewModel : ViewModel() {
    // Menggunakan MutableLiveData untuk menyimpan URI gambar
    private val _imageUri = MutableLiveData<Uri?>()
    val imageUri: LiveData<Uri?> get() = _imageUri

    // Metode untuk mengatur URI gambar
    fun setImageUri(uri: Uri) {
        _imageUri.value = uri
    }
}