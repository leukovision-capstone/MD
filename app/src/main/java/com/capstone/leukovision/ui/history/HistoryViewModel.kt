package com.capstone.leukovision.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.leukovision.data.HistoryItem
import java.util.Date

class HistoryViewModel : ViewModel() {

    private val _historyItems = MutableLiveData<List<HistoryItem>>()
    val historyItems: LiveData<List<HistoryItem>> = _historyItems

    init {
        // Contoh data dummy. Ini akan diganti dengan data dari database lokal atau API.
        _historyItems.value = listOf(
            HistoryItem(
                "H001",
                "https://example.com/image1.jpg", // Ganti dengan URL/path gambar asli
                "Limfoblastik Akut",
                0.98f,
                Date(System.currentTimeMillis() - 86400000 * 5) // 5 hari yang lalu
            ),
            HistoryItem(
                "H002",
                "https://example.com/image2.jpg", // Ganti dengan URL/path gambar asli
                "Tidak Ada Leukimia",
                0.95f,
                Date(System.currentTimeMillis() - 86400000 * 2) // 2 hari yang lalu
            ),
            HistoryItem(
                "H003",
                "https://example.com/image3.jpg", // Ganti dengan URL/path gambar asli
                "Limfoblastik Kronis",
                0.92f,
                Date(System.currentTimeMillis() - 86400000 * 1) // 1 hari yang lalu
            )
        )
    }

    // Fungsi placeholder untuk memuat data riwayat dari sumber eksternal
    fun loadHistory() {
        // Implementasi logika untuk memuat data riwayat dari database atau API
        // Misalnya:
        // viewModelScope.launch {
        //     try {
        //         val response = RetrofitInstance.apiService.getHistory()
        //         _historyItems.value = response.data // Sesuaikan dengan struktur ResponAPI Anda
        //     } catch (e: Exception) {
        //         // Tangani error
        //     }
        // }
    }
}