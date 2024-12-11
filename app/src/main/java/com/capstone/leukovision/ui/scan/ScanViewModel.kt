package dicoding.capstone.leukovision.ui.scan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScanViewModel : ViewModel() {

    // LiveData untuk teks tampilan
    private val _text = MutableLiveData<String>().apply {
        value = "Silakan pilih gambar dari galeri atau validasi gambar."
    }
    val text: LiveData<String> get() = _text

    // LiveData untuk menyimpan URI gambar yang dipilih
    private val _selectedImageUri = MutableLiveData<String?>()
    val selectedImageUri: LiveData<String?> get() = _selectedImageUri

    /**
     * Set URI gambar yang dipilih dari galeri.
     * @param uri URI gambar
     */
    fun setSelectedImageUri(uri: String) {
        _selectedImageUri.value = uri
    }

    /**
     * Validasi gambar (fungsi ini dapat dikembangkan sesuai logika Anda).
     */
    fun validateImage() {
        // Tambahkan logika validasi gambar jika diperlukan
        _text.value = "Gambar berhasil divalidasi. Navigasi ke halaman hasil."
    }
}
