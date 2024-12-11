package dicoding.capstone.leukovision.ui.result

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.capstone.leukovision.R
import com.capstone.leukovision.databinding.FragmentResultBinding
import dicoding.capstone.leukovision.utils.ImageClassifierHelper
import org.tensorflow.lite.task.vision.classifier.Classifications
import java.util.Locale

class ResultFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    private val resultViewModel: ResultViewModel by viewModels()
    private lateinit var imageClassifierHelper: ImageClassifierHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)

        val imageUriString = arguments?.getString(EXTRA_IMAGE_URI)
        if (imageUriString.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Tidak ada gambar yang dipilih.", Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
            return binding.root
        }

        val imageUri = Uri.parse(imageUriString)
        resultViewModel.setImageUri(imageUri.toString())

        setupImageClassifierHelper()

        resultViewModel.imageUri.observe(viewLifecycleOwner) { uri ->
            uri?.let {
                binding.imageScan.setImageURI(Uri.parse(it))
                classifyImage(it)
            }
        }

        setupButtonListeners()
        return binding.root
    }

    private fun setupImageClassifierHelper() {
        imageClassifierHelper = ImageClassifierHelper(
            context = requireContext(),
            modelPath = "validation_model_with_metadata.tflite"
        )
    }

    private fun classifyImage(imageUri: String) {
        try {
            val uri = Uri.parse(imageUri)
            val bitmap = uriToBitmap(uri) // Mengkonversi URI ke Bitmap
            val result: MutableList<Classifications>? = imageClassifierHelper.classifyImage(bitmap)

            // Tetap akses result, meskipun null
            if (result != null && result.isNotEmpty()) {
                // Ambil kategori dengan skor tertinggi
                val topCategory = result[0].categories.maxByOrNull { it.score }
                topCategory?.let { category ->
                    val label = category.label
                    val confidence = category.score
                    binding.textResult.text = "$label (${String.format(Locale.US, "%.2f%%", confidence * 100)})"
                } ?: run {
                    // Jika tidak ada kategori dalam hasil klasifikasi
                    binding.textResult.text = "Hasil klasifikasi tidak valid."
                }
            } else {
                // Jika result null atau kosong
                binding.textResult.text = "Tidak ada hasil klasifikasi."
            }
        } catch (e: Exception) {
            Log.e("ResultFragment", "Gagal mengklasifikasi gambar: ${e.message}", e)
            Toast.makeText(requireContext(), "Gagal menganalisis gambar.", Toast.LENGTH_SHORT).show()
        }
    }


    private fun uriToBitmap(uri: Uri): Bitmap {
        val inputStream = requireContext().contentResolver.openInputStream(uri)
        return android.graphics.BitmapFactory.decodeStream(inputStream)
    }

    private fun setupButtonListeners() {
        binding.buttonBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.buttonScanNew.setOnClickListener {
            findNavController().navigate(R.id.action_resultFragment_to_scanFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        imageClassifierHelper.close()
    }

    companion object {
        const val EXTRA_IMAGE_URI = "extra_image_uri"
    }
}
