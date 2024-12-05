package com.capstone.leukovision.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    // MutableLiveData untuk teks yang ditampilkan di HomeFragment
    private val _text = MutableLiveData<String>().apply {
        value = "Hallo Dokter 👋 Mari atasi leukemia bersama Leukovision"
    }
    val text: LiveData<String> = _text

    // Anda dapat menambahkan data lain di sini sesuai kebutuhan
}