package dicoding.capstone.leukovision.ui.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.tensorflow.lite.task.vision.classifier.Classifications

class ResultViewModel : ViewModel() {

    // LiveData untuk URI gambar
    private val _imageUri = MutableLiveData<String>()
    val imageUri: LiveData<String> get() = _imageUri

    // LiveData untuk hasil klasifikasi
    private val _classificationResult = MutableLiveData<List<Classifications>>()
    val classificationResult: LiveData<List<Classifications>> get() = _classificationResult

    // Fungsi untuk menetapkan URI gambar
    fun setImageUri(uri: String) {
        _imageUri.value = uri
    }

    // Fungsi untuk menetapkan hasil klasifikasi
    fun setClassificationResult(result: List<Classifications>) {
        _classificationResult.value = result
    }
}
